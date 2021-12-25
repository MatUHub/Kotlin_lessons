package com.example.kotlin_lessons.view_model

//запечатанные классы
sealed class AppState {
   data class Loading(var progress:Int):AppState()
   data class Success(var weatherData:String):AppState()
   data class Error(var error: String):AppState()
}