package com.example.weatherapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.weatherapp.databinding.ActivityMainBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    var binding: ActivityMainBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        binding?.submitArea?.setOnClickListener{
            getWeather()
        }
    }

    private fun getWeather() {
        var pin = binding?.cityPincode?.text.toString()
        var pinCountry :String = "$pin,in"

        val weather = WeatherService.weatherInstance.getWeather(code_country = pinCountry)
        weather.enqueue(object: Callback<WeatherInfo>{
            override fun onResponse(call: Call<WeatherInfo>, response: Response<WeatherInfo>) {
                val resultWeather = response.body()
                if(resultWeather != null){
                    binding?.textView?.text = "Temp = "+resultWeather.main.temp.toString() +" Place = "+
                            resultWeather.name.toString()
                }
                else{
                    val error = "Sorry we cant find ur Pin"
                    binding?.textView?.text = error
                }

            }

            override fun onFailure(call: Call<WeatherInfo>, t: Throwable) {

                val d = Log.d("Wagle", "Error in fetching", t)


            }
        })

    }


}




