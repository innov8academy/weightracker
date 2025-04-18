package com.example.weighttracker.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weighttracker.data.local.WeightDao
import com.example.weighttracker.data.local.WeightEntry
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HistoryViewModel @Inject constructor(
    private val weightDao: WeightDao
) : ViewModel() {

    fun getAllWeightEntries(): Flow<List<WeightEntry>> = weightDao.getAllWeightEntries()

    fun deleteWeightEntry(entry: WeightEntry) {
        viewModelScope.launch {
            weightDao.deleteWeightEntry(entry)
        }
    }

    fun updateWeightEntry(entry: WeightEntry) {
        viewModelScope.launch {
            weightDao.updateWeightEntry(entry)
        }
    }


}