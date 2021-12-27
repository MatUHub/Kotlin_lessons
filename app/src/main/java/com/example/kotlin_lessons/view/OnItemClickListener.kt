package com.example.kotlin_lessons.view

import com.example.kotlin_lessons.model.Weather

interface OnItemClickListener {
    fun onItemClick(weather: Weather)
}