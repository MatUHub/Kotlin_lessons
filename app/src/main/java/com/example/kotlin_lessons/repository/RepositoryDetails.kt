package com.example.kotlin_lessons.repository
import okhttp3.Callback
interface RepositoryDetails {
    fun getWeatherFromServer(url:String, callback: Callback)
}