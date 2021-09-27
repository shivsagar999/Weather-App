package com.example.weatherapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.weatherapp.databinding.ActivityMainBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


//var resultWeather : WeatherInfo? = null
class MainActivity : AppCompatActivity(), InputFragment.OnItemClickListener {




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

/*          val tempValue =  "Temp = "+ resultWeather!!.main.temp.toString() +" Place = "+
            resultWeather!!.name.toString()
*/

        val fragment = InputFragment()
        supportFragmentManager.beginTransaction().apply{
            add(R.id.input_fragment, fragment)
            setReorderingAllowed(true)
            addToBackStack(null)
            commit()
        }

    }




    override fun onButtonClicked(text: String) {
        super.onButtonClicked(text)

        Log.d("Wagle", "you clicked the mainactivity button $text")

        val fragment1 = OutputFragment()
        fragment1.setText(text)
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.input_fragment, fragment1)
            addToBackStack(null)
            commit()
        }


        //val intent: Intent = Intent(this, OutputActivity::class.java)

        //intent.putExtra("temp_value", text)
        //Log.d("Wagle", "you paseed 40 $intent")
        //this.startActivity(intent)
        //Log.d("Wagle", "you passed 42")
    }

    /*
    private fun getWeather() {
        var pin = binding?.cityPincode?.text.toString()
        var pinCountry :String = "$pin,in"


        val weather = WeatherService.weatherInstance.getWeather(code_country = pinCountry)
        weather.enqueue(object: Callback<WeatherInfo>{
            override fun onResponse(call: Call<WeatherInfo>, response: Response<WeatherInfo>) {
                val resultWeather = response.body()
                if(resultWeather != null){
                    val temp = "Temp = "+ resultWeather.main.temp.toString() +" Place = "+
                            resultWeather.name.toString()
                    binding?.textView?.text = temp


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
*/

}




