package com.fitmaster.app.service

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Build
import android.os.IBinder
import com.fitmaster.app.data.model.ActiveWorkoutState
import kotlinx.coroutines.flow.StateFlow

class WorkoutServiceManager(private val context: Context) {
    
    private var service: WorkoutTimerService? = null
    private var isBound = false
    
    private val serviceConnection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, binder: IBinder?) {
            val localBinder = binder as WorkoutTimerService.LocalBinder
            service = localBinder.getService()
            isBound = true
        }
        
        override fun onServiceDisconnected(name: ComponentName?) {
            service = null
            isBound = false
        }
    }
    
    fun bindService() {
        if (!isBound) {
            val intent = Intent(context, WorkoutTimerService::class.java)
            context.bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE)
        }
    }
    
    fun unbindService() {
        if (isBound) {
            context.unbindService(serviceConnection)
            isBound = false
            service = null
        }
    }
    
    fun startRestTimer(seconds: Int, exerciseName: String, currentSet: Int, totalSets: Int, planId: Long, dayId: Long) {
        val intent = Intent(context, WorkoutTimerService::class.java).apply {
            action = WorkoutTimerService.ACTION_START_TIMER
            putExtra(WorkoutTimerService.EXTRA_TIME_SECONDS, seconds)
            putExtra(WorkoutTimerService.EXTRA_IS_RESTING, true)
            putExtra(WorkoutTimerService.EXTRA_EXERCISE_NAME, exerciseName)
            putExtra(WorkoutTimerService.EXTRA_CURRENT_SET, currentSet)
            putExtra(WorkoutTimerService.EXTRA_TOTAL_SETS, totalSets)
            putExtra(WorkoutTimerService.EXTRA_PLAN_ID, planId)
            putExtra(WorkoutTimerService.EXTRA_DAY_ID, dayId)
        }
        startService(intent)
    }
    
    fun updateExerciseInfo(exerciseName: String, currentSet: Int, totalSets: Int) {
        service?.updateTimerInfo(false, exerciseName, currentSet, totalSets)
    }
    
    fun addRestTime(seconds: Int) {
        service?.addTime(seconds)
    }
    
    fun stopTimer() {
        try {
            // Stop the timer via bound service first
            if (isBound && service != null) {
                try {
                    service?.stopTimer()
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
            
            // Unbind from service
            try {
                unbindService()
            } catch (e: Exception) {
                e.printStackTrace()
            }
            
            // Send stop intent to ensure service stops completely
            val intent = Intent(context, WorkoutTimerService::class.java).apply {
                action = WorkoutTimerService.ACTION_STOP_TIMER
            }
            
            try {
                context.startService(intent)
            } catch (e: Exception) {
                // Service not running, that's fine
                e.printStackTrace()
            }
            
            // Force stop the service
            try {
                context.stopService(Intent(context, WorkoutTimerService::class.java))
            } catch (e: Exception) {
                e.printStackTrace()
            }
        } catch (e: Exception) {
            // Service might not be running, that's okay
            e.printStackTrace()
        }
    }
    
    fun getTimeRemaining(): StateFlow<Int>? {
        return service?.timeRemaining
    }
    
    private fun startService(intent: Intent) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            context.startForegroundService(intent)
        } else {
            context.startService(intent)
        }
    }
}
