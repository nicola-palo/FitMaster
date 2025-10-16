package com.fitmaster.app.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.fitmaster.app.data.converter.Converters
import com.fitmaster.app.data.dao.*
import com.fitmaster.app.data.entity.*

@Database(
    entities = [
        Exercise::class,
        WorkoutPlan::class,
        WorkoutPlanDay::class,
        WorkoutPlanExercise::class,
        WorkoutSession::class,
        WorkoutSessionExercise::class,
        BodyMeasurement::class,
        UserProfile::class
    ],
    version = 3,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class FitMasterDatabase : RoomDatabase() {
    
    abstract fun exerciseDao(): ExerciseDao
    abstract fun workoutPlanDao(): WorkoutPlanDao
    abstract fun workoutSessionDao(): WorkoutSessionDao
    abstract fun userDao(): UserDao

    companion object {
        @Volatile
        private var INSTANCE: FitMasterDatabase? = null

        fun getDatabase(context: Context): FitMasterDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    FitMasterDatabase::class.java,
                    "fitmaster_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
