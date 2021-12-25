package com.example.kotlin_lessons.view_model

import com.example.kotlin_lessons.model.Weather

//запечатанные классы
sealed class AppState {
    data class Loading(val progress: Int) : AppState()
    data class Success(val weatherData: Weather) : AppState()
    data class Error(val error: String) : AppState()
}