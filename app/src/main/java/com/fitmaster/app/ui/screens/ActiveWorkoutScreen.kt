package com.fitmaster.app.ui.screens

import android.content.Context
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.fitmaster.app.data.model.ExerciseWithDetails
import com.fitmaster.app.service.WorkoutServiceManager
import com.fitmaster.app.ui.components.TopAppBarWithBack
import com.fitmaster.app.ui.viewmodel.ActiveWorkoutViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlin.math.roundToInt

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ActiveWorkoutScreen(
    navController: NavController,
    viewModel: ActiveWorkoutViewModel,
    planId: Long,
    dayId: Long
) {
    val context = LocalContext.current
    val serviceManager = remember { WorkoutServiceManager(context) }
    
    val workoutState by viewModel.workoutState.collectAsState()
    val elapsedTime by viewModel.elapsedTime.collectAsState()
    val restTimeRemaining by viewModel.restTimeRemaining.collectAsState()
    val isPaused by viewModel.isPaused.collectAsState()
    val isCompleted by viewModel.isCompleted.collectAsState()
    
    // Service time remaining (when available)
    val serviceTimeRemaining by (serviceManager.getTimeRemaining() ?: MutableStateFlow(0)).collectAsState()
    val displayRestTime = if (serviceTimeRemaining > 0) serviceTimeRemaining else restTimeRemaining
    
    LaunchedEffect(planId, dayId) {
        if (workoutState == null && !isCompleted) {
            viewModel.startWorkout(planId, dayId)
        }
        serviceManager.bindService()
    }
    
    DisposableEffect(Unit) {
        onDispose {
            serviceManager.unbindService()
        }
    }
    
    // Start service timer when resting
    LaunchedEffect(workoutState?.isResting) {
        val state = workoutState
        if (state != null && state.isResting) {
            val currentExercise = state.exercises.getOrNull(state.currentExerciseIndex)
            if (currentExercise != null) {
                serviceManager.startRestTimer(
                    seconds = currentExercise.planExercise.restSeconds,
                    exerciseName = currentExercise.exercise.name,
                    currentSet = state.currentSet,
                    totalSets = currentExercise.planExercise.sets,
                    planId = planId,
                    dayId = dayId
                )
            }
        }
    }
    
    // Update exercise info when exercise changes
    LaunchedEffect(workoutState?.currentExerciseIndex, workoutState?.currentSet) {
        val state = workoutState
        if (state != null && !state.isResting) {
            val currentExercise = state.exercises.getOrNull(state.currentExerciseIndex)
            if (currentExercise != null) {
                serviceManager.updateExerciseInfo(
                    exerciseName = currentExercise.exercise.name,
                    currentSet = state.currentSet,
                    totalSets = currentExercise.planExercise.sets
                )
            }
        }
    }
    
    // Stop service when workout completes
    LaunchedEffect(isCompleted) {
        if (isCompleted) {
            serviceManager.stopTimer()
        }
    }
    
    Scaffold(
        topBar = {
            TopAppBarWithBack(
                title = "Workout",
                onBackClick = { navController.navigateUp() }
            )
        }
    ) { padding ->
        when {
            isCompleted -> {
                // Workout Completed Screen
                WorkoutCompletedScreen(
                    elapsedTime = elapsedTime,
                    onFinish = { navController.navigateUp() },
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(padding)
                )
            }
            workoutState != null -> {
                val state = workoutState!!
                if (state.isResting) {
                    // Rest Screen
                    RestingScreen(
                        restTimeRemaining = displayRestTime,
                        onSkipRest = { 
                            serviceManager.stopTimer()
                            viewModel.skipRest()
                        },
                        onAddTime = { 
                            serviceManager.addRestTime(60)
                            viewModel.addRestTime(60)
                        },
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(padding)
                    )
                } else {
                    // Exercise Screen
                    val currentExercise = state.exercises.getOrNull(state.currentExerciseIndex)
                    
                    if (currentExercise != null) {
                        ExerciseScreen(
                            currentExercise = currentExercise,
                            currentSet = state.currentSet,
                            elapsedTime = elapsedTime,
                            isPaused = isPaused,
                            exerciseIndex = state.currentExerciseIndex,
                            totalExercises = state.exercises.size,
                            onCompleteSet = { viewModel.completeSet() },
                            onPauseResume = {
                                if (isPaused) viewModel.resumeWorkout()
                                else viewModel.pauseWorkout()
                            },
                            onSkipExercise = { viewModel.skipExercise() },
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(padding)
                        )
                    }
                }
            }
            else -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(padding),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }
        }
    }
}

@Composable
fun RestingScreen(
    restTimeRemaining: Int,
    onSkipRest: () -> Unit,
    onAddTime: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            "Rest Time",
            style = MaterialTheme.typography.headlineLarge,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
        )
        
        Spacer(modifier = Modifier.height(32.dp))
        
        // Large rest timer
        Surface(
            modifier = Modifier.size(200.dp),
            shape = RoundedCornerShape(100.dp),
            color = MaterialTheme.colorScheme.primaryContainer
        ) {
            Box(contentAlignment = Alignment.Center) {
                Text(
                    restTimeRemaining.toString(),
                    style = MaterialTheme.typography.displayLarge.copy(
                        fontSize = MaterialTheme.typography.displayLarge.fontSize * 1.5f
                    ),
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )
            }
        }
        
        Spacer(modifier = Modifier.height(16.dp))
        
        Text(
            "seconds remaining",
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        
        Spacer(modifier = Modifier.height(48.dp))
        
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            OutlinedButton(
                onClick = onAddTime,
                modifier = Modifier
                    .weight(1f)
                    .height(56.dp)
            ) {
                Icon(Icons.Default.Add, contentDescription = null)
                Spacer(modifier = Modifier.width(8.dp))
                Text("1m")
            }
            
            Button(
                onClick = onSkipRest,
                modifier = Modifier
                    .weight(1f)
                    .height(56.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary
                )
            ) {
                Text("Skip Rest", style = MaterialTheme.typography.titleMedium)
            }
        }
    }
}

