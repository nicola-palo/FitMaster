package com.fitmaster.app.data.model

import com.fitmaster.app.data.entity.Exercise
import com.fitmaster.app.data.entity.WorkoutPlanExercise

data class ExerciseWithDetails(
    val planExercise: WorkoutPlanExercise,
    val exercise: Exercise
)

data class WorkoutDayWithExercises(
    val dayId: Long,
    val dayName: String,
    val dayOfWeek: Int,
    val exercises: List<ExerciseWithDetails>
)

data class ActiveWorkoutState(
    val sessionId: Long,
    val currentExerciseIndex: Int,
    val currentSet: Int,
    val exercises: List<ExerciseWithDetails>,
    val isResting: Boolean,
    val elapsedSeconds: Long
)
