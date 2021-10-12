package com.eurofins.weatherapp

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.eurofins.weatherapp.weather.WeatherInfo
import com.eurofins.weatherapp.weatherprediction.DailyWeather
import com.eurofins.weatherapp.weatherprediction.WeatherForecastinfo
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class WeatherViewModel : ViewModel() {

    private var _temperatureAndPlace = MutableLiveData<String>()
    val temperatureAndPlace: LiveData<String> get() = _temperatureAndPlace

    private var _description = MutableLiveData<String>()
    val description: LiveData<String> get() = _description

    private var _cloudCover = MutableLiveData<String>()
    val cloudCover: LiveData<String> get() = _cloudCover

    private var _pressure = MutableLiveData<String>()
    val pressure: LiveData<String> get() = _pressure

    private var _dailyForecastButton = MutableLiveData(false)
    val dailyForecastButton get() = _dailyForecastButton

    private var _fetchLocation = true
    val fetchLocation get() = _fetchLocation

    private var _dataset: List<DailyWeather> = listOf()
    val dataset get() = _dataset

    fun fetchWeather(pin: String) {
        fetchCurrentWeather(pin)
    }

    fun fetchPlace(lat: Double, lon: Double) {
        fetchCurrentPlace(lat, lon)
    }

    fun setFetchLocationState() {
        _fetchLocation = false
    }

    fun fetchWeatherForecast(lat: Double, lon: Double) {
        fetchWeatherForecastDaily(lat, lon)
    }

    fun makeButtonInvisible(state: Boolean) {
        _dailyForecastButton.value = state
    }

    private fun fetchCurrentPlace(lat: Double, lon: Double) {
        val weatherForecast = WeatherService.weatherInstance.getWeather(lat, lon)
        weatherForecast.enqueue(object : Callback<WeatherInfo> {
            override fun onResponse(
                call: Call<WeatherInfo>,
                response: Response<WeatherInfo>,
            ) {
                // if response is successful then get the data
                // Or show the error
                if (response.isSuccessful) {
                    val result = response.body()
                    if (result != null) {
                        _temperatureAndPlace.value = " Place: " + result.name +
                                "\nTemp = " + String.format("%.2f", result.main.temp - 273) + "°C"
                        _description.value = "Description\n  ${result.weather[0].main} "
                        _cloudCover.value = "Cloud Cover\n ${result.clouds.all} %"
                        _pressure.value = "Pressure\n ${result.main.pressure} mbar"
                    }
                } else {
                    _temperatureAndPlace.value = response.message()
                }
            }

            override fun onFailure(call: Call<WeatherInfo>, t: Throwable) {
                _temperatureAndPlace.value = "Error in fetching please check your connection"
            }
        })
    }

    private fun fetchWeatherForecastDaily(lat: Double, lon: Double) {
        val weatherForecast = WeatherService.weatherInstance.getWeatherForecast(lat, lon)
        weatherForecast.enqueue(object : Callback<WeatherForecastinfo> {
            @SuppressLint("NewApi")
            override fun onResponse(
                call: Call<WeatherForecastinfo>,
                response: Response<WeatherForecastinfo>,
            ) {
                val result = response.body()

                if (result != null) {
                    _dataset = result.daily
                } else {
                    _temperatureAndPlace.value = "Incorrect Lat or lon"
                }
            }

            override fun onFailure(call: Call<WeatherForecastinfo>, t: Throwable) {
                _temperatureAndPlace.value = "Error in fetching please check your connection"
            }
        })
    }

    private fun fetchCurrentWeather(pin: String) {
        val pinCountry = "$pin,in"
        Log.d("Wagle", " your pincode  $pinCountry")
        val weather = WeatherService.weatherInstance.getWeather(code_country = pinCountry)
        weather.enqueue(object : Callback<WeatherInfo> {
            override fun onResponse(call: Call<WeatherInfo>, response: Response<WeatherInfo>) {
                Log.d("Wagle", " you are inside response")
                if (response.isSuccessful) {
                    val result = response.body()
                    if (result != null) {
                        val temp = result.main.temp - 273
                        _temperatureAndPlace.value = " Place: " + result.name +
                                "\nTemp = " + String.format("%.2f", temp) + "°C"
                        _description.value = "Description\n  ${result.weather[0].main}"
                        _cloudCover.value = "Cloud Cover\n ${result.clouds.all} %"
                        _pressure.value = "Pressure\n ${result.main.pressure} mbar"

                        Log.d("Wagle", "Retrofit response is successfully fetched")
                        Log.d("Wagle", "your  response text ${temperatureAndPlace.value}")
                    }
                } else {
                    Log.d("Wagle", "Error in pincode")
                    _temperatureAndPlace.value = response.message()
                    Log.d("Wagle", "Error response ${response.message()}")
                }
            }

            override fun onFailure(call: Call<WeatherInfo>, t: Throwable) {
                Log.d("Wagle", "Could not fetch the retrofit response $t.message() ")
                _temperatureAndPlace.value = "Error in fetching please check your connection"
            }
        })
        Log.d("Wagle", "your text $temperatureAndPlace")
    }
}