package com.example.weighttracker.ui.screens.onboarding

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OnboardingScreen(navController: NavController) {
    var goal by remember { mutableStateOf("Cut") }
    var currentWeight by remember { mutableStateOf("") }
    var goalWeight by remember { mutableStateOf("") }

    val goals = listOf("Cut", "Bulk", "Maintain")

    //TODO Use viewmodel
    val onboardingViewModel = remember { OnboardingViewModel() }

    var isButtonEnabled by remember { mutableStateOf(false) }

    isButtonEnabled = currentWeight.isNotEmpty() && goalWeight.isNotEmpty()



    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Welcome!",
            style = MaterialTheme.typography.headlineLarge.copy(fontWeight = FontWeight.Bold),
            color = MaterialTheme.colorScheme.onBackground
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Set your goals",
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f)
        )

        Spacer(modifier = Modifier.height(16.dp))

        goals.forEach {
            DefaultButton(
                text = it,
                onClick = { goal = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp),
                enabled = true,
                isSelected = it == goal
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        TextField(
            value = currentWeight,
            onValueChange = { currentWeight = it },
            label = "Current Weight",
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
            modifier = Modifier.fillMaxWidth(),
            placeholder = "Enter your current weight"
        )

        Spacer(modifier = Modifier.height(16.dp))

        TextField(
            value = goalWeight,
            onValueChange = { goalWeight = it },
            label = "Goal Weight",
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
            modifier = Modifier.fillMaxWidth(),
            placeholder = "Enter your goal weight"
        )

        Spacer(modifier = Modifier.height(32.dp))

        DefaultButton(
            onClick = {
                onboardingViewModel.saveUserPreferences(
                    goal = goal,
                    currentWeight = currentWeight.toDoubleOrNull() ?: 0.0,
                    goalWeight = goalWeight.toDoubleOrNull() ?: 0.0
                )
                navController.navigate(Screen.DailyInput.route) {
                    popUpTo(Screen.Onboarding.route) {
                        inclusive = true
                    }
                }
            },
            text = "Save and Continue",
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            enabled = isButtonEnabled
        )

    }
}

