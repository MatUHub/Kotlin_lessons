package com.example.kotlin_lessons.repository


import com.example.kotlin_lessons.model.WeatherDTO
import com.example.kotlin_lessons.utils.YANDEX_API_END_POINT
import com.example.kotlin_lessons.utils.YANDEX_API_KEY
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query


interface WeatherApi {

    @GET(YANDEX_API_END_POINT)
    fun getWeather(
        @Header(YANDEX_API_KEY) apikey: String,
        @Query("lat") lat: Double,
        @Query("lon") lon: Double
    ): Call<WeatherDTO>

}