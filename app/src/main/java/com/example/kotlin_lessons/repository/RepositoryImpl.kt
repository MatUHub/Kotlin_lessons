package com.example.kotlin_lessons.repository

import com.example.kotlin_lessons.BuildConfig
import com.example.kotlin_lessons.model.getRussianCities
import com.example.kotlin_lessons.model.getWorldCities
import com.example.kotlin_lessons.utils.YANDEX_API_KEY
import okhttp3.*


class RepositoryImpl: RepositoryCitiesList,RepositoryDetails {

    override fun getWeatherFromLocalStorageRus()= getRussianCities()

    override fun getWeatherFromLocalStorageWorld()= getWorldCities()

    override fun getWeatherFromServer(url: String, callback: Callback) {
        val builder= Request.Builder().apply {
            header(YANDEX_API_KEY, BuildConfig.WEATHER_API_KEY)
            url(url)
        }
        OkHttpClient().newCall(builder.build()).enqueue(callback)
    }
}