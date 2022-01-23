package com.example.kotlin_lessons.repository

interface RepositoryDetails {
    fun getWeatherFromServer(url:String, callback: javax.security.auth.callback.Callback)
}