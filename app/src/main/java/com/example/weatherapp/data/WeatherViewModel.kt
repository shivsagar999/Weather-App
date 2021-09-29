package com.example.weatherapp.data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.weatherapp.weather.data.WeatherInfo
import com.example.weatherapp.WeatherService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class WeatherViewModel: ViewModel() {

        private var _temperatureAndPlace = MutableLiveData<String>()

        val temperatureAndPlace: LiveData<String> get() = _temperatureAndPlace

        fun getTemperature(text: String){
            getWeather(text)

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
                    _temperatureAndPlace.value = " Place = " + result.name +
                            "\nTemp = " + temp.toString() + " C" +
                            "\nDescription = " + result.weather[0].main +
                            "\nCloud Cover = " + result.clouds.all.toString() + "%" +
                            "\nPressure = " + result.main.pressure.toString() + " mbar"

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