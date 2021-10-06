package com.eurofins.weatherapp.permission

import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.FragmentActivity
import java.util.jar.Manifest

class FragmentPermissionHelper {

    fun startPermissionRequest(fragmentActivity: FragmentActivity,
                                resultCatcher: FragmentPermissionInterface,
                                    manifest: String){
        // Register the permissions callback, which handles the user's response to the
// system permissions dialog. Save the return value, an instance of
// ActivityResultLauncher. You can use either a val, as shown in this snippet,
// or a lateinit var in your onAttach() or onCreate() method.
        val requestPermissionLauncher =
            fragmentActivity.registerForActivityResult(ActivityResultContracts.RequestPermission()
            ) { isGranted: Boolean ->
                if (isGranted) {
                    // Permission is granted. Continue the action or workflow in your
                    // app.
                } else {
                    // Explain to the user that the feature is unavailable because the
                    // features requires a permission that the user has denied. At the
                    // same time, respect the user's decision. Don't link to system
                    // settings in an effort to convince the user to change their
                    // decision.
                }
            }
        requestPermissionLauncher.launch(
            android.Manifest.permission.ACCESS_FINE_LOCATION)
    }
}