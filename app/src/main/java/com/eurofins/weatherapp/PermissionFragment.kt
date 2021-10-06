package com.eurofins.weatherapp

import android.Manifest
import android.annotation.SuppressLint
import android.content.DialogInterface
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.NavHostFragment.findNavController
import androidx.navigation.fragment.findNavController
import com.eurofins.weatherapp.R
import com.eurofins.weatherapp.data.WeatherViewModel
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices


class PermissionFragment : Fragment() {

    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient

    private val viewModel: WeatherViewModel by activityViewModels()

    private val locationAccesser = 10001


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_permission, container, false)
    }



    override fun onStart() {
        super.onStart()

        fusedLocationProviderClient =
            LocationServices.getFusedLocationProviderClient(requireContext())

        //val gpsStatus = lm.isProviderEnabled(LocationManager.GPS_PROVIDER)

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
            getPermission()
            //findNavController().navigate(R.id.action_permissionFragment_to_inputFragment)

        }


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
                    .setMessage("To continue, we need permission to find your location")
                    .setCancelable(false)
                    .setPositiveButton("Ok",
                        DialogInterface.OnClickListener { dialogInterface: DialogInterface, i: Int ->
                            ActivityCompat.requestPermissions(requireActivity(),
                                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                                locationAccesser)
                        })
                    .setNegativeButton("Cancel",
                        DialogInterface.OnClickListener { dialog, which ->
                            findNavController().navigate(R.id.action_permissionFragment_to_inputFragment)
                        }).show()
            } else {
                ActivityCompat.requestPermissions(requireActivity(),
                    arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                    locationAccesser)
            } } else {
            Log.d("Wagle", " Permission Granted")


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

                findNavController().navigate(R.id.action_permissionFragment_to_outputFragment)
            } else {
                Toast.makeText(context,
                    "We cannot fetch ur location due to some issue please enter ur pincode",
                    Toast.LENGTH_SHORT).show()
                Log.d("Wagle", "you are in get locations else block")
                getPermissionForLoacation()
                //findNavController().navigate(R.id.action_permissionFragment_to_inputFragment)
            }
        }
    }



    fun getPermissionForLoacation(){
        AlertDialog.Builder(requireActivity())
            .setMessage("Location Service is disabled")
            .setCancelable(true)
            .setPositiveButton("Retry",
                DialogInterface.OnClickListener { dialogInterface: DialogInterface, i: Int ->
                    getLocation()
                })
            .setNegativeButton("Search Location",
                DialogInterface.OnClickListener { dialog, which ->
                    findNavController().navigate(R.id.action_permissionFragment_to_inputFragment)
                }).show()
    }



}