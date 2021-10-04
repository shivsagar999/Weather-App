package com.example.weatherapp.weatherPrediction.data

data class WeatherForecastinfo(val lat: Float, val lon: Float = 0.0F, val timezone: String,
                            val timezone_offset: Long, val current: CurrentWeather,
                            val daily: List<DailyWeather>)
