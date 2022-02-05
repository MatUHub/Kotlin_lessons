package com.example.kotlin_lessons.view


import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.media.MediaPlayer
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import com.example.kotlin_lessons.view_model.ContentProviderFragment
import com.example.kotlin_lessons.R
import com.example.kotlin_lessons.model.Settings
import com.example.kotlin_lessons.databinding.ActivityMainBinding
import com.example.kotlin_lessons.utils.SHARED_RUS
import com.example.kotlin_lessons.view.history.HistoryFragment
import com.example.kotlin_lessons.view.main.MainFragment
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging

class MainActivity : AppCompatActivity() {

    //Создание ссылки binding (используется для прямого доступа к xml файлам (binding.|id|textView.setText))
    private lateinit var binding: ActivityMainBinding

    companion object {
        private const val NOTIFICATION_ID_1 = 1
        private const val NOTIFICATION_ID_2 = 2
        private const val CHANNEL_ID_1 = "channel_id_1"
        private const val CHANNEL_ID_2 = "channel_id_2"
    }

    //функция для отпрвления уведомлений
    private fun pushNotification() {
        //приводим к NotificationManager
        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

//создаем через Builder уведомления (уведомление 1)

        val notificationBuilder_one = NotificationCompat.Builder(this, CHANNEL_ID_1).apply {
            setSmallIcon(R.drawable.ic_kotlin_logo)
            setContentTitle("Заголовок для $CHANNEL_ID_1")
            setContentText("Сообщение для $CHANNEL_ID_1")
            priority = NotificationCompat.PRIORITY_HIGH
        }

        //создаем через Builder уведомления (уведомление 2)

        val notificationBuilder_two = NotificationCompat.Builder(this, CHANNEL_ID_2).apply {
            setSmallIcon(R.drawable.ic_kotlin_logo)
            setContentTitle("Заголовок для $CHANNEL_ID_2")
            setContentText("Сообщение для $CHANNEL_ID_2")
            priority = NotificationCompat.PRIORITY_DEFAULT
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

        notificationManager.notify(NOTIFICATION_ID_2, notificationBuilder_two.build())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Инициализация binding в проекте
        binding = ActivityMainBinding.inflate(layoutInflater)
        //Введение binding в проект
        setContentView(binding.root)


        if (savedInstanceState == null)
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.container_main, MainFragment.newInstance())
                .commit()

        val sharedPreferences = getSharedPreferences(Settings.SHARED_PREF, Context.MODE_PRIVATE)
        Settings.settingRus = sharedPreferences.getBoolean(SHARED_RUS, false)



    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_screen_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_history -> {
                // определили фрагмент по тегу "tag"
                val fragmentHistory = supportFragmentManager.findFragmentByTag("tag")
                // проверили на null ссылку fragmentHistory
                if (fragmentHistory == null) {
                    supportFragmentManager.apply {
                        beginTransaction()
                            .replace(R.id.container_main, HistoryFragment.newInstance(), "tag")
                            .addToBackStack("")
                            .commit()
                    }
                }
                true
            }
            R.id.menu_content -> {
                supportFragmentManager.beginTransaction()
                    .add(R.id.container_main, ContentProviderFragment.newInstance())
                    .addToBackStack("")
                    .commit()
                true
            }
            else -> {
                super.onOptionsItemSelected(item)
            }
        }
    }
}