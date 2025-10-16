package com.fitmaster.app.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.fitmaster.app.data.converter.Converters
import java.util.Date

@Entity(tableName = "body_measurements")
@TypeConverters(Converters::class)
data class BodyMeasurement(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val date: Date = Date(),
    val weight: Float, // kg
    val bodyFatPercentage: Float? = null, // %
    val leanMass: Float? = null, // kg magri
    val fatMass: Float? = null, // massa grassa in kg
    val height: Float? = null, // cm (usually doesn't change)
    val chest: Float? = null, // cm
    val waist: Float? = null, // cm
    val biceps: Float? = null, // cm
    val thighs: Float? = null, // cm
    val notes: String? = null
)

@Entity(tableName = "user_profile")
@TypeConverters(Converters::class)
data class UserProfile(
    @PrimaryKey
    val id: Long = 1, // Always 1, single user
    val name: String,
    val username: String? = null,
    val age: Int,
    val sex: String, // Male, Female
    val goal: String, // Muscle Gain, Fat Loss, Maintenance
    val targetWeight: Float? = null, // kg - obiettivo peso
    val targetBodyFatPercentage: Float? = null, // % - obiettivo % grassa
    val profileImageUrl: String? = null,
    val createdAt: Date = Date(),
    val updatedAt: Date = Date()
)
