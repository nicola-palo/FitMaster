package com.fitmaster.app.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.fitmaster.app.data.converter.Converters
import java.util.Date

@Entity(tableName = "workout_plans")
@TypeConverters(Converters::class)
data class WorkoutPlan(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val name: String,
    val createdAt: Date = Date(),
    val isActive: Boolean = false,
    val isFavorite: Boolean = false
)

@Entity(tableName = "workout_plan_days")
data class WorkoutPlanDay(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val planId: Long,
    val dayOfWeek: Int, // 1 = Monday, 7 = Sunday
    val dayName: String
)

@Entity(tableName = "workout_plan_exercises")
data class WorkoutPlanExercise(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val planDayId: Long,
    val exerciseId: Long,
    val sets: Int,
    val reps: Int,
    val restSeconds: Int,
    val orderIndex: Int // To maintain order in the workout
)
