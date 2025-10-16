package com.fitmaster.app.data.dao

import androidx.room.*
import com.fitmaster.app.data.entity.BodyMeasurement
import com.fitmaster.app.data.entity.UserProfile
import kotlinx.coroutines.flow.Flow
import java.util.Date

@Dao
interface UserDao {
    // UserProfile queries
    @Query("SELECT * FROM user_profile WHERE id = 1")
    fun getUserProfile(): Flow<UserProfile?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUserProfile(profile: UserProfile)

    @Update
    suspend fun updateUserProfile(profile: UserProfile)

    // BodyMeasurement queries
    @Query("SELECT * FROM body_measurements ORDER BY date DESC")
    fun getAllMeasurements(): Flow<List<BodyMeasurement>>

    @Query("SELECT * FROM body_measurements WHERE id = :id")
    fun getMeasurementById(id: Long): Flow<BodyMeasurement?>

    @Query("SELECT * FROM body_measurements ORDER BY date DESC LIMIT 1")
    fun getLatestMeasurement(): Flow<BodyMeasurement?>

    @Query("SELECT * FROM body_measurements WHERE date >= :startDate AND date <= :endDate ORDER BY date ASC")
    fun getMeasurementsByDateRange(startDate: Date, endDate: Date): Flow<List<BodyMeasurement>>

    @Query("SELECT * FROM body_measurements ORDER BY date ASC")
    fun getAllMeasurementsAscending(): Flow<List<BodyMeasurement>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMeasurement(measurement: BodyMeasurement): Long

    @Update
    suspend fun updateMeasurement(measurement: BodyMeasurement)

    @Delete
    suspend fun deleteMeasurement(measurement: BodyMeasurement)
}
