package com.eurofins.weatherapp.weatherPrediction.data

import com.eurofins.weatherapp.weather.data.Weather
import com.eurofins.weatherapp.weatherprediction.data.Rain


data class HourlyWeather(val dt: Long, val temp: Float, val feels_like: Float,
                         val pressure: Long, val humidity: Int, val dew_point: Float, val uvi: Float,
                         val clouds: Int, val visibility: Long, val wind_speed: Float, val wind_deg: Float,
                         val wind_gust: Float, val weather: List<Weather>, val pop: Float, val rain: Rain = Rain()
)
