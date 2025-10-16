package com.fitmaster.app

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.core.content.ContextCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.fitmaster.app.ui.navigation.Screen
import com.fitmaster.app.ui.screens.*
import com.fitmaster.app.ui.theme.FitMasterTheme
import com.fitmaster.app.ui.viewmodel.*

class MainActivity : ComponentActivity() {
    
    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        // Permission granted or denied
    }
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        // Request notification permission for Android 13+
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.POST_NOTIFICATIONS
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
            }
        }
        
        setContent {
            FitMasterTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    FitMasterApp(intent = intent)
                }
            }
        }
    }
    
    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        setIntent(intent)
    }
}

@Composable
fun FitMasterApp(intent: Intent? = null) {
    val navController = rememberNavController()
    val context = androidx.compose.ui.platform.LocalContext.current
    val app = context.applicationContext as FitMasterApplication
    
    // Handle deep link from notification
    LaunchedEffect(intent) {
        if (intent?.getStringExtra("navigate_to") == "active_workout") {
            val planId = intent.getLongExtra("plan_id", 0)
            val dayId = intent.getLongExtra("day_id", 0)
            if (planId > 0 && dayId > 0) {
                navController.navigate("active_workout/$planId/$dayId") {
                    popUpTo(Screen.Home.route) { inclusive = false }
                }
            }
        }
    }
    
    val viewModelFactory = ViewModelFactory(
        exerciseRepository = app.exerciseRepository,
        workoutPlanRepository = app.workoutPlanRepository,
        workoutSessionRepository = app.workoutSessionRepository,
        userRepository = app.userRepository
    )
    
    NavHost(
        navController = navController,
        startDestination = Screen.Home.route
    ) {
        composable(Screen.Home.route) {
            val viewModel: HomeViewModel = viewModel(factory = viewModelFactory)
            HomeScreen(navController, viewModel)
        }
        
        composable(Screen.ExerciseList.route) {
            val viewModel: ExerciseListViewModel = viewModel(factory = viewModelFactory)
            ExerciseListScreen(navController, viewModel)
        }
        
        composable(
            route = Screen.ExerciseDetail.route,
            arguments = listOf(navArgument("exerciseId") { type = NavType.LongType })
        ) { backStackEntry ->
            val exerciseId = backStackEntry.arguments?.getLong("exerciseId") ?: 0L
            val viewModel = ViewModelFactory.createExerciseDetailViewModel(
                app.exerciseRepository,
                exerciseId
            )
            ExerciseDetailScreen(navController, viewModel)
        }
        
        composable(Screen.WorkoutPlanList.route) {
            val viewModel: WorkoutPlanListViewModel = viewModel(factory = viewModelFactory)
            WorkoutPlanListScreen(navController, viewModel)
        }
        
        composable(Screen.WorkoutPlanCreation.route) {
            val viewModel: WorkoutPlanViewModel = viewModel(factory = viewModelFactory)
            WorkoutPlanCreationScreen(navController, viewModel, planId = null)
        }
        
        composable(
            route = Screen.WorkoutPlanEdit.route,
            arguments = listOf(navArgument("planId") { type = NavType.LongType })
        ) { backStackEntry ->
            val planId = backStackEntry.arguments?.getLong("planId") ?: 0L
            val viewModel: WorkoutPlanViewModel = viewModel(factory = viewModelFactory)
            WorkoutPlanCreationScreen(navController, viewModel, planId = planId)
        }
        
        composable(
            route = Screen.ActiveWorkout.route,
            arguments = listOf(
                navArgument("planId") { type = NavType.LongType },
                navArgument("dayId") { type = NavType.LongType }
            )
        ) { backStackEntry ->
            val planId = backStackEntry.arguments?.getLong("planId") ?: 0L
            val dayId = backStackEntry.arguments?.getLong("dayId") ?: 0L
            val viewModel: ActiveWorkoutViewModel = viewModel(factory = viewModelFactory)
            ActiveWorkoutScreen(navController, viewModel, planId, dayId)
        }
        
        composable(
            route = Screen.RestTimer.route,
            arguments = listOf(navArgument("seconds") { type = NavType.IntType })
        ) { backStackEntry ->
            val seconds = backStackEntry.arguments?.getInt("seconds") ?: 30
            RestTimerScreen(navController, seconds)
        }
        
        composable(Screen.UserProfile.route) {
            val viewModel: UserProfileViewModel = viewModel(factory = viewModelFactory)
            UserProfileScreen(navController, viewModel)
        }
    }
}
