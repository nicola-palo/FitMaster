package com.fitmaster.app.data.dao

import androidx.room.*
import com.fitmaster.app.data.entity.WorkoutPlan
import com.fitmaster.app.data.entity.WorkoutPlanDay
import com.fitmaster.app.data.entity.WorkoutPlanExercise
import kotlinx.coroutines.flow.Flow

@Dao
interface WorkoutPlanDao {
    // WorkoutPlan queries
    @Query("SELECT * FROM workout_plans ORDER BY createdAt DESC")
    fun getAllWorkoutPlans(): Flow<List<WorkoutPlan>>

    @Query("SELECT * FROM workout_plans WHERE id = :id")
    fun getWorkoutPlanById(id: Long): Flow<WorkoutPlan?>

    @Query("SELECT * FROM workout_plans WHERE isActive = 1 LIMIT 1")
    fun getActiveWorkoutPlan(): Flow<WorkoutPlan?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWorkoutPlan(plan: WorkoutPlan): Long

    @Update
    suspend fun updateWorkoutPlan(plan: WorkoutPlan)

    @Delete
    suspend fun deleteWorkoutPlan(plan: WorkoutPlan)

    @Query("UPDATE workout_plans SET isActive = 0")
    suspend fun deactivateAllPlans()

    @Query("UPDATE workout_plans SET isActive = 1 WHERE id = :planId")
    suspend fun setActivePlan(planId: Long)

    @Query("SELECT * FROM workout_plans WHERE isFavorite = 1 ORDER BY createdAt DESC")
    fun getFavoritePlans(): Flow<List<WorkoutPlan>>

    @Query("SELECT COUNT(*) FROM workout_plans WHERE isFavorite = 1")
    suspend fun getFavoriteCount(): Int

    @Query("UPDATE workout_plans SET isFavorite = :isFavorite WHERE id = :planId")
    suspend fun updateFavoriteStatus(planId: Long, isFavorite: Boolean)

    // WorkoutPlanDay queries
    @Query("SELECT * FROM workout_plan_days WHERE planId = :planId ORDER BY dayOfWeek ASC")
    fun getPlanDays(planId: Long): Flow<List<WorkoutPlanDay>>

    @Query("SELECT * FROM workout_plan_days WHERE id = :dayId")
    fun getPlanDayById(dayId: Long): Flow<WorkoutPlanDay?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPlanDay(day: WorkoutPlanDay): Long

    @Delete
    suspend fun deletePlanDay(day: WorkoutPlanDay)

    // WorkoutPlanExercise queries
    @Query("SELECT * FROM workout_plan_exercises WHERE planDayId = :dayId ORDER BY orderIndex ASC")
    fun getPlanDayExercises(dayId: Long): Flow<List<WorkoutPlanExercise>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPlanExercise(exercise: WorkoutPlanExercise): Long

    @Update
    suspend fun updatePlanExercise(exercise: WorkoutPlanExercise)

    @Delete
    suspend fun deletePlanExercise(exercise: WorkoutPlanExercise)

    @Query("DELETE FROM workout_plan_days WHERE planId = :planId")
    suspend fun deletePlanDays(planId: Long)

    @Query("DELETE FROM workout_plan_exercises WHERE planDayId = :dayId")
    suspend fun deletePlanDayExercises(dayId: Long)
}
