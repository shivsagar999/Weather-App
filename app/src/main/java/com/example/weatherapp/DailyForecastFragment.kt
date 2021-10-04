package com.example.weatherapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapp.adapter.WeatherForecastAdapter
import com.example.weatherapp.data.WeatherViewModel
import com.example.weatherapp.databinding.FragmentDailyForecastBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [DailyForecastFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class DailyForecastFragment : Fragment() {

    private val viewModel: WeatherViewModel by activityViewModels()

    private lateinit var _binding: FragmentDailyForecastBinding
    val binding get() = _binding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {

            _binding = FragmentDailyForecastBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val recyclerView: RecyclerView = binding.recyclerView

        recyclerView.adapter = WeatherForecastAdapter(viewModel.dataset)
    }


}