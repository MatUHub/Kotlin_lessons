package com.example.kotlin_lessons.repository

import com.example.kotlin_lessons.BuildConfig
import com.example.kotlin_lessons.model.WeatherDTO
import com.example.kotlin_lessons.model.getRussianCities
import com.example.kotlin_lessons.model.getWorldCities
import com.example.kotlin_lessons.utils.YANDEX_API_URL
import com.google.gson.GsonBuilder
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class RepositoryRemoteImpl : RepositoryDetails {

    override fun getWeatherFromServer(lat: Double,lon: Double, callback: Callback<WeatherDTO>) {
        val retrofit = Retrofit.Builder()
            .baseUrl(YANDEX_API_URL).addConverterFactory(
                GsonConverterFactory.create(
                    GsonBuilder().setLenient().create()
                )
            )
            .build().create(WeatherApi::class.java)
        retrofit.getWeather(BuildConfig.WEATHER_API_KEY,lat, lon).enqueue(callback)
    }
}