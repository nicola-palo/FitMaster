package com.fitmaster.app.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.fitmaster.app.data.entity.Exercise
import com.fitmaster.app.ui.components.BottomNavigationBar
import com.fitmaster.app.ui.navigation.Screen
import com.fitmaster.app.ui.viewmodel.ExerciseListViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExerciseListScreen(
    navController: NavController,
    viewModel: ExerciseListViewModel
) {
    val exercises by viewModel.exercises.collectAsState()
    val searchQuery by viewModel.searchQuery.collectAsState()
    val selectedMuscleGroup by viewModel.selectedMuscleGroup.collectAsState()
    val selectedDifficulty by viewModel.selectedDifficulty.collectAsState()
    val availableMuscleGroups by viewModel.availableMuscleGroups.collectAsState()
    val availableDifficulties by viewModel.availableDifficulties.collectAsState()
    val exerciseToEdit by viewModel.exerciseToEdit.collectAsState()
    
    var showAddExerciseDialog by remember { mutableStateOf(false) }
    var showMuscleGroupFilter by remember { mutableStateOf(false) }
    var showDifficultyFilter by remember { mutableStateOf(false) }
    
    if (showAddExerciseDialog) {
        AddExerciseDialog(
            onDismiss = { showAddExerciseDialog = false },
            onSave = { name, category, muscleGroup, difficulty, sets, reps, imageUrl ->
                viewModel.addExercise(name, category, muscleGroup, difficulty, sets, reps, imageUrl)
                showAddExerciseDialog = false
            }
        )
    }
    
    exerciseToEdit?.let { exercise ->
        EditExerciseDialog(
            exercise = exercise,
            onDismiss = { viewModel.clearExerciseToEdit() },
            onSave = { ex, name, category, muscleGroup, difficulty, sets, reps, imageUrl ->
                viewModel.updateExercise(ex, name, category, muscleGroup, difficulty, sets, reps, imageUrl)
                viewModel.clearExerciseToEdit()
            }
        )
    }
    
    if (showMuscleGroupFilter) {
        MuscleGroupFilterDialog(
            muscleGroups = availableMuscleGroups,
            currentSelection = selectedMuscleGroup,
            onDismiss = { showMuscleGroupFilter = false },
            onSelect = { muscleGroup ->
                viewModel.selectMuscleGroup(muscleGroup)
                showMuscleGroupFilter = false
            }
        )
    }
    
    if (showDifficultyFilter) {
        DifficultyFilterDialog(
            difficulties = availableDifficulties,
            currentSelection = selectedDifficulty,
            onDismiss = { showDifficultyFilter = false },
            onSelect = { difficulty ->
                viewModel.selectDifficulty(difficulty)
                showDifficultyFilter = false
            }
        )
    }
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Exercises", style = MaterialTheme.typography.titleLarge) },
                actions = {
                    IconButton(onClick = { showAddExerciseDialog = true }) {
                        Icon(Icons.Default.Add, contentDescription = "Add Exercise")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background
                )
            )
        },
        bottomBar = {
            BottomNavigationBar(navController, "exercise_list")
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            // Search Bar
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { viewModel.updateSearchQuery(it) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                placeholder = { Text("Search exercises") },
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
                trailingIcon = {
                    if (searchQuery.isNotEmpty()) {
                        IconButton(onClick = { viewModel.updateSearchQuery("") }) {
                            Icon(Icons.Default.Clear, contentDescription = "Clear")
                        }
                    }
                },
                singleLine = true,
                shape = RoundedCornerShape(12.dp)
            )
            
            // Filters
            LazyRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                item {
                    FilterChip(
                        selected = selectedMuscleGroup != null,
                        onClick = { showMuscleGroupFilter = true },
                        label = { Text(selectedMuscleGroup ?: "Muscle Group") },
                        trailingIcon = { Icon(Icons.Default.ArrowDropDown, contentDescription = null) }
                    )
                }
                item {
                    FilterChip(
                        selected = selectedDifficulty != null,
                        onClick = { showDifficultyFilter = true },
                        label = { Text(selectedDifficulty ?: "Difficulty") },
                        trailingIcon = { Icon(Icons.Default.ArrowDropDown, contentDescription = null) }
                    )
                }
                if (selectedMuscleGroup != null || selectedDifficulty != null) {
                    item {
                        FilterChip(
                            selected = false,
                            onClick = { viewModel.clearFilters() },
                            label = { Text("Clear") },
                            leadingIcon = { Icon(Icons.Default.Clear, contentDescription = null) }
                        )
                    }
                }
            }
            
            // Exercise List
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                item {
                    Text(
                        "All Exercises",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                }
                
                items(exercises) { exercise ->
                    ExerciseCard(
                        exercise = exercise,
                        onClick = {
                            navController.navigate(Screen.ExerciseDetail.createRoute(exercise.id))
                        },
                        onEdit = { exerciseToEdit ->
                            viewModel.selectExerciseForEdit(exerciseToEdit)
                        },
                        onDelete = { exerciseToDelete ->
                            viewModel.deleteExercise(exerciseToDelete)
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun ExerciseCard(
    exercise: Exercise,
    onClick: () -> Unit,
    onEdit: (Exercise) -> Unit,
    onDelete: (Exercise) -> Unit
) {
    var showDeleteDialog by remember { mutableStateOf(false) }
    
    if (showDeleteDialog) {
        AlertDialog(
            onDismissRequest = { showDeleteDialog = false },
            title = { Text("Delete Exercise") },
            text = { Text("Are you sure you want to delete \"${exercise.name}\"?") },
            confirmButton = {
                TextButton(
                    onClick = {
                        onDelete(exercise)
                        showDeleteDialog = false
                    }
                ) {
                    Text("Delete", color = MaterialTheme.colorScheme.error)
                }
            },
            dismissButton = {
                TextButton(onClick = { showDeleteDialog = false }) {
                    Text("Cancel")
                }
            }
        )
    }
    
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    exercise.category,
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.Medium
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    exercise.name,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    "${exercise.defaultSets} sets of ${exercise.defaultReps} reps",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                
                // Action buttons
                Spacer(modifier = Modifier.height(8.dp))
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    OutlinedButton(
                        onClick = { onEdit(exercise) },
                        modifier = Modifier.height(32.dp),
                        contentPadding = PaddingValues(horizontal = 12.dp)
                    ) {
                        Icon(
                            Icons.Default.Edit,
                            contentDescription = "Edit",
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("Edit", style = MaterialTheme.typography.labelSmall)
                    }
                    OutlinedButton(
                        onClick = { showDeleteDialog = true },
                        modifier = Modifier.height(32.dp),
                        contentPadding = PaddingValues(horizontal = 12.dp),
                        colors = ButtonDefaults.outlinedButtonColors(
                            contentColor = MaterialTheme.colorScheme.error
                        )
                    ) {
                        Icon(
                            Icons.Default.Delete,
                            contentDescription = "Delete",
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("Delete", style = MaterialTheme.typography.labelSmall)
                    }
                }
            }
            
            // Exercise Image
            exercise.imageUrl?.let { url ->
                AsyncImage(
                    model = url,
                    contentDescription = exercise.name,
                    modifier = Modifier
                        .size(80.dp)
                        .clip(RoundedCornerShape(8.dp)),
                    contentScale = ContentScale.Crop
                )
            } ?: Box(
                modifier = Modifier
                    .size(80.dp)
                    .clip(RoundedCornerShape(8.dp)),
                contentAlignment = Alignment.Center
            ) {
                Surface(
                    color = MaterialTheme.colorScheme.surface,
                    modifier = Modifier.fillMaxSize()
                ) {
                    Icon(
                        Icons.Default.FitnessCenter,
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(20.dp),
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
            }
        }
    }
}

@Composable
fun AddExerciseDialog(
    onDismiss: () -> Unit,
    onSave: (String, String, String, String, Int, Int, String?) -> Unit
) {
    var name by remember { mutableStateOf("") }
    var category by remember { mutableStateOf("") }
    var muscleGroup by remember { mutableStateOf("") }
    var difficulty by remember { mutableStateOf("Beginner") }
    var sets by remember { mutableStateOf("3") }
    var reps by remember { mutableStateOf("10") }
    var imageUrl by remember { mutableStateOf("") }
    
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Add New Exercise") },
        text = {
            LazyColumn(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                item {
                    OutlinedTextField(
                        value = name,
                        onValueChange = { name = it },
                        label = { Text("Exercise Name") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true
                    )
                }
                item {
                    OutlinedTextField(
                        value = category,
                        onValueChange = { category = it },
                        label = { Text("Category (e.g., Chest, Back)") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true
                    )
                }
                item {
                    OutlinedTextField(
                        value = muscleGroup,
                        onValueChange = { muscleGroup = it },
                        label = { Text("Muscle Group") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true
                    )
                }
                item {
                    var expanded by remember { mutableStateOf(false) }
                    @OptIn(ExperimentalMaterial3Api::class)
                    ExposedDropdownMenuBox(
                        expanded = expanded,
                        onExpandedChange = { expanded = !expanded }
                    ) {
                        OutlinedTextField(
                            value = difficulty,
                            onValueChange = {},
                            readOnly = true,
                            label = { Text("Difficulty") },
                            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                            modifier = Modifier
                                .fillMaxWidth()
                                .menuAnchor()
                        )
                        ExposedDropdownMenu(
                            expanded = expanded,
                            onDismissRequest = { expanded = false }
                        ) {
                            listOf("Beginner", "Intermediate", "Advanced").forEach { level ->
                                DropdownMenuItem(
                                    text = { Text(level) },
                                    onClick = {
                                        difficulty = level
                                        expanded = false
                                    }
                                )
                            }
                        }
                    }
                }
                item {
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
                }
                item {
                    OutlinedTextField(
                        value = imageUrl,
                        onValueChange = { imageUrl = it },
                        label = { Text("Image URL (optional)") },
                        placeholder = { Text("https://example.com/image.jpg") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true
                    )
                }
            }
        },
        confirmButton = {
            TextButton(
                onClick = {
                    if (name.isNotBlank() && category.isNotBlank()) {
                        onSave(
                            name,
                            category,
                            muscleGroup.ifBlank { category },
                            difficulty,
                            sets.toIntOrNull() ?: 3,
                            reps.toIntOrNull() ?: 10,
                            imageUrl.ifBlank { null }
                        )
                    }
                },
                enabled = name.isNotBlank() && category.isNotBlank()
            ) {
                Text("Save")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}

@Composable
fun MuscleGroupFilterDialog(
    muscleGroups: List<String>,
    currentSelection: String?,
    onDismiss: () -> Unit,
    onSelect: (String?) -> Unit
) {
    
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Filter by Muscle Group") },
        text = {
            LazyColumn {
                item {
                    TextButton(
                        onClick = { onSelect(null) },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            "All",
                            style = MaterialTheme.typography.bodyLarge,
                            fontWeight = if (currentSelection == null) FontWeight.Bold else FontWeight.Normal
                        )
                    }
                    Divider()
                }
                items(muscleGroups) { group ->
                    TextButton(
                        onClick = { onSelect(group) },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            group,
                            style = MaterialTheme.typography.bodyLarge,
                            fontWeight = if (currentSelection == group) FontWeight.Bold else FontWeight.Normal
                        )
                    }
                    Divider()
                }
            }
        },
        confirmButton = {
            TextButton(onClick = onDismiss) {
                Text("Close")
            }
        }
    )
}

@Composable
fun DifficultyFilterDialog(
    difficulties: List<String>,
    currentSelection: String?,
    onDismiss: () -> Unit,
    onSelect: (String?) -> Unit
) {
    
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Filter by Difficulty") },
        text = {
            LazyColumn {
                item {
                    TextButton(
                        onClick = { onSelect(null) },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            "All",
                            style = MaterialTheme.typography.bodyLarge,
                            fontWeight = if (currentSelection == null) FontWeight.Bold else FontWeight.Normal
                        )
                    }
                    Divider()
                }
                items(difficulties) { level ->
                    TextButton(
                        onClick = { onSelect(level) },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            level,
                            style = MaterialTheme.typography.bodyLarge,
                            fontWeight = if (currentSelection == level) FontWeight.Bold else FontWeight.Normal
                        )
                    }
                    Divider()
                }
            }
        },
        confirmButton = {
            TextButton(onClick = onDismiss) {
                Text("Close")
            }
        }
    )
}

@Composable
fun EditExerciseDialog(
    exercise: Exercise,
    onDismiss: () -> Unit,
    onSave: (Exercise, String, String, String, String, Int, Int, String?) -> Unit
) {
    var name by remember { mutableStateOf(exercise.name) }
    var category by remember { mutableStateOf(exercise.category) }
    var muscleGroup by remember { mutableStateOf(exercise.muscleGroup) }
    var difficulty by remember { mutableStateOf(exercise.difficulty) }
    var sets by remember { mutableStateOf(exercise.defaultSets.toString()) }
    var reps by remember { mutableStateOf(exercise.defaultReps.toString()) }
    var imageUrl by remember { mutableStateOf(exercise.imageUrl ?: "") }
    
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Edit Exercise") },
        text = {
            LazyColumn(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                item {
                    OutlinedTextField(
                        value = name,
                        onValueChange = { name = it },
                        label = { Text("Exercise Name") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true
                    )
                }
                item {
                    OutlinedTextField(
                        value = category,
                        onValueChange = { category = it },
                        label = { Text("Category (e.g., Chest, Back)") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true
                    )
                }
                item {
                    OutlinedTextField(
                        value = muscleGroup,
                        onValueChange = { muscleGroup = it },
                        label = { Text("Muscle Group") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true
                    )
                }
                item {
                    var expanded by remember { mutableStateOf(false) }
                    @OptIn(ExperimentalMaterial3Api::class)
                    ExposedDropdownMenuBox(
                        expanded = expanded,
                        onExpandedChange = { expanded = !expanded }
                    ) {
                        OutlinedTextField(
                            value = difficulty,
                            onValueChange = {},
                            readOnly = true,
                            label = { Text("Difficulty") },
                            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                            modifier = Modifier
                                .fillMaxWidth()
                                .menuAnchor()
                        )
                        ExposedDropdownMenu(
                            expanded = expanded,
                            onDismissRequest = { expanded = false }
                        ) {
                            listOf("Beginner", "Intermediate", "Advanced").forEach { level ->
                                DropdownMenuItem(
                                    text = { Text(level) },
                                    onClick = {
                                        difficulty = level
                                        expanded = false
                                    }
                                )
                            }
                        }
                    }
                }
                item {
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
                }
                item {
                    OutlinedTextField(
                        value = imageUrl,
                        onValueChange = { imageUrl = it },
                        label = { Text("Image URL (optional)") },
                        placeholder = { Text("https://example.com/image.jpg") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true
                    )
                }
            }
        },
        confirmButton = {
            TextButton(
                onClick = {
                    if (name.isNotBlank() && category.isNotBlank()) {
                        onSave(
                            exercise,
                            name,
                            category,
                            muscleGroup.ifBlank { category },
                            difficulty,
                            sets.toIntOrNull() ?: 3,
                            reps.toIntOrNull() ?: 10,
                            imageUrl.ifBlank { null }
                        )
                    }
                },
                enabled = name.isNotBlank() && category.isNotBlank()
            ) {
                Text("Save")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}
