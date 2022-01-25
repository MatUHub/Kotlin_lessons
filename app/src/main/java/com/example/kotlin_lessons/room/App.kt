package com.example.kotlin_lessons.room

import android.app.Application

class App:Application() {
    override fun onCreate() {
        super.onCreate()
        appInstance = this
    }

    companion object{
        private var appInstance: App? = null
        const val DB_NAME = "History.db"
    }
}