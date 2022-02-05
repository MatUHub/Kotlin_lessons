package com.example.kotlin_lessons

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

//ServerKey
//AAAAh2t1WTg:APA91bHG2U4ebSvDBD4Q9AC4PHCPG79xvBurpXj5S4IZFIfqT3-S743lFNm17Z8vx1oKhFl_-X6QWDw_aFWVG90P1C1mqNi-Ws6z-zR26igKBqoVrelofGMbm5ynrUZkhz0ywcMhw0Wk

class MyFCMService : FirebaseMessagingService() {
    //создание токена пользователя (индификационный номер, черная метка)
    override fun onNewToken(s: String) {
        super.onNewToken(s)
    }

    override fun onMessageReceived(message: RemoteMessage) {
        val data = message.data
        if (data.isNotEmpty()) {
            val title = data[KEY_TITLE]
            val message = data[KEY_MESSAGE]
            if (!title.isNullOrBlank() && !message.isNullOrBlank())
                pushNotification(title, message)
        }
    }


    companion object {
        private const val NOTIFICATION_ID_1 = 1
        private const val CHANNEL_ID_1 = "channel_id_1"
        private const val KEY_TITLE = "myTitle"
        private const val KEY_MESSAGE = "myMessage"
        /*  private const val NOTIFICATION_ID_2 = 2
          private const val CHANNEL_ID_2 = "channel_id_2"*/
    }

    //функция для отправления уведомлений
    private fun pushNotification(title: String, message: String) {
        //приводим к NotificationManager
        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

//создаем через Builder уведомления (уведомление 1)

        val notificationBuilder_one = NotificationCompat.Builder(this, CHANNEL_ID_1).apply {
            setSmallIcon(R.drawable.ic_kotlin_logo)
            setContentTitle(title)
            setContentText(message)
            priority = NotificationCompat.PRIORITY_HIGH
        }

        //создние канала 1

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channelNameOne = " Name $CHANNEL_ID_1"
            val channelDescriptionOne = "Description for $CHANNEL_ID_1"
            val channelPriorityOne = NotificationManager.IMPORTANCE_HIGH

            val channelOne =
                NotificationChannel(CHANNEL_ID_1, channelNameOne, channelPriorityOne).apply {
                    description = channelDescriptionOne
                }

            notificationManager.createNotificationChannel(channelOne)
        }
// ID необходимо для удаления уведомления либо для получения информации на какуое уведомление был клик
        notificationManager.notify(NOTIFICATION_ID_1, notificationBuilder_one.build())


        /* //создаем через Builder уведомления (уведомление 2)

         val notificationBuilder_two = NotificationCompat.Builder(this, CHANNEL_ID_2).apply {
             setSmallIcon(R.drawable.ic_kotlin_logo)
             setContentTitle("Заголовок для $CHANNEL_ID_2")
             setContentText("Сообщение для $CHANNEL_ID_2")
             priority = NotificationCompat.PRIORITY_DEFAULT
         }

         //создние канала 2

         if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
             val channelNameTwo = " Name $CHANNEL_ID_2"
             val channelDescriptionTwo = "Description for $CHANNEL_ID_2"
             val channelPriorityTwo = NotificationManager.IMPORTANCE_HIGH

             val channelTwo =
                 NotificationChannel(CHANNEL_ID_2, channelNameTwo, channelPriorityTwo).apply {
                     description = channelDescriptionTwo
                 }

             notificationManager.createNotificationChannel(channelTwo)
         }

         notificationManager.notify(NOTIFICATION_ID_2, notificationBuilder_two.build())*/
    }
}