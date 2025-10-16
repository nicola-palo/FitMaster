package com.fitmaster.app.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fitmaster.app.data.entity.BodyMeasurement
import com.fitmaster.app.data.entity.UserProfile
import com.fitmaster.app.data.repository.UserRepository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.util.Date

class UserProfileViewModel(
    private val userRepository: UserRepository
) : ViewModel() {
    
    val userProfile: StateFlow<UserProfile?> = userRepository.getUserProfile()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = null
        )
    
    val allMeasurements: StateFlow<List<BodyMeasurement>> = 
        userRepository.getAllMeasurementsAscending()
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = emptyList()
            )
    
    val latestMeasurement: StateFlow<BodyMeasurement?> = userRepository.getLatestMeasurement()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = null
        )
    
    fun updateProfile(
        name: String,
        age: Int,
        sex: String,
        goal: String,
        targetWeight: Float? = null,
        targetBodyFatPercentage: Float? = null
    ) {
        viewModelScope.launch {
            val current = userProfile.value
            if (current != null) {
                userRepository.updateUserProfile(
                    current.copy(
                        name = name,
                        age = age,
                        sex = sex,
                        goal = goal,
                        targetWeight = targetWeight,
                        targetBodyFatPercentage = targetBodyFatPercentage,
                        updatedAt = Date()
                    )
                )
            } else {
                userRepository.insertUserProfile(
                    UserProfile(
                        name = name,
                        age = age,
                        sex = sex,
                        goal = goal,
                        targetWeight = targetWeight,
                        targetBodyFatPercentage = targetBodyFatPercentage
                    )
                )
            }
        }
    }
    
    fun addMeasurement(
        weight: Float,
        bodyFatPercentage: Float? = null,
        date: Date = Date(),
        height: Float? = null,
        chest: Float? = null,
        waist: Float? = null,
        biceps: Float? = null,
        thighs: Float? = null,
        notes: String? = null
    ) {
        viewModelScope.launch {
            // Calcola massa magra e massa grassa se abbiamo la % di grasso
            val fatMass = if (bodyFatPercentage != null) {
                weight * (bodyFatPercentage / 100f)
            } else null
            
            val leanMass = if (bodyFatPercentage != null) {
                weight - (weight * (bodyFatPercentage / 100f))
            } else null
            
            userRepository.insertMeasurement(
                BodyMeasurement(
                    date = date,
                    weight = weight,
                    bodyFatPercentage = bodyFatPercentage,
                    leanMass = leanMass,
                    fatMass = fatMass,
                    height = height,
                    chest = chest,
                    waist = waist,
                    biceps = biceps,
                    thighs = thighs,
                    notes = notes
                )
            )
        }
    }
    
    fun deleteMeasurement(measurement: BodyMeasurement) {
        viewModelScope.launch {
            userRepository.deleteMeasurement(measurement)
        }
    }
}
