package com.example.weatherapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.weatherapp.data.WeatherViewModel
import com.example.weatherapp.databinding.FragmentInputBinding


class InputFragment : Fragment() {

    private val viewModel: WeatherViewModel by viewModels()
    private lateinit var _binding: FragmentInputBinding

    private val binding get() = _binding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentInputBinding.inflate(inflater, container, false)

        return binding.root

    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.button.setOnClickListener{

            val pin = binding.inputText.text.toString()
            viewModel.getTemperature(pin)

        }
        viewModel.temperatureAndPlace.observe(viewLifecycleOwner,
            {newWord-> binding.errorText.text = newWord})
    }


}