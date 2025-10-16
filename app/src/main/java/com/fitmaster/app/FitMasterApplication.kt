package com.fitmaster.app

import android.app.Application
import com.fitmaster.app.data.database.FitMasterDatabase
import com.fitmaster.app.data.repository.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class FitMasterApplication : Application() {
    
    private val applicationScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)
    
    val database by lazy { FitMasterDatabase.getDatabase(this) }
    
    val exerciseRepository by lazy { ExerciseRepository(database.exerciseDao()) }
    val workoutPlanRepository by lazy { WorkoutPlanRepository(database.workoutPlanDao()) }
    val workoutSessionRepository by lazy { WorkoutSessionRepository(database.workoutSessionDao()) }
    val userRepository by lazy { UserRepository(database.userDao()) }
    
    override fun onCreate() {
        super.onCreate()
        
        // Initialize database with sample data
        applicationScope.launch {
            initializeSampleData()
        }
    }
    
    private suspend fun initializeSampleData() {
        try {
            val exerciseDao = database.exerciseDao()
            val userDao = database.userDao()
            
            // Check if data already exists
            val existingExercises = exerciseDao.getAllExercises().first()
            
            if (existingExercises.isEmpty()) {
                // Insert sample exercises
                SampleData.getDefaultExercises().forEach { exercise ->
                    exerciseDao.insert(exercise)
                }
            }
            
            // Check if user profile exists, if not create default
            val existingProfile = userDao.getUserProfile().first()
            if (existingProfile == null) {
                userDao.insertUserProfile(SampleData.getDefaultUserProfile())
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}
