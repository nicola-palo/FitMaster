package com.fitmaster.app.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.fitmaster.app.data.repository.*

class ViewModelFactory(
    private val exerciseRepository: ExerciseRepository,
    private val workoutPlanRepository: WorkoutPlanRepository,
    private val workoutSessionRepository: WorkoutSessionRepository,
    private val userRepository: UserRepository
) : ViewModelProvider.Factory {
    
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(HomeViewModel::class.java) -> {
                HomeViewModel(workoutPlanRepository, workoutSessionRepository) as T
            }
            modelClass.isAssignableFrom(ExerciseListViewModel::class.java) -> {
                ExerciseListViewModel(exerciseRepository) as T
            }
            modelClass.isAssignableFrom(WorkoutPlanListViewModel::class.java) -> {
                WorkoutPlanListViewModel(workoutPlanRepository) as T
            }
            modelClass.isAssignableFrom(WorkoutPlanViewModel::class.java) -> {
                WorkoutPlanViewModel(workoutPlanRepository, exerciseRepository) as T
            }
            modelClass.isAssignableFrom(ActiveWorkoutViewModel::class.java) -> {
                ActiveWorkoutViewModel(workoutSessionRepository, workoutPlanRepository, exerciseRepository) as T
            }
            modelClass.isAssignableFrom(UserProfileViewModel::class.java) -> {
                UserProfileViewModel(userRepository) as T
            }
            else -> throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
    
    companion object {
        fun createExerciseDetailViewModel(
            exerciseRepository: ExerciseRepository,
            exerciseId: Long
        ): ExerciseDetailViewModel {
            return ExerciseDetailViewModel(exerciseRepository, exerciseId)
        }
    }
}
