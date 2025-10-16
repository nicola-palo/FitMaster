package com.fitmaster.app.data.repository

import com.fitmaster.app.data.dao.WorkoutPlanDao
import com.fitmaster.app.data.entity.WorkoutPlan
import com.fitmaster.app.data.entity.WorkoutPlanDay
import com.fitmaster.app.data.entity.WorkoutPlanExercise
import kotlinx.coroutines.flow.Flow

class WorkoutPlanRepository(private val workoutPlanDao: WorkoutPlanDao) {
    
    // WorkoutPlan methods
    fun getAllWorkoutPlans(): Flow<List<WorkoutPlan>> = workoutPlanDao.getAllWorkoutPlans()
    
    fun getWorkoutPlanById(id: Long): Flow<WorkoutPlan?> = workoutPlanDao.getWorkoutPlanById(id)
    
    fun getActiveWorkoutPlan(): Flow<WorkoutPlan?> = workoutPlanDao.getActiveWorkoutPlan()
    
    suspend fun insertWorkoutPlan(plan: WorkoutPlan): Long = workoutPlanDao.insertWorkoutPlan(plan)
    
    suspend fun updateWorkoutPlan(plan: WorkoutPlan) = workoutPlanDao.updateWorkoutPlan(plan)
    
    suspend fun deleteWorkoutPlan(plan: WorkoutPlan) {
        // Delete associated days and exercises first
        val planDays = workoutPlanDao.getPlanDays(plan.id)
        workoutPlanDao.deletePlanDays(plan.id)
        workoutPlanDao.deleteWorkoutPlan(plan)
    }
    
    suspend fun setActivePlan(planId: Long) {
        workoutPlanDao.deactivateAllPlans()
        workoutPlanDao.setActivePlan(planId)
    }
    
    suspend fun deactivateAllPlans() {
        workoutPlanDao.deactivateAllPlans()
    }
    
    // Favorite methods
    fun getFavoritePlans(): Flow<List<WorkoutPlan>> = workoutPlanDao.getFavoritePlans()
    
    suspend fun getFavoriteCount(): Int = workoutPlanDao.getFavoriteCount()
    
    suspend fun toggleFavorite(planId: Long, currentStatus: Boolean): Boolean {
        val newStatus = !currentStatus
        // If setting to favorite, check if we already have 3 favorites
        if (newStatus) {
            val count = workoutPlanDao.getFavoriteCount()
            if (count >= 3) {
                return false // Cannot add more than 3 favorites
            }
        }
        workoutPlanDao.updateFavoriteStatus(planId, newStatus)
        return true
    }
    
    // WorkoutPlanDay methods
    fun getPlanDays(planId: Long): Flow<List<WorkoutPlanDay>> = workoutPlanDao.getPlanDays(planId)
    
    fun getPlanDayById(dayId: Long): Flow<WorkoutPlanDay?> = workoutPlanDao.getPlanDayById(dayId)
    
    suspend fun insertPlanDay(day: WorkoutPlanDay): Long = workoutPlanDao.insertPlanDay(day)
    
    suspend fun deletePlanDay(day: WorkoutPlanDay) {
        workoutPlanDao.deletePlanDayExercises(day.id)
        workoutPlanDao.deletePlanDay(day)
    }
    
    // WorkoutPlanExercise methods
    fun getPlanDayExercises(dayId: Long): Flow<List<WorkoutPlanExercise>> = 
        workoutPlanDao.getPlanDayExercises(dayId)
    
    suspend fun insertPlanExercise(exercise: WorkoutPlanExercise): Long = 
        workoutPlanDao.insertPlanExercise(exercise)
    
    suspend fun updatePlanExercise(exercise: WorkoutPlanExercise) = 
        workoutPlanDao.updatePlanExercise(exercise)
    
    suspend fun deletePlanExercise(exercise: WorkoutPlanExercise) = 
        workoutPlanDao.deletePlanExercise(exercise)
    
    suspend fun deletePlanExerciseById(exerciseId: Long) {
        val exercise = WorkoutPlanExercise(
            id = exerciseId,
            planDayId = 0, // Not used for deletion
            exerciseId = 0,
            sets = 0,
            reps = 0,
            restSeconds = 0,
            orderIndex = 0
        )
        workoutPlanDao.deletePlanExercise(exercise)
    }
}
