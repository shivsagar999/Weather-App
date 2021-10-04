package com.example.weatherapp.data

import android.annotation.SuppressLint
import android.location.Location
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.fragment.findNavController
import com.example.weatherapp.R
import com.example.weatherapp.WeatherService
import com.example.weatherapp.weather.data.WeatherInfo
import com.example.weatherapp.weatherPrediction.data.WeatherForecastinfo
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class WeatherViewModel: ViewModel() {

        private var _temperatureAndPlace = MutableLiveData<String>()
        val temperatureAndPlace: LiveData<String> get() = _temperatureAndPlace

        private var _description = MutableLiveData<String>()
        val description: LiveData<String> get() = _description

        private var _cloudCover = MutableLiveData<String>()
        val cluodCover: LiveData<String> get() = _cloudCover

        private var _pressure = MutableLiveData<String>()
        val pressure: LiveData<String> get() = _pressure

        private var _dataset: MutableList<DailyForecastList> = mutableListOf(DailyForecastList(24.0f,
                                                    "rainy"))
        val dataset get() = _dataset

        private var _listUpdated = MutableLiveData<Boolean>(false)
        val listUpdated get() = _listUpdated


        fun getTemperature(text: String){
            getWeather(text)

        }

    fun getWeatherForecast(lat: Double, lon: Double)
    {
        _dataset.clear()
        getWeatherForecastDaily(lat, lon)

    }



    private fun getWeatherForecastDaily(lat: Double, lon: Double) {

        val weatherForecast = WeatherService.weatherInstance.getWeatherForecast(lat, lon)
        weatherForecast.enqueue(object: Callback<WeatherForecastinfo>{
            override fun onResponse(
                call: Call<WeatherForecastinfo>,
                response: Response<WeatherForecastinfo>
            ) {
                val result = response.body()

                if(result != null){
                   // _temperatureAndPlace.value = "lat: " + result.lat + "\nlon: " + result.lon
                    //_pressure.value = "Pressure\n" + result.current.pressure+" mbar"
                   // _cloudCover.value = " Clouds\n" + result.current.clouds +"%"
                   // _description.value = "Description\n" + result.current.weather[0].description

                    for (item in result.daily){
                        _dataset.add(DailyForecastList((item.temp.day-273),
                            item.weather[0].description.toString()))
                    }

                }
                else{
                    _temperatureAndPlace.value = "Incorrect Lat or lon"
                }
            }

            override fun onFailure(call: Call<WeatherForecastinfo>, t: Throwable) {
                _temperatureAndPlace.value = "Error in fetching please check your connection"
            }
        })

    }


    private fun getWeather(pin: String) {

        val pinCountry = "$pin,in"
        Log.d("Wagle", " your pincode  $pinCountry")

        val weather = WeatherService.weatherInstance.getWeather(code_country = pinCountry)
        weather.enqueue(object: Callback<WeatherInfo> {
            override fun onResponse(call: Call<WeatherInfo>, response: Response<WeatherInfo>) {
                val result = response.body()
                Log.d("Wagle", " you are inside response")
                if(result != null){
                    val temp = result.main.temp - 273
                            _temperatureAndPlace.value = " Place: " + result.name +
                            "\nTemp = " + temp.toString() + " C"
                            _description.value = "Description\n " + result.weather[0].main
                            _cloudCover.value = "Cloud Cover\n" + result.clouds.all.toString() + "%"
                            _pressure.value = "Pressure\n " + result.main.pressure.toString() + " mbar"

                    Log.d("Wagle", "Retrofit response is succesfully fetched")
                    Log.d("Wagle", "your  response text ${temperatureAndPlace.value}")

                }

                else{
                    Log.d("Wagle", "Error in pincode")
                    _temperatureAndPlace.value = "Incorrect Pin"

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