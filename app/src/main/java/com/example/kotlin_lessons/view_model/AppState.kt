package com.example.kotlin_lessons.view_model

import com.example.kotlin_lessons.model.Weather
import com.example.kotlin_lessons.model.WeatherDTO

//запечатанные классы
sealed class AppState {
    data class Loading(val progress: Int) : AppState()
    data class Success(val weatherData: List<Weather>) : AppState()
    data class Error(val error: WeatherDTO) : AppState()
}