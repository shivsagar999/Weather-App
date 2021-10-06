package com.eurofins.weatherapp

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.eurofins.weatherapp.data.WeatherViewModel
import com.eurofins.weatherapp.R
import com.eurofins.weatherapp.databinding.FragmentOutputBinding


class OutputFragment : Fragment() {

    private val viewModel: WeatherViewModel by activityViewModels()

    private val safeArgs: OutputFragmentArgs by navArgs()

    private lateinit var _binding: FragmentOutputBinding

    private val binding get() = _binding


    //private var tempText : String = "Your temp has not yet received"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requireActivity()
            .onBackPressedDispatcher
            .addCallback(this, object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    Log.d("Wagle", "Fragment back pressed invoked")
                    // Do custom work here
                    activity?.finish()

                    // if you want onBackPressed() to be called as normal afterwards
                    if (isEnabled) {
                        isEnabled = false
                        requireActivity().onBackPressed()
                    }
                }
            }
            )
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {

        // Inflate the layout for this fragment
        Log.d("Wagle", " You are inside OnCreateView of OutputFragment")
        _binding = FragmentOutputBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.searchButton.setOnClickListener {
            findNavController().navigate(R.id.action_outputFragment_to_inputFragment)
        }
        Log.d("Wagle", " You are inside OnViewCreated of OutputFragment")
        val temp: String = safeArgs.weatherInfo
        binding.textView.text = temp

        viewModel.temperatureAndPlace.observe(viewLifecycleOwner,
            { newWord -> binding.textView.text = newWord })
        viewModel.description.observe(viewLifecycleOwner,
            { newWord -> binding.description.text = newWord })
        viewModel.cloudCover.observe(viewLifecycleOwner,
            { newWord -> binding.cloudCover.text = newWord })
        viewModel.pressure.observe(viewLifecycleOwner,
            { newWord -> binding.pressure.text = newWord })

        binding.nextScreen.setOnClickListener {
            findNavController().navigate(R.id.action_outputFragment_to_dailyForecastFragment)
        }


    }


}