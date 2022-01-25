package com.example.kotlin_lessons.repository

import com.example.kotlin_lessons.model.Weather

interface RepositoryHistoryWeather {
    fun getAllHistoryWeather(): List<Weather>
    fun saveWeather(weather: Weather)
}