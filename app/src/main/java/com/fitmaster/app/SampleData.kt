package com.fitmaster.app

import com.fitmaster.app.data.entity.Exercise
import com.fitmaster.app.data.entity.UserProfile

object SampleData {
    fun getDefaultUserProfile(): UserProfile {
        return UserProfile(
            name = "User",
            age = 25,
            sex = "Male",
            goal = "Muscle Gain"
        )
    }
    
    fun getDefaultExercises(): List<Exercise> = listOf(
        Exercise(
            name = "Push-ups",
            description = "The push-up is a fundamental exercise that strengthens the chest, shoulders, and triceps. It also engages the core and improves overall body strength.",
            technicalTips = "Keep your body in a straight line from head to heels\nLower your chest to the ground, keeping elbows tucked in\nPush back up to the starting position with controlled movements",
            category = "Strength",
            difficulty = "Beginner",
            muscleGroup = "Chest",
            defaultSets = 3,
            defaultReps = 10,
            defaultRestSeconds = 60,
            imageUrl = "https://lh3.googleusercontent.com/aida-public/AB6AXuAg7mO5J8M8XMbpbNn8_eY-qMXMYVAAtfusj3141M6rVW3_KJtBeel8dI_pSKgCHMixwUiZypoVWC98PnUznHzJ4syQEimp7brkZj8ZI8PVwlq6PmjlLJi97AhPeIhSGYxSG3QF2kkEFgykrku_bVaXI3GrUQDc5Xi9Y01Mb9q_3qVWDIxCe6YukeTMN_cxzz3vx4-r0dwuQdsTjpQemzgEaabkqx4LEJNCpPHqBy4RqZLn2p06-_3Z3lbNNlo24FTpzP-ylivzPw"
        ),
        Exercise(
            name = "Squats",
            description = "Squats are a compound exercise that targets the quadriceps, hamstrings, and glutes. They also engage the core and lower back.",
            technicalTips = "Keep your feet shoulder-width apart\nLower your hips back and down as if sitting in a chair\nKeep your chest up and knees aligned with your toes\nPush through your heels to return to standing",
            category = "Strength",
            difficulty = "Beginner",
            muscleGroup = "Legs",
            defaultSets = 3,
            defaultReps = 12,
            defaultRestSeconds = 60,
            imageUrl = "https://lh3.googleusercontent.com/aida-public/AB6AXuBD3T7BEccUYmquG_ux3rOe0fFgIAr4xfQYqN1X7fPHzcEd6L21jGrFpd404W6pnfZxDIpGHtpHTKezFT66C15CGzy3vU2LSFfyP6k9OleSxEZ9NyGXWF9dCkMQZDM4S4Oucgxat86ZjRaUOo3aRK58DEFf5uOtBD9jvB2Cx9oktMXC8sIc3oDNFgnyrkM5z9AbwWmSRqCC0ptAMzIWPItpLtyZCV9tASiV6D-bsbTo4eYQZmQzkCCsjF2z4Tq1LzJQxZXprvuZ7g"
        ),
        Exercise(
            name = "Lunges",
            description = "Lunges are an effective lower body exercise that targets the quadriceps, hamstrings, and glutes while improving balance.",
            technicalTips = "Step forward with one leg\nLower your hips until both knees are bent at 90 degrees\nKeep your front knee aligned with your ankle\nPush back to starting position",
            category = "Strength",
            difficulty = "Beginner",
            muscleGroup = "Legs",
            defaultSets = 3,
            defaultReps = 10,
            defaultRestSeconds = 60,
            imageUrl = "https://lh3.googleusercontent.com/aida-public/AB6AXuA7WLpXIAYXICpA-uNG-F33--DHv7L4C5iBCHQlG8uR3ffLQaK4nlE1rbRsoq4xdCngLfi7dpXTbWBy1D1lQ2E2D0vFlupOG8UO8EaTqYQv058bnS1wwIYr57JxmJu3-PGhkrx9TCB7gOuRK_0-HogXmcEuh3-re1ERgcPnXIClls23wf-DNvNhdnu26Q2fPoODnXrP5SMRJ7d_E6ZkfKd9MQQcMySPdQJ3J7Lju9Ca9EVPeAVRunZt90n0ZgCaCnqIWoPTk5PwiQ"
        ),
        Exercise(
            name = "Plank",
            description = "The plank is a core-strengthening exercise that engages multiple muscle groups and improves stability.",
            technicalTips = "Start in a push-up position with forearms on the ground\nKeep your body in a straight line from head to heels\nEngage your core and hold the position\nDon't let your hips sag or rise",
            category = "Strength",
            difficulty = "Beginner",
            muscleGroup = "Core",
            defaultSets = 3,
            defaultReps = 1,
            defaultRestSeconds = 60,
            imageUrl = "https://lh3.googleusercontent.com/aida-public/AB6AXuCmN9Ndb8IHzeGVIjgoo-oaJt89Qc3NV1zYereHM8iHo5awZ8d6lgVsE2Kb-BManG09JFEzJQvn-TR0T7NPaO8NR3qYyE63gny75y3qOWPQs3_wZs_MimAzjOc4uE04LQDE_MQyfOJGzaEQsWXtWaE3mGy84HI0pld1dciyNGRLfDMgixQzc26XCSaUDGREU4fREWTPsINYmAOkh-g7zVOxJ53go1lXqnwGdSWwso2xoA4-_symlv6mvmIgflZ2evSWyWfriS1s9Q"
        ),
        Exercise(
            name = "Pull-ups",
            description = "Pull-ups are an upper body exercise that primarily targets the back muscles, biceps, and shoulders.",
            technicalTips = "Hang from a bar with palms facing away\nPull yourself up until your chin is above the bar\nLower yourself back down with control\nKeep your core engaged throughout",
            category = "Strength",
            difficulty = "Intermediate",
            muscleGroup = "Back",
            defaultSets = 3,
            defaultReps = 8,
            defaultRestSeconds = 60
        ),
        Exercise(
            name = "Deadlift",
            description = "The deadlift is a compound exercise that works the entire posterior chain including back, glutes, and hamstrings.",
            technicalTips = "Stand with feet hip-width apart\nBend at hips and knees to grip the bar\nKeep your back straight and chest up\nDrive through your heels to lift the weight",
            category = "Strength",
            difficulty = "Advanced",
            muscleGroup = "Back",
            defaultSets = 4,
            defaultReps = 6,
            defaultRestSeconds = 90
        ),
        Exercise(
            name = "Bench Press",
            description = "The bench press is a classic upper body exercise focusing on chest, shoulders, and triceps.",
            technicalTips = "Lie flat on bench with feet on floor\nGrip the bar slightly wider than shoulder width\nLower the bar to your chest\nPress back up to starting position",
            category = "Strength",
            difficulty = "Intermediate",
            muscleGroup = "Chest",
            defaultSets = 4,
            defaultReps = 8,
            defaultRestSeconds = 60
        ),
        Exercise(
            name = "Bicep Curls",
            description = "Bicep curls isolate and strengthen the biceps muscles in the upper arm.",
            technicalTips = "Stand with dumbbells at your sides\nKeep elbows close to your torso\nCurl the weights up to shoulder level\nLower back down with control",
            category = "Strength",
            difficulty = "Beginner",
            muscleGroup = "Arms",
            defaultSets = 3,
            defaultReps = 12,
            defaultRestSeconds = 60
        )
    )
}
