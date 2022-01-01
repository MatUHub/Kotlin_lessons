package com.example.kotlin_lessons.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.kotlin_lessons.R
import com.example.kotlin_lessons.databinding.ActivityMainBinding
import com.example.kotlin_lessons.view.main.MainFragment

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
    }
}