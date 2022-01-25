package com.example.kotlin_lessons.room

import android.app.Application
import androidx.room.Room
import java.util.*

class App:Application() {
    override fun onCreate() {
        super.onCreate()
        appInstance = this
    }

    companion object {
        private var appInstance: App? = null
        const val DB_NAME = "History.db"
        private var db: HistoryDatabase? = null

        fun getHistoryDatabase(): HistoryWeatherDao {
            if (db == null) {
                if (appInstance == null) {
                    throw IllformedLocaleException("Ошибка ")
                } else {
                    db = Room.databaseBuilder(appInstance!!, HistoryDatabase::class.java, DB_NAME)
                        .allowMainThreadQueries()
                        .build()
                }
            }
            return db!!.historyWeatherDao()
        }
    }
}