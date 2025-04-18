package com.example.weighttracker.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weighttracker.data.local.WeightDao
import com.example.weighttracker.data.local.WeightEntry
import dagger.Provides
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject
import androidx.lifecycle.LiveData
@HiltViewModel
class DailyInputViewModel @Inject constructor(
    private val weightDao: WeightDao
) : ViewModel() {

    fun addWeightEntry(weightEntry: WeightEntry) {
        viewModelScope.launch {
            weightDao.insert(weightEntry)
        }
    }

    fun getAllWeightEntries(): Flow<List<WeightEntry>> {
        return weightDao.getAllWeightEntries()
    }
}