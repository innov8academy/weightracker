package com.example.weighttracker.data.local

import androidx.room.RoomDatabase
import com.example.weighttracker.model.WeightEntry

@Database(entities = [WeightEntry::class], version = 1, exportSchema = false)
abstract class WeightDatabase : RoomDatabase() {
    abstract fun weightDao(): WeightDao
}