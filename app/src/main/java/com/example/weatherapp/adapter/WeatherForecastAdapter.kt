package com.example.weatherapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapp.R
import com.example.weatherapp.adapter.WeatherForecastAdapter.WeatherForecastViewHolder
import com.example.weatherapp.data.DailyForecastList
import com.example.weatherapp.weatherPrediction.data.Temp


class WeatherForecastAdapter(private val dataset : List<DailyForecastList>):
    RecyclerView.Adapter<WeatherForecastViewHolder>() {

   // private val dataset : List<DailyForecastList> = listOf(DailyForecastList(24.0f, "rainy"),
     //   DailyForecastList(25.0f, "rainy"), DailyForecastList(26.0f, "rainy"),
     //   DailyForecastList(27.0f, "cloudy"), DailyForecastList(28.0f, "sunny") )


    class WeatherForecastViewHolder(view: View): RecyclerView.ViewHolder(view){
            val textView1: TextView = view.findViewById(R.id.temperature)
            val textView2: TextView = view.findViewById(R.id.description)


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeatherForecastViewHolder {
                val adapterLayout = LayoutInflater.from(parent.context).
                        inflate(R.layout.adapter_list, parent, false)

                return WeatherForecastViewHolder(adapterLayout)

    }

    override fun onBindViewHolder(holder: WeatherForecastViewHolder, position: Int) {
        val item = dataset[position]
        holder.textView1.text = "Day ${position+1}- Temp:" + item.temp.toString()
        holder.textView2.text = "Description: " + item.description.toString()

    }

    override fun getItemCount(): Int {
        return dataset.size
    }
}