package com.fitmaster.app.data.repository

import com.fitmaster.app.data.dao.UserDao
import com.fitmaster.app.data.entity.BodyMeasurement
import com.fitmaster.app.data.entity.UserProfile
import kotlinx.coroutines.flow.Flow
import java.util.Date

class UserRepository(private val userDao: UserDao) {
    
    // UserProfile methods
    fun getUserProfile(): Flow<UserProfile?> = userDao.getUserProfile()
    
    suspend fun insertUserProfile(profile: UserProfile) = userDao.insertUserProfile(profile)
    
    suspend fun updateUserProfile(profile: UserProfile) = userDao.updateUserProfile(profile)
    
    // BodyMeasurement methods
    fun getAllMeasurements(): Flow<List<BodyMeasurement>> = userDao.getAllMeasurements()
    
    fun getMeasurementById(id: Long): Flow<BodyMeasurement?> = userDao.getMeasurementById(id)
    
    fun getLatestMeasurement(): Flow<BodyMeasurement?> = userDao.getLatestMeasurement()
    
    fun getMeasurementsByDateRange(startDate: Date, endDate: Date): Flow<List<BodyMeasurement>> = 
        userDao.getMeasurementsByDateRange(startDate, endDate)
    
    fun getAllMeasurementsAscending(): Flow<List<BodyMeasurement>> = 
        userDao.getAllMeasurementsAscending()
    
    suspend fun insertMeasurement(measurement: BodyMeasurement): Long = 
        userDao.insertMeasurement(measurement)
    
    suspend fun updateMeasurement(measurement: BodyMeasurement) = 
        userDao.updateMeasurement(measurement)
    
    suspend fun deleteMeasurement(measurement: BodyMeasurement) = 
        userDao.deleteMeasurement(measurement)
}
