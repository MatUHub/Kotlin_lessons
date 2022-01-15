package com.example.kotlin_lessons.utils

import android.os.Looper
import com.example.kotlin_lessons.model.WeatherDTO
import com.google.gson.Gson
import java.io.BufferedReader

import java.io.InputStreamReader
import javax.net.ssl.HttpsURLConnection
import java.net.URL
import java.util.logging.Handler
import java.util.stream.Collectors


class WeatherLoader(
    private val lat: Double,
    private val lon: Double,
    private val onWeatherLoaded: OnWeatherLoaded
) {
    fun loadWeather() {
        Thread {
            val url = URL("https://api.weather.yandex.ru/v2/informers?lat=$lat&lon=$lon")
            val httpsURLConnection = (url.openConnection() as HttpsURLConnection).apply {
                requestMethod = "GET"
                readTimeout = 2000
                addRequestProperty("X-Yandex-API-Key", "a58fb4e9-4923-470a-a28a-8330708129c8")
            }
            val bufferedReader = BufferedReader(InputStreamReader(httpsURLConnection.inputStream))
            val weatherDTO: WeatherDTO? =
                Gson().fromJson(convertBufferToResult(bufferedReader), WeatherDTO::class.java)
            android.os.Handler(Looper.getMainLooper()).post{
                onWeatherLoaded.onLoaded(weatherDTO)
            }
        }.start()
    }

    private fun convertBufferToResult(bufferedReader: BufferedReader): String {
        return bufferedReader.lines()
            .collect(Collectors.joining("\n")) // Подчеркивает, но при этом все работает
    }

    interface OnWeatherLoaded {
        fun onLoaded(weatherDTO: WeatherDTO?)
        fun onFailed()
    }
}