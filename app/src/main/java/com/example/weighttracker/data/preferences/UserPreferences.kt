package com.example.weighttracker.data.preferences

data class UserPreferences(
    val goal: String = "Maintain",
    val currentWeight: Double = 0.0,
    val goalWeight: Double = 0.0,
    val unit: String = "kg"
)