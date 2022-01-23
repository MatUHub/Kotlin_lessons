package com.example.kotlin_lessons.repository
import com.example.kotlin_lessons.model.WeatherDTO
import retrofit2.Callback
interface RepositoryDetails {
    fun getWeatherFromServer(lat: Double,lon: Double, callback: Callback<WeatherDTO>)
}