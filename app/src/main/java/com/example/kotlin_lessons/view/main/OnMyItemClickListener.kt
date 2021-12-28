package com.example.kotlin_lessons.view.main

import com.example.kotlin_lessons.model.Weather

interface OnMyItemClickListener {
    fun onItemClick(weather: Weather)
}