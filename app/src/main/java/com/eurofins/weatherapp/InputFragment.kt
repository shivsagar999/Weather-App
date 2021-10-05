package com.eurofins.weatherapp


import android.annotation.SuppressLint
import android.location.Location
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.eurofins.weatherapp.data.WeatherViewModel
import com.eurofins.weatherapp.databinding.FragmentInputBinding
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices


class InputFragment : Fragment() {

    private val viewModel: WeatherViewModel by activityViewModels()
    private lateinit var _binding: FragmentInputBinding

    lateinit var fusedLocationProviderClient: FusedLocationProviderClient

    private val binding get() = _binding


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
        _binding = FragmentInputBinding.inflate(inflater, container, false)

        return binding.root

    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fusedLocationProviderClient =
            LocationServices.getFusedLocationProviderClient(requireContext())


        binding.button.setOnClickListener {
            getLocation()


            val pin = binding.inputText.text.toString()
            viewModel.getTemperature(pin)
            findNavController().navigate(R.id.action_inputFragment_to_outputFragment)

        }

    }


    @SuppressLint("MissingPermission")
    private fun getLocation() {
        fusedLocationProviderClient.lastLocation.addOnSuccessListener { location: Location? ->
            Log.d("Wagle", location.toString())
            if (location != null) {
                Log.d("Wagle", location.latitude.toString() + " " + location.longitude.toString())
                viewModel.getWeatherForecast(location.latitude, location.longitude)
            } else {
            }
        }
    }


}