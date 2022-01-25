package com.example.kotlin_lessons.repository

import com.example.kotlin_lessons.model.*
import com.example.kotlin_lessons.room.App
import com.example.kotlin_lessons.room.HistoryWeatherEntity


class RepositoryLocalImpl : RepositoryCitiesList, RepositoryHistoryWeather {

    override fun getWeatherFromLocalStorageRus() = getRussianCities()

    override fun getWeatherFromLocalStorageWorld() = getWorldCities()

    override fun getAllHistoryWeather(): List<Weather> {
        return converterHistoryEntityToWeather(App.getHistoryDatabase().getAllHistoryWeather())
    }

    fun converterHistoryEntityToWeather(entityList: List<HistoryWeatherEntity>): List<Weather> {
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
        App.getHistoryDatabase().insert(
            convertWeatherToHistoryWeatherEntity(weather)
        )
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