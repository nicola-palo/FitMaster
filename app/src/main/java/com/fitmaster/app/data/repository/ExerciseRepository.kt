package com.fitmaster.app.data.repository

import com.fitmaster.app.data.dao.ExerciseDao
import com.fitmaster.app.data.entity.Exercise
import kotlinx.coroutines.flow.Flow

class ExerciseRepository(private val exerciseDao: ExerciseDao) {
    
    fun getAllExercises(): Flow<List<Exercise>> = exerciseDao.getAllExercises()
    
    fun getExerciseById(id: Long): Flow<Exercise?> = exerciseDao.getExerciseById(id)
    
    fun getExercisesByCategory(category: String): Flow<List<Exercise>> = 
        exerciseDao.getExercisesByCategory(category)
    
    fun getExercisesByMuscleGroup(muscleGroup: String): Flow<List<Exercise>> = 
        exerciseDao.getExercisesByMuscleGroup(muscleGroup)
    
    fun getExercisesByDifficulty(difficulty: String): Flow<List<Exercise>> = 
        exerciseDao.getExercisesByDifficulty(difficulty)
    
    fun searchExercises(query: String): Flow<List<Exercise>> = 
        exerciseDao.searchExercises(query)
    
    suspend fun insertExercise(exercise: Exercise): Long = exerciseDao.insert(exercise)
    
    suspend fun insertAllExercises(exercises: List<Exercise>) = exerciseDao.insertAll(exercises)
    
    suspend fun updateExercise(exercise: Exercise) = exerciseDao.update(exercise)
    
    suspend fun deleteExercise(exercise: Exercise) = exerciseDao.delete(exercise)
    
    suspend fun deleteExerciseById(id: Long) = exerciseDao.deleteById(id)
}
