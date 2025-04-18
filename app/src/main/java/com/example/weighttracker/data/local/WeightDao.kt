package com.example.weighttracker.data.local

import com.example.weighttracker.model.WeightEntry
import kotlinx.coroutines.flow.Flow

@Dao
interface WeightDao {

    @Query("SELECT * FROM weight_entries ORDER BY date DESC")
    fun getAllWeightEntries(): Flow<List<WeightEntry>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWeightEntry(weightEntry: WeightEntry)

    @Update
    suspend fun updateWeightEntry(weightEntry: WeightEntry)

    @Delete
    suspend fun deleteWeightEntry(weightEntry: WeightEntry)
}