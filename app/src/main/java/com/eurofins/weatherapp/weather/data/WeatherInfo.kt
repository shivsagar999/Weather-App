package com.eurofins.weatherapp.weather.data

import com.eurofins.weatherapp.weather.data.Coordinates
import com.eurofins.weatherapp.weather.data.MainWeatherFeatures
import com.eurofins.weatherapp.weather.data.Weather
import com.example.weatherapp.weather.data.Wind

data class WeatherInfo(val coord: Coordinates, val weather: List<Weather>, val base: String,
                       val main : MainWeatherFeatures,
                       val visibility : Long, val wind: Wind, val clouds: Clouds,
                       val dt : Long, val system : Sys, val timeZone: Long,
                       val id : Int, val name: String, val cod : Int   )