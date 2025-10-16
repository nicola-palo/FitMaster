package com.fitmaster.app.data.repository

import com.fitmaster.app.data.dao.WorkoutSessionDao
import com.fitmaster.app.data.entity.WorkoutSession
import com.fitmaster.app.data.entity.WorkoutSessionExercise
import kotlinx.coroutines.flow.Flow
import java.util.Date

class WorkoutSessionRepository(private val workoutSessionDao: WorkoutSessionDao) {
    
    // WorkoutSession methods
    fun getAllSessions(): Flow<List<WorkoutSession>> = workoutSessionDao.getAllSessions()
    
    fun getSessionById(id: Long): Flow<WorkoutSession?> = workoutSessionDao.getSessionById(id)
    
    fun getActiveSession(): Flow<WorkoutSession?> = workoutSessionDao.getActiveSession()
    
    fun getCompletedSessions(): Flow<List<WorkoutSession>> = workoutSessionDao.getCompletedSessions()
    
    fun getSessionsByDateRange(startDate: Date, endDate: Date): Flow<List<WorkoutSession>> = 
        workoutSessionDao.getSessionsByDateRange(startDate, endDate)
    
    suspend fun insertSession(session: WorkoutSession): Long = workoutSessionDao.insertSession(session)
    
    suspend fun updateSession(session: WorkoutSession) = workoutSessionDao.updateSession(session)
    
    suspend fun deleteSession(session: WorkoutSession) {
        workoutSessionDao.deleteSessionExercises(session.id)
        workoutSessionDao.deleteSession(session)
    }
    
    // WorkoutSessionExercise methods
    fun getSessionExercises(sessionId: Long): Flow<List<WorkoutSessionExercise>> = 
        workoutSessionDao.getSessionExercises(sessionId)
    
    suspend fun insertSessionExercise(exercise: WorkoutSessionExercise): Long = 
        workoutSessionDao.insertSessionExercise(exercise)
    
    suspend fun updateSessionExercise(exercise: WorkoutSessionExercise) = 
        workoutSessionDao.updateSessionExercise(exercise)
    
    suspend fun deleteSessionExercise(exercise: WorkoutSessionExercise) = 
        workoutSessionDao.deleteSessionExercise(exercise)
}
