package com.fitmaster.app.service

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Binder
import android.os.Build
import android.os.IBinder
import androidx.core.app.NotificationCompat
import com.fitmaster.app.MainActivity
import com.fitmaster.app.R
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class WorkoutTimerService : Service() {
    
    private val binder = LocalBinder()
    private val serviceScope = CoroutineScope(Dispatchers.Default + SupervisorJob())
    
    private var timerJob: Job? = null
    
    private val _timeRemaining = MutableStateFlow(0)
    val timeRemaining: StateFlow<Int> = _timeRemaining
    
    private val _isRunning = MutableStateFlow(false)
    val isRunning: StateFlow<Boolean> = _isRunning
    
    private var isResting = false
    private var exerciseName = ""
    private var currentSet = 0
    private var totalSets = 0
    private var planId: Long = 0
    private var dayId: Long = 0
    
    companion object {
        private const val NOTIFICATION_ID = 1
        private const val CHANNEL_ID = "workout_timer_channel"
        
        const val ACTION_START_TIMER = "START_TIMER"
        const val ACTION_PAUSE_TIMER = "PAUSE_TIMER"
        const val ACTION_RESUME_TIMER = "RESUME_TIMER"
        const val ACTION_STOP_TIMER = "STOP_TIMER"
        
        const val EXTRA_TIME_SECONDS = "time_seconds"
        const val EXTRA_IS_RESTING = "is_resting"
        const val EXTRA_EXERCISE_NAME = "exercise_name"
        const val EXTRA_CURRENT_SET = "current_set"
        const val EXTRA_TOTAL_SETS = "total_sets"
        const val EXTRA_PLAN_ID = "plan_id"
        const val EXTRA_DAY_ID = "day_id"
    }
    
    inner class LocalBinder : Binder() {
        fun getService(): WorkoutTimerService = this@WorkoutTimerService
    }
    
    override fun onCreate() {
        super.onCreate()
        createNotificationChannel()
    }
    
    override fun onBind(intent: Intent?): IBinder = binder
    
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        when (intent?.action) {
            ACTION_START_TIMER -> {
                val timeSeconds = intent.getIntExtra(EXTRA_TIME_SECONDS, 60)
                isResting = intent.getBooleanExtra(EXTRA_IS_RESTING, false)
                exerciseName = intent.getStringExtra(EXTRA_EXERCISE_NAME) ?: ""
                currentSet = intent.getIntExtra(EXTRA_CURRENT_SET, 1)
                totalSets = intent.getIntExtra(EXTRA_TOTAL_SETS, 3)
                planId = intent.getLongExtra(EXTRA_PLAN_ID, 0)
                dayId = intent.getLongExtra(EXTRA_DAY_ID, 0)
                startTimer(timeSeconds)
            }
            ACTION_PAUSE_TIMER -> pauseTimer()
            ACTION_RESUME_TIMER -> resumeTimer()
            ACTION_STOP_TIMER -> stopTimer()
        }
        return START_STICKY
    }
    
    private fun startTimer(seconds: Int) {
        _timeRemaining.value = seconds
        _isRunning.value = true
        
        startForeground(NOTIFICATION_ID, createNotification())
        
        timerJob?.cancel()
        timerJob = serviceScope.launch {
            while (_timeRemaining.value > 0 && _isRunning.value) {
                delay(1000)
                _timeRemaining.value -= 1
                updateNotification()
            }
            
            if (_timeRemaining.value <= 0) {
                // Timer finished
                _isRunning.value = false
            }
        }
    }
    
    private fun pauseTimer() {
        _isRunning.value = false
        timerJob?.cancel()
        updateNotification()
    }
    
    private fun resumeTimer() {
        if (_timeRemaining.value > 0) {
            _isRunning.value = true
            timerJob?.cancel()
            timerJob = serviceScope.launch {
                while (_timeRemaining.value > 0 && _isRunning.value) {
                    delay(1000)
                    _timeRemaining.value -= 1
                    updateNotification()
                }
                
                if (_timeRemaining.value <= 0) {
                    _isRunning.value = false
                }
            }
        }
    }
    
    fun stopTimer() {
        try {
            _isRunning.value = false
            _timeRemaining.value = 0
            timerJob?.cancel()
            stopForeground(STOP_FOREGROUND_REMOVE)
            stopSelf()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
    
    fun updateTimerInfo(isResting: Boolean, exerciseName: String, currentSet: Int, totalSets: Int) {
        this.isResting = isResting
        this.exerciseName = exerciseName
        this.currentSet = currentSet
        this.totalSets = totalSets
        updateNotification()
    }
    
    fun addTime(seconds: Int) {
        _timeRemaining.value += seconds
        updateNotification()
    }
    
    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                "Workout Timer",
                NotificationManager.IMPORTANCE_LOW
            ).apply {
                description = "Shows workout timer progress"
                setShowBadge(false)
            }
            
            val notificationManager = getSystemService(NotificationManager::class.java)
            notificationManager.createNotificationChannel(channel)
        }
    }
    
    private fun createNotification(): Notification {
        // Create deep link intent to active workout screen
        val intent = Intent(this, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_SINGLE_TOP
            putExtra("navigate_to", "active_workout")
            putExtra("plan_id", planId)
            putExtra("day_id", dayId)
        }
        val pendingIntent = PendingIntent.getActivity(
            this,
            0,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
        
        val minutes = _timeRemaining.value / 60
        val seconds = _timeRemaining.value % 60
        val timeText = String.format("%02d:%02d", minutes, seconds)
        
        val title = if (isResting) {
            "Rest Time"
        } else {
            exerciseName.ifEmpty { "Workout in Progress" }
        }
        
        val text = if (isResting) {
            timeText
        } else {
            "Set $currentSet/$totalSets - $timeText"
        }
        
        return NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle(title)
            .setContentText(text)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentIntent(pendingIntent)
            .setOngoing(true)
            .setSilent(true)
            .setCategory(NotificationCompat.CATEGORY_WORKOUT)
            .setPriority(NotificationCompat.PRIORITY_LOW)
            .build()
    }
    
    private fun updateNotification() {
        try {
            val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.notify(NOTIFICATION_ID, createNotification())
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
    
    override fun onDestroy() {
        super.onDestroy()
        timerJob?.cancel()
        serviceScope.cancel()
    }
}
