package com.example.kotlin_lessons.repository

import com.example.kotlin_lessons.model.City
import com.example.kotlin_lessons.model.Weather
import com.example.kotlin_lessons.model.getRussianCities
import com.example.kotlin_lessons.model.getWorldCities
import com.example.kotlin_lessons.room.App
import com.example.kotlin_lessons.room.HistoryWeatherEntity


class RepositoryLocalImpl : RepositoryCitiesList, RepositoryHistoryWeather {

    override fun getWeatherFromLocalStorageRus() = getRussianCities()

    override fun getWeatherFromLocalStorageWorld() = getWorldCities()

    override fun getAllHistoryWeather(): List<Weather> {

        return converterHistoryEntityToWeather(App.getHistoryWeatherDao().getAllHistoryWeather())
    }

    private fun converterHistoryEntityToWeather(entityList: List<HistoryWeatherEntity>): List<Weather> {

        return entityList.map {
            Weather(
                City(it.city, 0.0, 0.0),
                it.temperature,
                it.feelsLike,
                it.icon
            )
        }
    }

    override fun saveWeather(weather: Weather) {
        Thread {
            App.getHistoryWeatherDao().insert(
                convertWeatherToHistoryWeatherEntity(weather)
            )
        }.start()
    }

    private fun convertWeatherToHistoryWeatherEntity(weather: Weather) =
        HistoryWeatherEntity(
            0,
            weather.city.name,
            weather.temperature,
            weather.feelsLike,
            weather.icon
        )

}