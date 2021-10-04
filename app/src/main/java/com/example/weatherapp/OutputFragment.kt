package com.example.weatherapp

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.weatherapp.data.WeatherViewModel
import com.example.weatherapp.databinding.FragmentOutputBinding


class OutputFragment : Fragment() {

    private val viewModel: WeatherViewModel by activityViewModels()

    private val safeArgs: OutputFragmentArgs by navArgs()

    private lateinit var _binding: FragmentOutputBinding

    private val binding get() = _binding


    //private var tempText : String = "Your temp has not yet received"


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {


        // Inflate the layout for this fragment
        Log.d("Wagle", " You are inside OnCreateView of OutputFragment")
        _binding = FragmentOutputBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d("Wagle", " You are inside OnViewCreated of OutputFragment")
        val temp: String = safeArgs.weatherInfo.toString()
        binding.textView.text = temp

        viewModel.temperatureAndPlace.observe(viewLifecycleOwner,
               {newWord-> binding.textView.text = newWord })
        viewModel.description.observe(viewLifecycleOwner,
            {newWord-> binding.description.text = newWord })
        viewModel.cluodCover.observe(viewLifecycleOwner,
            {newWord-> binding.cloudCover.text = newWord })
        viewModel.pressure.observe(viewLifecycleOwner,
            {newWord-> binding.pressure.text = newWord })

        binding.nextScreen.setOnClickListener{
            findNavController().navigate(R.id.action_outputFragment_to_dailyForecastFragment)
        }



    }





}