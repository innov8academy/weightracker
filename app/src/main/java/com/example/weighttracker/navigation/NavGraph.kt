package com.example.weighttracker.navigation

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.weighttracker.ui.screens.DailyInputScreen
import com.example.weighttracker.ui.screens.DashboardScreen
import com.example.weighttracker.ui.screens.HistoryScreen
import com.example.weighttracker.ui.screens.OnboardingScreen
import com.example.weighttracker.ui.screens.SettingsScreen

@Composable    
fun NavGraph(navController: NavHostController, context: Context) {
    NavHost(navController = navController, startDestination = Screen.Onboarding.route) {
        composable(Screen.Onboarding.route) {
            OnboardingScreen(navController, context)
        }
        composable(Screen.DailyInput.route) {
            DailyInputScreen(navController, context)
        }
        composable(Screen.Dashboard.route) {
            DashboardScreen(navController, context)
        }
        composable(Screen.History.route) {
            HistoryScreen(navController, context)
        }
        composable(Screen.Settings.route) {
            SettingsScreen(navController, context)
        }
    }
}