package com.fitmaster.app.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fitmaster.app.data.entity.WorkoutPlan
import com.fitmaster.app.data.entity.WorkoutPlanDay
import com.fitmaster.app.data.entity.WorkoutSession
import com.fitmaster.app.data.repository.WorkoutPlanRepository
import com.fitmaster.app.data.repository.WorkoutSessionRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class HomeViewModel(
    private val workoutPlanRepository: WorkoutPlanRepository,
    private val workoutSessionRepository: WorkoutSessionRepository
) : ViewModel() {
    
    val activePlan: StateFlow<WorkoutPlan?> = workoutPlanRepository.getActiveWorkoutPlan()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = null
        )
    
    val recentSessions: StateFlow<List<WorkoutSession>> = 
        workoutSessionRepository.getCompletedSessions()
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = emptyList()
            )
    
    val allPlans: StateFlow<List<WorkoutPlan>> = workoutPlanRepository.getAllWorkoutPlans()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )
    
    val favoritePlans: StateFlow<List<WorkoutPlan>> = workoutPlanRepository.getFavoritePlans()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )
    
    private val _planDays = MutableStateFlow<List<WorkoutPlanDay>>(emptyList())
    val planDays: StateFlow<List<WorkoutPlanDay>> = _planDays.asStateFlow()
    
    fun loadPlanDays(planId: Long) {
        viewModelScope.launch {
            val days = workoutPlanRepository.getPlanDays(planId).first()
            _planDays.value = days
        }
    }
    
    fun setActivePlan(planId: Long) {
        viewModelScope.launch {
            workoutPlanRepository.setActivePlan(planId)
        }
    }
    
    fun deactivatePlan() {
        viewModelScope.launch {
            try {
                workoutPlanRepository.deactivateAllPlans()
            } catch (e: Exception) {
                // Handle error gracefully
                e.printStackTrace()
            }
        }
    }
    
    fun deletePlan(plan: WorkoutPlan) {
        viewModelScope.launch {
            workoutPlanRepository.deleteWorkoutPlan(plan)
        }
    }
    
    fun deleteSession(session: WorkoutSession) {
        viewModelScope.launch {
            workoutSessionRepository.deleteSession(session)
        }
    }
    
    fun getPlanDay(dayId: Long) = workoutPlanRepository.getPlanDayById(dayId)
    
    fun toggleFavorite(plan: WorkoutPlan, onMaxReached: () -> Unit) {
        viewModelScope.launch {
            val success = workoutPlanRepository.toggleFavorite(plan.id, plan.isFavorite)
            if (!success) {
                onMaxReached()
            }
        }
    }
}
