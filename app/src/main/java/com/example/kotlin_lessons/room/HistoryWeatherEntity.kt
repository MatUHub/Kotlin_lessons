package com.example.kotlin_lessons.room

import androidx.room.Entity
import androidx.room.PrimaryKey

// наименование
@Entity(tableName = "history_weather_entity")
data class HistoryWeatherEntity(
    @PrimaryKey(autoGenerate = true) val id: Long,
    val temperature: Int,
    val feelsLike: Int,
    val icon: String
) {

}