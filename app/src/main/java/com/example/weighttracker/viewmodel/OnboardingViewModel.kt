package com.example.weighttracker.viewmodel

import androidx.lifecycle.viewModelScope
import com.example.weighttracker.data.preferences.PreferencesDataStore
import com.example.weighttracker.data.preferences.UserPreferences
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OnboardingViewModel @Inject constructor(
    private val preferencesDataStore: PreferencesDataStore
) : ViewModel() {

    fun saveUserPreferences(goal: String, currentWeight: Double, goalWeight: Double, unit: String) {
        viewModelScope.launch {
            preferencesDataStore.saveUserPreferences(
                UserPreferences(goal, currentWeight, goalWeight, unit)
            )
        }
    }
}