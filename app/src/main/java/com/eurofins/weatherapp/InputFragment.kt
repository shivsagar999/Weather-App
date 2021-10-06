package com.eurofins.weatherapp


import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.core.content.ContextCompat
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
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
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

        binding.button.setOnClickListener {
            val pin = binding.inputText.text.toString()
            viewModel.getTemperature(pin)
            findNavController().navigate(R.id.action_inputFragment_to_outputFragment)
        }
    }
/*
    override fun onStart() {
        super.onStart()
        fusedLocationProviderClient =
            LocationServices.getFusedLocationProviderClient(requireContext())
        if (ContextCompat.checkSelfPermission(requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION) ==
            PackageManager.PERMISSION_GRANTED
        ) {
            Log.d("Wagle", " Permission has been granted")
            getLocation()
        } else {
            Log.d("Wagle", "Welcome to else block of permission")
            Toast.makeText(requireContext(), "Permission is not granted for fetching location",
                Toast.LENGTH_LONG).show()
        }
    }

    @SuppressLint("MissingPermission")
    private fun getLocation() {
        fusedLocationProviderClient.lastLocation.addOnSuccessListener { location: Location? ->
            Log.d("Wagle", location.toString())
            if (location != null) {
                Log.d("Wagle", location.latitude.toString() + " " + location.longitude.toString())
                viewModel.getWeatherForecast(location.latitude, location.longitude)
                viewModel.getPlace(location.latitude, location.longitude)
                findNavController().navigate(R.id.action_inputFragment_to_outputFragment)

                //findNavController().navigate(R.id.action_permissionFragment_to_outputFragment)
            } else {
                Toast.makeText(context,
                    "We cannot fetch ur location due to some issue please enter ur pincode",
                    Toast.LENGTH_SHORT).show()
                Log.d("Wagle", "you are in get locations else block")

            }
        }
    }*/
}