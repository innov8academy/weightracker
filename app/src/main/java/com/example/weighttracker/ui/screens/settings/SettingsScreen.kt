package com.example.weighttracker.ui.screens.settings

import android.content.Context
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.weighttracker.ui.components.DefaultButton
import com.example.weighttracker.ui.theme.Typography
import com.example.weighttracker.util.Units
import com.example.weighttracker.viewmodel.SettingsViewModel
import kotlinx.coroutines.flow.collectLatest

@Composable
fun SettingsScreen(
    navController: NavController,
    viewModel: SettingsViewModel = hiltViewModel()
) {
    val context: Context = LocalContext.current

    var isKg by remember { mutableStateOf(true) }
    var currentUnit by remember { mutableStateOf(Units.KG) }

    LaunchedEffect(Unit) {
        viewModel.unit.collectLatest { unit ->
            currentUnit = unit
            isKg = unit == Units.KG
        }
    }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text("Settings", style = Typography.headlineMedium, fontWeight = FontWeight.Bold)

        Spacer(modifier = Modifier.height(16.dp))

        // Unit Toggle
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Text("Use Kilograms (kg)")
            Switch(
                checked = isKg,
                onCheckedChange = {
                    isKg = it
                    val newUnit = if (it) Units.KG else Units.LB
                    viewModel.changeUnit(newUnit)
                },
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Change Goal Button
        DefaultButton(
            text = "Change Goal",
            onClick = {
                //TODO: Implement change goal logic (e.g., navigate to onboarding with pre-filled data)
            },
            enabled = true,
            modifier = Modifier.fillMaxWidth(),
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Reset Progress Button
        DefaultButton(
            text = "Reset Progress",
            onClick = {
                // TODO: Implement reset progress logic (clear all entries, navigate to onboarding?)
            },
            enabled = true,
            modifier = Modifier.fillMaxWidth(),
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Export Data Button
        DefaultButton(
            text = "Export Data (CSV)",
            onClick = {
                viewModel.exportDataToCSV(context)
            },
            enabled = true,
            modifier = Modifier.fillMaxWidth(),
        )
    }
}