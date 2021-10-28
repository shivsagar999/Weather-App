package com.eurofins.weatherapp

import android.Manifest
import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.Fragment
import android.content.DialogInterface
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.eurofins.weatherapp.databinding.FragmentInputBinding
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices

class InputFragment : Fragment() {

    private val inputFragmentViewModel: WeatherViewModel by activityViewModels()
    private lateinit var _binding: FragmentInputBinding
    private val binding get() = _binding
    private val PATTERN: Regex = Regex("^[1-9][0-9]{5}\$")
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private val locationAccessor = 10001

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
            val result = PATTERN.containsMatchIn(pin)
            if (result) {
                inputFragmentViewModel.fetchWeather(pin)
                inputFragmentViewModel.makeButtonInvisible(true)
                findNavController().navigate(R.id.outputFragment)
            } else {
                Toast.makeText(context, "Please Enter a 6 Digit Valid Pincode", Toast.LENGTH_LONG)
                    .show()
            }
        }
    }

    override fun onStart() {
        super.onStart()
        //fetchLocation variable is used to check whether
        // we are fetching the weather for the first time
        if (inputFragmentViewModel.fetchLocation) {
            fusedLocationProviderClient =
                LocationServices.getFusedLocationProviderClient(requireContext())
            if (ContextCompat.checkSelfPermission(requireContext(),
                    Manifest.permission.ACCESS_FINE_LOCATION) ==
                PackageManager.PERMISSION_GRANTED
            ) {
                Log.d("Wagle", " Permission has been granted")
                fetchLocation()

            } else {
                Log.d("Wagle", "Welcome to else block of permission")
                Toast.makeText(requireContext(), "Permission is not granted for fetching location",
                    Toast.LENGTH_LONG).show()
                getPermission()
            }
        }
    }

    @SuppressLint("MissingPermission")
    private fun fetchLocation() {
        fusedLocationProviderClient.lastLocation.addOnSuccessListener { location: Location? ->
            Log.d("Wagle", location.toString())
            if (location != null) {
                Log.d("Wagle", location.latitude.toString() + " " + location.longitude.toString())
                inputFragmentViewModel.fetchWeatherForecast(location.latitude, location.longitude)
                inputFragmentViewModel.fetchPlace(location.latitude, location.longitude)
                inputFragmentViewModel.makeButtonInvisible(false)
                findNavController().navigate(R.id.action_inputFragment_to_outputFragment)
            } else {
                getPermissionForLocation()
                Toast.makeText(context,
                    "We cannot fetch ur location due to some issue please enter ur pincode",
                    Toast.LENGTH_SHORT).show()
                Log.d("Wagle", "you are in get locations else block")
            }
        }
        inputFragmentViewModel.setFetchLocationState()
    }

    private fun getPermissionForLocation() {
        AlertDialog.Builder(requireContext())
            .setMessage("Location Service is disabled")
            .setCancelable(true)
            .setPositiveButton("Retry"
            ) { _: DialogInterface, _: Int ->
                fetchLocation()
            }
            .setNeutralButton("Search Location"
            ) { _, _ ->
                findNavController().navigate(R.id.inputFragment)
            }
            .setNegativeButton("cancel"
            ) { dialog, _ ->
                dialog.dismiss()
            }.show()
    }

    private fun getPermission() {
        if (ContextCompat.checkSelfPermission(requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION) !=
            PackageManager.PERMISSION_GRANTED
        ) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(requireActivity(),
                    Manifest.permission.ACCESS_FINE_LOCATION)
            ) {
                AlertDialog.Builder(requireActivity())
                    .setMessage("WE need permission to find your location")
                    .setCancelable(false)
                    .setPositiveButton("Ok"
                    ) { _: DialogInterface, _: Int ->
                        requestPermissions(
                            arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                            locationAccessor)
                    }.show()
            } else {
                requestPermissions(
                    arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                    locationAccessor)
            }
        } else {
            Log.d("Wagle", " Permission Granted")

        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray,
    ) {
        if (requestCode == locationAccessor) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Log.d("Wagle", " Permission Granted")
                fetchLocation()
            } else {
                if (!ActivityCompat.shouldShowRequestPermissionRationale(requireActivity(),
                        Manifest.permission.ACCESS_FINE_LOCATION)
                ) {
                    Log.d("Wagle",
                        " Permission Permanently not Granted")
                } else {
                    Log.d("Wagle", " Permission not Granted")
                }
            }
        }
    }
}