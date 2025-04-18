package com.example.weighttracker.viewmodel

import android.content.Context
import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weighttracker.data.local.WeightDao
import com.example.weighttracker.data.preferences.PreferencesDataStore
import com.example.weighttracker.data.preferences.UserPreferences
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val preferencesDataStore: PreferencesDataStore,
    private val weightDao: WeightDao
) : ViewModel() {

    private val _exportStatus = MutableLiveData<ExportStatus>()
    val exportStatus: LiveData<ExportStatus> = _exportStatus

    fun saveUserPreferences(userPreferences: UserPreferences) {
        viewModelScope.launch {
            preferencesDataStore.saveUserPreferences(userPreferences)
        }
    }

    fun exportData(context: Context, uri: Uri) {
        viewModelScope.launch {
            _exportStatus.postValue(ExportStatus.Loading)
            try {
                val entries = weightDao.getAllWeightEntries()
                if (entries.isNotEmpty()) {
                    val csvString = buildCsvString(entries)
                    context.contentResolver.openOutputStream(uri)?.use { outputStream ->
                        outputStream.write(csvString.toByteArray())
                    }
                    _exportStatus.postValue(ExportStatus.Success)
                } else {
                    _exportStatus.postValue(ExportStatus.Error("No data to export"))
                }
            } catch (e: Exception) {
                _exportStatus.postValue(ExportStatus.Error("Error exporting data: ${e.message}"))
            }
        }
    }

    private fun buildCsvString(entries: List<com.example.weighttracker.data.local.WeightEntry>): String {
        val header = "Date,Weight,Mood\n"
        val data = entries.joinToString(separator = "\n") { entry ->
            "${entry.date},${entry.weight},${entry.mood}"
        }
        return header + data
    }

    sealed class ExportStatus {
        object Loading : ExportStatus()
        object Success : ExportStatus()
        data class Error(val message: String) : ExportStatus()
        object Idle : ExportStatus()
    }
}