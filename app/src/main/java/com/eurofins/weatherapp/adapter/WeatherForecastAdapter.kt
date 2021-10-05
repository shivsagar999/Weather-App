package com.eurofins.weatherapp.adapter

import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.eurofins.weatherapp.R
import com.eurofins.weatherapp.data.DailyForecastList

import com.eurofins.weatherapp.adapter.WeatherForecastAdapter.WeatherForecastViewHolder
import java.text.SimpleDateFormat
import java.util.*


class WeatherForecastAdapter(private val dataset: List<DailyForecastList>) :
    RecyclerView.Adapter<WeatherForecastViewHolder>() {

    // private val dataset : List<DailyForecastList> = listOf(DailyForecastList(24.0f, "rainy"),
    //   DailyForecastList(25.0f, "rainy"), DailyForecastList(26.0f, "rainy"),
    //   DailyForecastList(27.0f, "cloudy"), DailyForecastList(28.0f, "sunny") )

    val date = Date()

    class WeatherForecastViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textView1: TextView = view.findViewById(R.id.temperature)
        val textView2: TextView = view.findViewById(R.id.description)


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeatherForecastViewHolder {
        val adapterLayout =
            LayoutInflater.from(parent.context).inflate(R.layout.adapter_list, parent, false)

        return WeatherForecastViewHolder(adapterLayout)

    }


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: WeatherForecastViewHolder, position: Int) {
        val item = dataset[position]
        val desc = item.description
        val temperature = item.temp.toString()
        //val pos = position + 1
        date.time = item.date
        val dateString = SimpleDateFormat("MM, dd, yyyy").format(date)



        holder.textView1.text = "$dateString - Temp: ${temperature}"
        holder.textView2.text = "Description: $desc"

    }

    override fun getItemCount(): Int {
        return dataset.size
    }
}


