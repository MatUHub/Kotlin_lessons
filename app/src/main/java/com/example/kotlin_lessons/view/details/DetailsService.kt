package com.example.kotlin_lessons.view.details

import android.app.IntentService
import android.content.Intent
import com.example.kotlin_lessons.BuildConfig
import com.example.kotlin_lessons.model.WeatherDTO
import com.example.kotlin_lessons.utils.*
import com.google.gson.Gson
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.URL
import java.util.stream.Collectors
import javax.net.ssl.HttpsURLConnection


const val DETAILS_SERVICE_KEY_EXTRAS = "key_"

class DetailsService(name: String = "") : IntentService(name) {

    override fun onHandleIntent(intent: Intent?) {
        intent?.let {
            val lat = intent.getDoubleExtra(BUNDLE_KEY_LAT,0.0)
            val lon = intent.getDoubleExtra(BUNDLE_KEY_LON,0.0)
            loadWeather(lat, lon)
        }
    }

    private fun loadWeather(lat: Double, lon: Double) {
        val url = URL("https://api.weather.yandex.ru/v2/informers?lat=$lat&lon=$lon")
        val httpsURLConnection = (url.openConnection() as HttpsURLConnection).apply {
            requestMethod = "GET"
            readTimeout = 2000
            addRequestProperty(YANDEX_API_KEY, BuildConfig.WEATHER_API_KEY)
        }
        val bufferedReader =
            BufferedReader(InputStreamReader(httpsURLConnection.inputStream))
        val weatherDTO: WeatherDTO? =
            Gson().fromJson(convertBufferToResult(bufferedReader), WeatherDTO::class.java)
       sendBroadcast(Intent(BROADCAST_ACTION).apply {
           putExtra(BUNDLE_KEY_WEATHER, weatherDTO)
       })
    }

    private fun convertBufferToResult(bufferedReader: BufferedReader): String {
        return bufferedReader.lines()
            .collect(Collectors.joining("\n")) // Подчеркивает, но при этом все работает
    }
}