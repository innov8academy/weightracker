package com.example.weighttracker.data.preferences

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.doublePreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class PreferencesDataStore(private val context: Context) {

    companion object {
        private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "user_preferences")
        val GOAL = stringPreferencesKey("goal")
        val CURRENT_WEIGHT = doublePreferencesKey("current_weight")
        val GOAL_WEIGHT = doublePreferencesKey("goal_weight")
        val UNIT = stringPreferencesKey("unit")
    }
    
    suspend fun saveUserPreferences(userPreferences: UserPreferences) {
        context.dataStore.edit { preferences ->
            preferences[GOAL] = userPreferences.goal
            preferences[CURRENT_WEIGHT] = userPreferences.currentWeight
            preferences[GOAL_WEIGHT] = userPreferences.goalWeight
            preferences[UNIT] = userPreferences.unit
        }
    }

    fun getUserPreferences(): Flow<UserPreferences> = context.dataStore.data
        .map { preferences ->
            UserPreferences(
                goal = preferences[GOAL] ?: "Maintain",
                currentWeight = preferences[CURRENT_WEIGHT] ?: 0.0,
                goalWeight = preferences[GOAL_WEIGHT] ?: 0.0,
                unit = preferences[UNIT] ?: "kg"
            )
        }
}