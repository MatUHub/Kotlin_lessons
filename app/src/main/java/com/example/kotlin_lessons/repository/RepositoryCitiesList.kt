package com.example.kotlin_lessons.repository

import com.example.kotlin_lessons.model.Weather

interface RepositoryCitiesList {
    fun getWeatherFromLocalStorageRus(): List<Weather>
    fun getWeatherFromLocalStorageWorld(): List<Weather>
}