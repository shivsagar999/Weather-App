package com.example.weatherapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.weatherapp.databinding.ActivityMainBinding
import com.example.weatherapp.databinding.ActivityOutputBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class OutputActivity : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_output)



        val letterId = intent.extras?.getString("temp_value").toString()

        val fragment = OutputFragment()
        fragment.setText(letterId)
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.output_fragment, fragment)
            commit()
        }





    }

}