package com.example.weighttracker.navigation

sealed class Screen(val route: String){
    object Onboarding: Screen("onboarding_screen")
    object DailyInput: Screen("daily_input_screen")
    object Dashboard: Screen("dashboard_screen")
    object History: Screen("history_screen")
    object Settings: Screen("settings_screen")
}