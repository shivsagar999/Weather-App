package com.example.weatherapp.weather.data

data class Sys(val type: Int = 0,val id : Long = 1,
               val country : String, val sunrise : Long, val sunset: Float )