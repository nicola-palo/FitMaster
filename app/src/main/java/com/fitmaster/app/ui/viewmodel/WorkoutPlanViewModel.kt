package com.fitmaster.app.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fitmaster.app.data.entity.*
import com.fitmaster.app.data.repository.ExerciseRepository
import com.fitmaster.app.data.repository.WorkoutPlanRepository
import com.fitmaster.app.data.model.ExerciseWithDetails
import com.fitmaster.app.data.model.WorkoutDayWithExercises
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.util.Date

class WorkoutPlanViewModel(
    private val workoutPlanRepository: WorkoutPlanRepository,
    private val exerciseRepository: ExerciseRepository
) : ViewModel() {
    
    private val _planName = MutableStateFlow("My Workout Plan")
    val planName: StateFlow<String> = _planName.asStateFlow()
    
    private val _currentPlanId = MutableStateFlow<Long?>(null)
    val currentPlanId: StateFlow<Long?> = _currentPlanId.asStateFlow()
    
    private val _workoutDays = MutableStateFlow<List<WorkoutDayWithExercises>>(emptyList())
    val workoutDays: StateFlow<List<WorkoutDayWithExercises>> = _workoutDays.asStateFlow()
    
    val allExercises: StateFlow<List<Exercise>> = exerciseRepository.getAllExercises()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )
    
    fun setPlanName(name: String) {
        _planName.value = name
        // Auto-save the plan name
        viewModelScope.launch {
            _currentPlanId.value?.let { planId ->
                workoutPlanRepository.getWorkoutPlanById(planId).first()?.let { plan ->
                    workoutPlanRepository.updateWorkoutPlan(
                        plan.copy(name = name)
                    )
                }
            }
        }
    }
    
    fun loadPlan(planId: Long) {
        viewModelScope.launch {
            _currentPlanId.value = planId
            workoutPlanRepository.getWorkoutPlanById(planId).first()?.let { plan ->
                _planName.value = plan.name
                loadPlanDays(planId)
            }
        }
    }
    
    private suspend fun loadPlanDays(planId: Long) {
        val days = workoutPlanRepository.getPlanDays(planId).first()
        val daysWithExercises = days.map { day ->
            val planExercises = workoutPlanRepository.getPlanDayExercises(day.id).first()
            val exercisesWithDetails = planExercises.mapNotNull { planEx ->
                val exercise = exerciseRepository.getExerciseById(planEx.exerciseId).first()
                exercise?.let { ExerciseWithDetails(planEx, it) }
            }
            WorkoutDayWithExercises(
                dayId = day.id,
                dayName = day.dayName,
                dayOfWeek = day.dayOfWeek,
                exercises = exercisesWithDetails
            )
        }
        _workoutDays.value = daysWithExercises
    }
    
    fun createNewPlan() {
        viewModelScope.launch {
            val plan = WorkoutPlan(
                name = _planName.value,
                createdAt = Date(),
                isActive = false
            )
            val planId = workoutPlanRepository.insertWorkoutPlan(plan)
            _currentPlanId.value = planId
            
            // Create 7 days
            val daysOfWeek = listOf("Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday")
            val dayIds = mutableListOf<Long>()
            
            daysOfWeek.forEachIndexed { index, dayName ->
                val dayId = workoutPlanRepository.insertPlanDay(
                    WorkoutPlanDay(
                        planId = planId,
                        dayOfWeek = index + 1,
                        dayName = dayName
                    )
                )
                dayIds.add(dayId)
            }
            
            // Initialize empty days
            _workoutDays.value = daysOfWeek.mapIndexed { index, dayName ->
                WorkoutDayWithExercises(
                    dayId = dayIds.getOrNull(index) ?: 0L,
                    dayName = dayName,
                    dayOfWeek = index + 1,
                    exercises = emptyList()
                )
            }
        }
    }
    
    fun addExerciseToDay(dayId: Long, exerciseId: Long, sets: Int = 3, reps: Int = 10, restSeconds: Int = 60) {
        viewModelScope.launch {
            workoutPlanRepository.getPlanDayExercises(dayId).first().let { currentExercises ->
                val orderIndex = currentExercises.size
                workoutPlanRepository.insertPlanExercise(
                    WorkoutPlanExercise(
                        planDayId = dayId,
                        exerciseId = exerciseId,
                        sets = sets,
                        reps = reps,
                        restSeconds = restSeconds,
                        orderIndex = orderIndex
                    )
                )
                _currentPlanId.value?.let { loadPlanDays(it) }
            }
        }
    }
    
    fun removeExerciseFromDay(planExerciseId: Long) {
        viewModelScope.launch {
            workoutPlanRepository.deletePlanExerciseById(planExerciseId)
            _currentPlanId.value?.let { loadPlanDays(it) }
        }
    }
    
    fun savePlan() {
        viewModelScope.launch {
            _currentPlanId.value?.let { planId ->
                workoutPlanRepository.getWorkoutPlanById(planId).first()?.let { plan ->
                    workoutPlanRepository.updateWorkoutPlan(
                        plan.copy(name = _planName.value)
                    )
                }
            }
        }
    }
}
