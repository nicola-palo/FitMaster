package com.fitmaster.app.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fitmaster.app.data.entity.WorkoutPlan
import com.fitmaster.app.data.repository.WorkoutPlanRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class WorkoutPlanListViewModel(
    private val workoutPlanRepository: WorkoutPlanRepository
) : ViewModel() {
    
    val allPlans: StateFlow<List<WorkoutPlan>> = workoutPlanRepository.getAllWorkoutPlans()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )
    
    val activePlan: StateFlow<WorkoutPlan?> = workoutPlanRepository.getActiveWorkoutPlan()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = null
        )
    
    fun setActivePlan(planId: Long) {
        viewModelScope.launch {
            workoutPlanRepository.setActivePlan(planId)
        }
    }
    
    fun deletePlan(plan: WorkoutPlan) {
        viewModelScope.launch {
            workoutPlanRepository.deleteWorkoutPlan(plan)
        }
    }
    
    fun toggleFavorite(plan: WorkoutPlan, onMaxReached: () -> Unit) {
        viewModelScope.launch {
            val success = workoutPlanRepository.toggleFavorite(plan.id, plan.isFavorite)
            if (!success) {
                onMaxReached()
            }
        }
    }
}
