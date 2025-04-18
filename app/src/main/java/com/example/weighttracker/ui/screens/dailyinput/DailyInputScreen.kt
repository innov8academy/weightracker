package com.example.weighttracker.ui.screens.dailyinput

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.weighttracker.R
import com.example.weighttracker.navigation.Screen
import com.example.weighttracker.ui.components.DefaultButton
import com.example.weighttracker.ui.components.TextInputField
import com.example.weighttracker.util.isNumber
import com.example.weighttracker.viewmodel.DailyInputViewModel

@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun DailyInputScreen(
    navController: NavController,
    viewModel: DailyInputViewModel = hiltViewModel()
) {
    var weight by remember { mutableStateOf("") }
    var mood by remember { mutableStateOf("") }

    var isWeightValid by remember { mutableStateOf(true) }

    val keyboardController = LocalSoftwareKeyboardController.current
    val context = LocalContext.current

    val onSaveClick = {
        if (weight.isNumber()) {
            viewModel.addWeightEntry(weight.toDouble(), mood)
            Toast.makeText(context, context.getString(R.string.weight_saved), Toast.LENGTH_SHORT).show()
            navController.navigate(Screen.Dashboard.route) {
                popUpTo(Screen.DailyInput.route) { inclusive = true }
            }
        } else {
            isWeightValid = false
        }
        keyboardController?.hide()
    }

    val isLoading by viewModel.isLoading
    if (isLoading) {
        // TODO: Add Loading screen
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = stringResource(R.string.daily_weight_input))

        Spacer(modifier = Modifier.height(16.dp))

        TextInputField(
            value = weight,
            onValueChange = {
                weight = it
                isWeightValid = it.isNumber()
            },
            label = stringResource(R.string.weight),
            modifier = Modifier.fillMaxWidth(),
            isError = !isWeightValid,
            errorText = if (!isWeightValid) stringResource(R.string.invalid_weight) else null,
            keyboardController = keyboardController
        )

        Spacer(modifier = Modifier.height(16.dp))

        TextInputField(
            value = mood,
            onValueChange = {
                mood = it
            },
            label = stringResource(R.string.mood_optional),
            modifier = Modifier.fillMaxWidth(),
            keyboardController = keyboardController
        )

        Spacer(modifier = Modifier.height(32.dp))

        DefaultButton(
            text = stringResource(R.string.save),
            onClick = onSaveClick,
            enabled = weight.isNotBlank() && isWeightValid
        )
    }
}