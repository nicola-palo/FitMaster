package com.fitmaster.app.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "exercises")
data class Exercise(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val name: String,
    val description: String,
    val technicalTips: String, // Stored as newline-separated list
    val category: String, // Strength, Cardio, Flexibility, etc.
    val difficulty: String, // Beginner, Intermediate, Advanced
    val muscleGroup: String, // Chest, Back, Legs, Arms, Core, etc.
    val defaultSets: Int = 3,
    val defaultReps: Int = 10,
    val defaultRestSeconds: Int = 60,
    val imageUrl: String? = null,
    val videoUrl: String? = null,
    val isCustom: Boolean = false // false for predefined, true for user-created
)
