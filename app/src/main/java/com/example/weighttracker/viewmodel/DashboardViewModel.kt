package com.example.weighttracker.viewmodel

import androidx.lifecycle.ViewModel
import com.example.weighttracker.data.local.WeightEntry
import com.example.weighttracker.data.local.WeightDao
import com.example.weighttracker.data.preferences.PreferencesDataStore
import com.example.weighttracker.data.preferences.UserPreferences
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val weightDao: WeightDao,
    private val preferencesDataStore: PreferencesDataStore
) : ViewModel() {

    fun getWeightEntries(): Flow<List<WeightEntry>> = weightDao.getAllWeightEntries()

    fun getPreferences(): Flow<UserPreferences> = preferencesDataStore.getUserPreferences()

}