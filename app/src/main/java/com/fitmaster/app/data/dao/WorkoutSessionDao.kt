package com.fitmaster.app.data.dao

import androidx.room.*
import com.fitmaster.app.data.entity.WorkoutSession
import com.fitmaster.app.data.entity.WorkoutSessionExercise
import kotlinx.coroutines.flow.Flow
import java.util.Date

@Dao
interface WorkoutSessionDao {
    // WorkoutSession queries
    @Query("SELECT * FROM workout_sessions ORDER BY startTime DESC")
    fun getAllSessions(): Flow<List<WorkoutSession>>

    @Query("SELECT * FROM workout_sessions WHERE id = :id")
    fun getSessionById(id: Long): Flow<WorkoutSession?>

    @Query("SELECT * FROM workout_sessions WHERE isCompleted = 0 ORDER BY startTime DESC LIMIT 1")
    fun getActiveSession(): Flow<WorkoutSession?>

    @Query("SELECT * FROM workout_sessions WHERE isCompleted = 1 ORDER BY startTime DESC")
    fun getCompletedSessions(): Flow<List<WorkoutSession>>

    @Query("SELECT * FROM workout_sessions WHERE startTime >= :startDate AND startTime <= :endDate ORDER BY startTime DESC")
    fun getSessionsByDateRange(startDate: Date, endDate: Date): Flow<List<WorkoutSession>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSession(session: WorkoutSession): Long

    @Update
    suspend fun updateSession(session: WorkoutSession)

    @Delete
    suspend fun deleteSession(session: WorkoutSession)

    // WorkoutSessionExercise queries
    @Query("SELECT * FROM workout_session_exercises WHERE sessionId = :sessionId")
    fun getSessionExercises(sessionId: Long): Flow<List<WorkoutSessionExercise>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSessionExercise(exercise: WorkoutSessionExercise): Long

    @Update
    suspend fun updateSessionExercise(exercise: WorkoutSessionExercise)

    @Delete
    suspend fun deleteSessionExercise(exercise: WorkoutSessionExercise)

    @Query("DELETE FROM workout_session_exercises WHERE sessionId = :sessionId")
    suspend fun deleteSessionExercises(sessionId: Long)
}
