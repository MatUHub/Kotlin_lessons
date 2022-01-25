package com.example.kotlin_lessons.room

import androidx.room.Database
import androidx.room.RoomDatabase
// в [] указана тиблица
@Database(entities = [HistoryWeatherEntity::class], version = 1, exportSchema = false)
abstract class HistoryDatabase: RoomDatabase() {
    abstract fun historyWeatherDao(): HistoryWeatherDao
}