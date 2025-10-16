package com.fitmaster.app.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.fitmaster.app.data.entity.Exercise
import com.fitmaster.app.ui.components.TopAppBarWithBack
import com.fitmaster.app.ui.viewmodel.WorkoutPlanViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WorkoutPlanCreationScreen(
    navController: NavController,
    viewModel: WorkoutPlanViewModel,
    planId: Long?
) {
    val planName by viewModel.planName.collectAsState()
    val workoutDays by viewModel.workoutDays.collectAsState()
    val allExercises by viewModel.allExercises.collectAsState()
    
    var selectedDayId by remember { mutableStateOf<Long?>(null) }
    var showExerciseDialog by remember { mutableStateOf(false) }
    
    LaunchedEffect(planId) {
        if (planId != null && planId > 0) {
            viewModel.loadPlan(planId)
        } else {
            viewModel.createNewPlan()
        }
    }
    
    val selectedDay = workoutDays.find { it.dayId == selectedDayId }
    
    if (showExerciseDialog && selectedDay != null) {
        ExerciseSelectionDialog(
            exercises = allExercises,
            onDismiss = { showExerciseDialog = false },
            onExerciseSelected = { exercise: Exercise, sets: Int, reps: Int, restSeconds: Int ->
                selectedDay.let { day ->
                    viewModel.addExerciseToDay(day.dayId, exercise.id, sets, reps, restSeconds)
                }
                showExerciseDialog = false
            }
        )
    }
    
    Scaffold(
        topBar = {
            TopAppBarWithBack(
                title = if (planId != null && planId > 0) "Edit Workout Plan" else "New Workout Plan",
                onBackClick = { navController.navigateUp() }
            )
        },
        bottomBar = {
            Button(
                onClick = { navController.navigateUp() },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .height(56.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary
                )
            ) {
                Text("Done")
            }
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                OutlinedTextField(
                    value = planName,
                    onValueChange = { viewModel.setPlanName(it) },
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text("Plan Name") },
                    textStyle = MaterialTheme.typography.titleMedium,
                    singleLine = true
                )
            }
            
            item {
                Text(
                    "Workout Days",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )
            }
            
            items(workoutDays) { day ->
                ElevatedCard(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            selectedDayId = day.dayId
                            showExerciseDialog = true
                        }
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column {
                                Text(
                                    day.dayName,
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = FontWeight.Bold
                                )
                                Text(
                                    "${day.exercises.size} exercises",
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                            Icon(
                                Icons.Default.Add,
                                contentDescription = "Add exercise",
                                tint = MaterialTheme.colorScheme.primary
                            )
                        }
                        
                        if (day.exercises.isNotEmpty()) {
                            Spacer(modifier = Modifier.height(12.dp))
                            Divider()
                            Spacer(modifier = Modifier.height(8.dp))
                            
                            day.exercises.forEach { exerciseWithDetails ->
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(vertical = 4.dp),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Column(modifier = Modifier.weight(1f)) {
                                        Text(
                                            exerciseWithDetails.exercise.name,
                                            style = MaterialTheme.typography.bodyMedium,
                                            fontWeight = FontWeight.Medium
                                        )
                                        Text(
                                            "${exerciseWithDetails.planExercise.sets} sets × ${exerciseWithDetails.planExercise.reps} reps",
                                            style = MaterialTheme.typography.bodySmall,
                                            color = MaterialTheme.colorScheme.onSurfaceVariant
                                        )
                                        Text(
                                            "Rest: ${exerciseWithDetails.planExercise.restSeconds}s",
                                            style = MaterialTheme.typography.bodySmall,
                                            color = MaterialTheme.colorScheme.primary
                                        )
                                    }
                                    IconButton(
                                        onClick = {
                                            viewModel.removeExerciseFromDay(exerciseWithDetails.planExercise.id)
                                        }
                                    ) {
                                        Icon(
                                            Icons.Default.Delete,
                                            contentDescription = "Remove exercise",
                                            tint = MaterialTheme.colorScheme.error
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ExerciseSelectionDialog(
    exercises: List<Exercise>,
    onDismiss: () -> Unit,
    onExerciseSelected: (Exercise, Int, Int, Int) -> Unit
) {
    var selectedExercise by remember { mutableStateOf<Exercise?>(null) }
    var sets by remember { mutableStateOf("3") }
    var reps by remember { mutableStateOf("10") }
    var restSeconds by remember { mutableStateOf("60") }
    
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(if (selectedExercise == null) "Select Exercise" else "Configure Exercise") },
        text = {
            if (selectedExercise == null) {
                // Exercise selection list
                LazyColumn(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    items(exercises) { exercise ->
                        TextButton(
                            onClick = { 
                                selectedExercise = exercise
                                sets = exercise.defaultSets.toString()
                                reps = exercise.defaultReps.toString()
                                restSeconds = exercise.defaultRestSeconds.toString()
                            },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Column(
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Text(
                                    exercise.name,
                                    style = MaterialTheme.typography.bodyLarge,
                                    fontWeight = FontWeight.Medium
                                )
                                Text(
                                    "${exercise.category} - ${exercise.muscleGroup}",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                        }
                        Divider()
                    }
                }
            } else {
                // Exercise configuration
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Text(
                        selectedExercise!!.name,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                    
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        OutlinedTextField(
                            value = sets,
                            onValueChange = { sets = it.filter { char -> char.isDigit() } },
                            label = { Text("Sets") },
                            modifier = Modifier.weight(1f),
                            singleLine = true
                        )
                        OutlinedTextField(
                            value = reps,
                            onValueChange = { reps = it.filter { char -> char.isDigit() } },
                            label = { Text("Reps") },
                            modifier = Modifier.weight(1f),
                            singleLine = true
                        )
                    }
                    
                    OutlinedTextField(
                        value = restSeconds,
                        onValueChange = { restSeconds = it.filter { char -> char.isDigit() } },
                        label = { Text("Rest (seconds)") },
                        placeholder = { Text("60") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true,
                        supportingText = { Text("Rest time between sets") }
                    )
                }
            }
        },
        confirmButton = {
            if (selectedExercise != null) {
                TextButton(
                    onClick = {
                        onExerciseSelected(
                            selectedExercise!!,
                            sets.toIntOrNull() ?: 3,
                            reps.toIntOrNull() ?: 10,
                            restSeconds.toIntOrNull() ?: 60
                        )
                    }
                ) {
                    Text("Add")
                }
            }
        },
        dismissButton = {
            TextButton(onClick = {
                if (selectedExercise != null) {
                    selectedExercise = null
                } else {
                    onDismiss()
                }
            }) {
                Text(if (selectedExercise != null) "Back" else "Cancel")
            }
        }
    )
}
