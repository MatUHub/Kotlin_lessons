package com.example.kotlin_lessons.model

data class Weather(val city:City = getDefaultCity(), val temperature:Int = 10, val feelsLike:Int = 20)

data class City(var name:String, val lon:Double, val lat:Double)

fun getDefaultCity() = City("Москва", 37.8,55.5)
