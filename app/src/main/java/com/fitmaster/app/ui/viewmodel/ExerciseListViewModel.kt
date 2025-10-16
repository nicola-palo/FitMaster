package com.fitmaster.app.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fitmaster.app.data.entity.Exercise
import com.fitmaster.app.data.repository.ExerciseRepository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class ExerciseListViewModel(
    private val exerciseRepository: ExerciseRepository
) : ViewModel() {
    
    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()
    
    private val _selectedMuscleGroup = MutableStateFlow<String?>(null)
    val selectedMuscleGroup: StateFlow<String?> = _selectedMuscleGroup.asStateFlow()
    
    private val _selectedDifficulty = MutableStateFlow<String?>(null)
    val selectedDifficulty: StateFlow<String?> = _selectedDifficulty.asStateFlow()
    
    private val _exerciseToEdit = MutableStateFlow<Exercise?>(null)
    val exerciseToEdit: StateFlow<Exercise?> = _exerciseToEdit.asStateFlow()
    
    // Dynamic muscle groups from database
    val availableMuscleGroups: StateFlow<List<String>> = exerciseRepository.getAllExercises()
        .map { exercises ->
            exercises.map { it.muscleGroup }.distinct().sorted()
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )
    
    // Dynamic difficulties from database
    val availableDifficulties: StateFlow<List<String>> = exerciseRepository.getAllExercises()
        .map { exercises ->
            exercises.map { it.difficulty }.distinct().sorted()
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )
    
    val exercises: StateFlow<List<Exercise>> = combine(
        _searchQuery,
        _selectedMuscleGroup,
        _selectedDifficulty
    ) { query, muscleGroup, difficulty ->
        Triple(query, muscleGroup, difficulty)
    }.flatMapLatest { (query, muscleGroup, difficulty) ->
        when {
            query.isNotBlank() -> exerciseRepository.searchExercises(query)
            muscleGroup != null -> exerciseRepository.getExercisesByMuscleGroup(muscleGroup)
            difficulty != null -> exerciseRepository.getExercisesByDifficulty(difficulty)
            else -> exerciseRepository.getAllExercises()
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )
    
    fun updateSearchQuery(query: String) {
        _searchQuery.value = query
    }
    
    fun selectMuscleGroup(muscleGroup: String?) {
        _selectedMuscleGroup.value = muscleGroup
        _selectedDifficulty.value = null
    }
    
    fun selectDifficulty(difficulty: String?) {
        _selectedDifficulty.value = difficulty
        _selectedMuscleGroup.value = null
    }
    
    fun clearFilters() {
        _selectedMuscleGroup.value = null
        _selectedDifficulty.value = null
        _searchQuery.value = ""
    }
    
    fun addExercise(
        name: String,
        category: String,
        muscleGroup: String,
        difficulty: String,
        sets: Int,
        reps: Int,
        imageUrl: String?
    ) {
        viewModelScope.launch {
            val newExercise = Exercise(
                name = name,
                category = category,
                muscleGroup = muscleGroup,
                difficulty = difficulty,
                description = "Custom exercise",
                technicalTips = "Keep proper form\nControl the movement\nBreathe properly",
                defaultSets = sets,
                defaultReps = reps,
                defaultRestSeconds = 60,
                imageUrl = imageUrl,
                isCustom = true
            )
            exerciseRepository.insertExercise(newExercise)
        }
    }
    
    fun updateExercise(
        exercise: Exercise,
        name: String,
        category: String,
        muscleGroup: String,
        difficulty: String,
        sets: Int,
        reps: Int,
        imageUrl: String?
    ) {
        viewModelScope.launch {
            val updatedExercise = exercise.copy(
                name = name,
                category = category,
                muscleGroup = muscleGroup,
                difficulty = difficulty,
                defaultSets = sets,
                defaultReps = reps,
                imageUrl = imageUrl
            )
            exerciseRepository.updateExercise(updatedExercise)
        }
    }
    
    fun deleteExercise(exercise: Exercise) {
        viewModelScope.launch {
            exerciseRepository.deleteExercise(exercise)
        }
    }
    
    fun selectExerciseForEdit(exercise: Exercise) {
        _exerciseToEdit.value = exercise
    }
    
    fun clearExerciseToEdit() {
        _exerciseToEdit.value = null
    }
}
