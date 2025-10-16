package com.fitmaster.app.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fitmaster.app.data.entity.*
import com.fitmaster.app.data.model.ActiveWorkoutState
import com.fitmaster.app.data.model.ExerciseWithDetails
import com.fitmaster.app.data.repository.ExerciseRepository
import com.fitmaster.app.data.repository.WorkoutPlanRepository
import com.fitmaster.app.data.repository.WorkoutSessionRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.util.Date

class ActiveWorkoutViewModel(
    private val workoutSessionRepository: WorkoutSessionRepository,
    private val workoutPlanRepository: WorkoutPlanRepository,
    private val exerciseRepository: ExerciseRepository
) : ViewModel() {
    
    private val _workoutState = MutableStateFlow<ActiveWorkoutState?>(null)
    val workoutState: StateFlow<ActiveWorkoutState?> = _workoutState.asStateFlow()
    
    private val _elapsedTime = MutableStateFlow(0L)
    val elapsedTime: StateFlow<Long> = _elapsedTime.asStateFlow()
    
    private val _restTimeRemaining = MutableStateFlow(0)
    val restTimeRemaining: StateFlow<Int> = _restTimeRemaining.asStateFlow()
    
    private val _isPaused = MutableStateFlow(false)
    val isPaused: StateFlow<Boolean> = _isPaused.asStateFlow()
    
    private val _isCompleted = MutableStateFlow(false)
    val isCompleted: StateFlow<Boolean> = _isCompleted.asStateFlow()
    
    private var timerJob: Job? = null
    private var restTimerJob: Job? = null
    
    fun startWorkout(planId: Long, dayId: Long) {
        viewModelScope.launch {
            // Create workout session
            val session = WorkoutSession(
                planId = planId,
                planDayId = dayId,
                startTime = Date(),
                isCompleted = false
            )
            val sessionId = workoutSessionRepository.insertSession(session)
            
            // Load exercises for this day
            val exercises = mutableListOf<ExerciseWithDetails>()
            workoutPlanRepository.getPlanDayExercises(dayId).first().forEach { planExercise ->
                exerciseRepository.getExerciseById(planExercise.exerciseId).first()?.let { exercise ->
                    exercises.add(ExerciseWithDetails(planExercise, exercise))
                    
                    // Create session exercise
                    workoutSessionRepository.insertSessionExercise(
                        WorkoutSessionExercise(
                            sessionId = sessionId,
                            exerciseId = exercise.id,
                            plannedSets = planExercise.sets,
                            completedSets = 0,
                            plannedReps = planExercise.reps,
                            restSeconds = planExercise.restSeconds
                        )
                    )
                }
            }
            
            _workoutState.value = ActiveWorkoutState(
                sessionId = sessionId,
                currentExerciseIndex = 0,
                currentSet = 1,
                exercises = exercises,
                isResting = false,
                elapsedSeconds = 0
            )
            
            startTimer()
        }
    }
    
    fun completeSet() {
        val state = _workoutState.value ?: return
        val currentExercise = state.exercises[state.currentExerciseIndex]
        
        viewModelScope.launch {
            if (state.currentSet < currentExercise.planExercise.sets) {
                // Start rest period
                _workoutState.value = state.copy(isResting = true)
                startRestTimer(currentExercise.planExercise.restSeconds)
            } else {
                // Move to next exercise
                moveToNextExercise()
            }
        }
    }
    
    private fun moveToNextExercise() {
        val state = _workoutState.value ?: return
        
        if (state.currentExerciseIndex < state.exercises.size - 1) {
            _workoutState.value = state.copy(
                currentExerciseIndex = state.currentExerciseIndex + 1,
                currentSet = 1,
                isResting = false
            )
        } else {
            // Workout completed
            completeWorkout()
        }
    }
    
    fun skipRest() {
        restTimerJob?.cancel()
        _restTimeRemaining.value = 0
        
        val state = _workoutState.value ?: return
        _workoutState.value = state.copy(
            currentSet = state.currentSet + 1,
            isResting = false
        )
    }
    
    fun skipExercise() {
        moveToNextExercise()
    }
    
    fun pauseWorkout() {
        _isPaused.value = true
        timerJob?.cancel()
    }
    
    fun resumeWorkout() {
        _isPaused.value = false
        startTimer()
    }
    
    private fun startTimer() {
        timerJob?.cancel()
        timerJob = viewModelScope.launch {
            while (true) {
                delay(1000)
                if (!_isPaused.value) {
                    _elapsedTime.value += 1
                }
            }
        }
    }
    
    private fun startRestTimer(seconds: Int) {
        _restTimeRemaining.value = seconds
        restTimerJob?.cancel()
        restTimerJob = viewModelScope.launch {
            while (_restTimeRemaining.value > 0) {
                delay(1000)
                _restTimeRemaining.value -= 1
            }
            
            // Rest completed, move to next set
            val state = _workoutState.value ?: return@launch
            _workoutState.value = state.copy(
                currentSet = state.currentSet + 1,
                isResting = false
            )
        }
    }
    
    fun addRestTime(seconds: Int) {
        _restTimeRemaining.value += seconds
    }
    
    private fun completeWorkout() {
        viewModelScope.launch {
            val state = _workoutState.value ?: return@launch
            
            workoutSessionRepository.getSessionById(state.sessionId).first()?.let { session ->
                workoutSessionRepository.updateSession(
                    session.copy(
                        endTime = Date(),
                        durationSeconds = _elapsedTime.value,
                        isCompleted = true
                    )
                )
            }
            
            timerJob?.cancel()
            _isCompleted.value = true
        }
    }
    
    override fun onCleared() {
        super.onCleared()
        timerJob?.cancel()
        restTimerJob?.cancel()
    }
}
