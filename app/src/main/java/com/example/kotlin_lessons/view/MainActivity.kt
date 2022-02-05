package com.example.kotlin_lessons.view


import android.content.Context
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.example.kotlin_lessons.R
import com.example.kotlin_lessons.databinding.ActivityMainBinding
import com.example.kotlin_lessons.model.Settings
import com.example.kotlin_lessons.utils.SHARED_RUS
import com.example.kotlin_lessons.view.history.HistoryFragment
import com.example.kotlin_lessons.view.main.MainFragment
import com.example.kotlin_lessons.view_model.ContentProviderFragment

class MainActivity : AppCompatActivity() {

    //Создание ссылки binding (используется для прямого доступа к xml файлам (binding.|id|textView.setText))
    private lateinit var binding: ActivityMainBinding



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