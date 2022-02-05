package com.example.kotlin_lessons

import com.google.firebase.messaging.FirebaseMessagingService

class MyFCMService : FirebaseMessagingService() {
    //создание токена пользователя (индификационный номер, черная метка)
    override fun onNewToken(s: String) {
        super.onNewToken(s)
    }
}