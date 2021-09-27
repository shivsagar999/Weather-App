package com.example.weatherapp

import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

//api.openweathermap.org/data/2.5/weather?zip={zip code},{country code}&appid=db56c752cfc4cd7455810275b9736ffc

const val baseValue = "https://api.openweathermap.org/data/2.5/"
const val apiKey = "db56c752cfc4cd7455810275b9736ffc"


interface WeatherInterface{

    @GET("weather?appid=$apiKey")
    fun getWeather(@Query("zip")code_country : String):Call<WeatherInfo>

    @GET("weather?appid=$apiKey")
    fun getWeatherUsingLatLong(@Query("lat")Lat: Double,
                               @Query("Lon")Lon: Double):Call<WeatherInfo>
}

object WeatherService {
    val weatherInstance : WeatherInterface
        init {
            val retrofit = Retrofit.Builder().baseUrl(baseValue).
            addConverterFactory(GsonConverterFactory.create()).build()
            weatherInstance = retrofit.create(WeatherInterface::class.java)
        }
}