@Composable
fun ExerciseScreen(
    currentExercise: ExerciseWithDetails,
    currentSet: Int,
    elapsedTime: Long,
    isPaused: Boolean,
    exerciseIndex: Int,
    totalExercises: Int,
    onCompleteSet: () -> Unit,
    onPauseResume: () -> Unit,
    onSkipExercise: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        // Progress indicator
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                "Exercise ${exerciseIndex + 1}/$totalExercises",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.primary
            )
            LinearProgressIndicator(
                progress = (exerciseIndex + 1).toFloat() / totalExercises.toFloat(),
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 16.dp)
            )
        }
        
        // Timer Display
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            TimeDisplay(label = "Hours", value = (elapsedTime / 3600).toInt())
            Text(":", style = MaterialTheme.typography.displayLarge, modifier = Modifier.padding(horizontal = 8.dp))
            TimeDisplay(label = "Minutes", value = ((elapsedTime % 3600) / 60).toInt())
            Text(":", style = MaterialTheme.typography.displayLarge, modifier = Modifier.padding(horizontal = 8.dp))
            TimeDisplay(label = "Seconds", value = (elapsedTime % 60).toInt())
        }
        
        // Current Exercise Info
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                currentExercise.exercise.name,
                style = MaterialTheme.typography.displaySmall,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                "Set $currentSet/${currentExercise.planExercise.sets}",
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.Bold
            )
        }
        
        // Exercise Details Card
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceVariant
            )
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("Reps", style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    Text(
                        currentExercise.planExercise.reps.toString(),
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold
                    )
                }
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("Rest", style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    Text(
                        "${currentExercise.planExercise.restSeconds}s",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
        
        Spacer(modifier = Modifier.weight(1f))
        
        // Action Buttons
        Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
            Button(
                onClick = onCompleteSet,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary
                )
            ) {
                Icon(Icons.Default.CheckCircle, contentDescription = null)
                Spacer(modifier = Modifier.width(8.dp))
                Text("Set Completed", style = MaterialTheme.typography.titleMedium)
            }
            
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                OutlinedButton(
                    onClick = onPauseResume,
                    modifier = Modifier
                        .weight(1f)
                        .height(48.dp)
                ) {
                    Icon(
                        if (isPaused) Icons.Default.PlayArrow else Icons.Default.Pause,
                        contentDescription = null
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(if (isPaused) "Resume" else "Pause")
                }
                
                OutlinedButton(
                    onClick = onSkipExercise,
                    modifier = Modifier
                        .weight(1f)
                        .height(48.dp)
                ) {
                    Icon(Icons.Default.SkipNext, contentDescription = null)
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("Skip")
                }
            }
        }
    }
}

@Composable
fun TimeDisplay(label: String, value: Int) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Surface(
            modifier = Modifier.size(64.dp),
            shape = RoundedCornerShape(12.dp),
            color = MaterialTheme.colorScheme.surfaceVariant
        ) {
            Box(contentAlignment = Alignment.Center) {
                Text(
                    String.format("%02d", value),
                    style = MaterialTheme.typography.displaySmall,
                    fontWeight = FontWeight.Bold
                )
            }
        }
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            label,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Composable
fun WorkoutCompletedScreen(
    elapsedTime: Long,
    onFinish: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            Icons.Default.CheckCircle,
            contentDescription = null,
            modifier = Modifier.size(120.dp),
            tint = MaterialTheme.colorScheme.primary
        )
        
        Spacer(modifier = Modifier.height(32.dp))
        
        Text(
            "Workout Completed!",
            style = MaterialTheme.typography.displaySmall,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
        )
        
        Spacer(modifier = Modifier.height(16.dp))
        
        Text(
            "Great job! You've completed your workout.",
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        
        Spacer(modifier = Modifier.height(48.dp))
        
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceVariant
            )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    "Total Time",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Spacer(modifier = Modifier.height(8.dp))
                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        String.format("%02d", (elapsedTime / 3600).toInt()),
                        style = MaterialTheme.typography.displayMedium,
                        fontWeight = FontWeight.Bold
                    )
                    Text(":", style = MaterialTheme.typography.displayMedium, modifier = Modifier.padding(horizontal = 4.dp))
                    Text(
                        String.format("%02d", ((elapsedTime % 3600) / 60).toInt()),
                        style = MaterialTheme.typography.displayMedium,
                        fontWeight = FontWeight.Bold
                    )
                    Text(":", style = MaterialTheme.typography.displayMedium, modifier = Modifier.padding(horizontal = 4.dp))
                    Text(
                        String.format("%02d", (elapsedTime % 60).toInt()),
                        style = MaterialTheme.typography.displayMedium,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
        
        Spacer(modifier = Modifier.height(48.dp))
        
        Button(
            onClick = onFinish,
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primary
            )
        ) {
            Text("Finish", style = MaterialTheme.typography.titleMedium)
        }
    }
}
