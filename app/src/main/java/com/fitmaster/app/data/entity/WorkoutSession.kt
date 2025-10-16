package com.fitmaster.app.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.fitmaster.app.data.converter.Converters
import java.util.Date

@Entity(tableName = "workout_sessions")
@TypeConverters(Converters::class)
data class WorkoutSession(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val planId: Long,
    val planDayId: Long,
    val startTime: Date,
    val endTime: Date? = null,
    val durationSeconds: Long = 0,
    val isCompleted: Boolean = false
)

@Entity(tableName = "workout_session_exercises")
@TypeConverters(Converters::class)
data class WorkoutSessionExercise(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val sessionId: Long,
    val exerciseId: Long,
    val plannedSets: Int,
    val completedSets: Int = 0,
    val plannedReps: Int,
    val actualReps: Int? = null, // Actual reps performed
    val restSeconds: Int,
    val completedAt: Date? = null
)
