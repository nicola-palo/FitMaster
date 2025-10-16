package com.fitmaster.app.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fitmaster.app.data.entity.Exercise
import com.fitmaster.app.data.repository.ExerciseRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn

class ExerciseDetailViewModel(
    private val exerciseRepository: ExerciseRepository,
    private val exerciseId: Long
) : ViewModel() {
    
    val exercise: StateFlow<Exercise?> = exerciseRepository.getExerciseById(exerciseId)
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = null
        )
}
