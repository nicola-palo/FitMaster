package com.fitmaster.app.ui.navigation

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object ExerciseList : Screen("exercise_list")
    object ExerciseDetail : Screen("exercise_detail/{exerciseId}") {
        fun createRoute(exerciseId: Long) = "exercise_detail/$exerciseId"
    }
    object WorkoutPlanList : Screen("workout_plan_list")
    object WorkoutPlanCreation : Screen("workout_plan_creation")
    object WorkoutPlanEdit : Screen("workout_plan_edit/{planId}") {
        fun createRoute(planId: Long) = "workout_plan_edit/$planId"
    }
    object ActiveWorkout : Screen("active_workout/{planId}/{dayId}") {
        fun createRoute(planId: Long, dayId: Long) = "active_workout/$planId/$dayId"
    }
    object RestTimer : Screen("rest_timer/{seconds}") {
        fun createRoute(seconds: Int) = "rest_timer/$seconds"
    }
    object UserProfile : Screen("user_profile")
}
