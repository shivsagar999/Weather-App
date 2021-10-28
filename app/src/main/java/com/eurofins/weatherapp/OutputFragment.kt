package com.eurofins.weatherapp

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.eurofins.weatherapp.databinding.FragmentOutputBinding


class OutputFragment : Fragment() {

    private val outputFragmentViewModel: WeatherViewModel by activityViewModels()
    private lateinit var _binding: FragmentOutputBinding
    private val binding get() = _binding

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
        outputFragmentViewModel.temperatureAndPlace.observe(viewLifecycleOwner,
            { newWord -> binding.textView.text = newWord })
        outputFragmentViewModel.description.observe(viewLifecycleOwner,
            { newWord -> binding.description.text = newWord })
        outputFragmentViewModel.cloudCover.observe(viewLifecycleOwner,
            { newWord -> binding.cloudCover.text = newWord })
        outputFragmentViewModel.pressure.observe(viewLifecycleOwner,
            { newWord -> binding.pressure.text = newWord })
        outputFragmentViewModel.dailyForecastButton.observe(viewLifecycleOwner,
            {
                if (it) {
                    binding.nextScreen.visibility = View.GONE
                } else {
                    binding.nextScreen.visibility = View.VISIBLE
                }
            })
        binding.nextScreen.setOnClickListener {
            findNavController().navigate(R.id.action_outputFragment_to_dailyForecastFragment)
        }
    }
}