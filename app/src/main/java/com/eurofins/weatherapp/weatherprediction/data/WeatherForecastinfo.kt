package com.eurofins.weatherapp.weatherprediction.data

data class WeatherForecastinfo(val lat: Float, val lon: Float = 0.0F, val timezone: String,
                               val timezone_offset: Long, val current: com.eurofins.weatherapp.weatherprediction.data.CurrentWeather,
                               val daily: List<DailyWeather>)
