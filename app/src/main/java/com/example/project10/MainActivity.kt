// MainActivity.kt
package com.example.project10

import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.project10.ui.theme.Project10Theme
import com.google.android.gms.location.FusedLocationProviderClient
import androidx.compose.runtime.*
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.LocationServices
import java.io.IOException


class MainActivity : ComponentActivity() {
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private var locationPermissionGranted = false
    private var currentLocation: Pair<Double, Double>? by mutableStateOf(null)
//    private var currentTemperature: Float? by mutableStateOf(null)
    private var currentState by mutableStateOf<String?>(null)
    private var currentCity by mutableStateOf<String?>(null)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        // Request location permission
        requestLocationPermission.launch(android.Manifest.permission.ACCESS_FINE_LOCATION)
        setContent {
            Project10Theme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colorScheme.background) {
                    AppNavigator()
                }
            }
        }
    }

    private val requestLocationPermission =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            locationPermissionGranted = isGranted
            if (locationPermissionGranted) {
                requestLocation()
            }
        }

    private fun requestLocation() {
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            fusedLocationClient.lastLocation
                .addOnSuccessListener { location ->
                    location?.let {
                        currentLocation = Pair(it.latitude, it.longitude)
                        getLocationData()
                    }
                }
                .addOnFailureListener { e ->
                    Log.e("Location", "Error getting location", e)
                }
        }
    }

    private fun getLocationData() {
        currentLocation?.let { (latitude, longitude) ->
            try {
                val geocoder = Geocoder(this)
                val addresses: List<Address> = geocoder.getFromLocation(latitude, longitude, 1)!!

                if (addresses.isNotEmpty()) {
                    val address: Address = addresses[0]
                    SharedState.currentState = address.adminArea
                    SharedState.currentCity = address.locality

                    updateComposeUI()

                    // Now you can use 'currentState' and 'currentCity' as needed
                    Log.d("Location", "State: $currentState, City: $currentCity")
                } else {

                }
            } catch (e: IOException) {
                Log.e("Location", "Error getting location data", e)
            }
        }
    }

    private fun updateComposeUI() {
        // Notify Compose to re-compose the UI
        SharedState.currentState = SharedState.currentState
        SharedState.currentCity = SharedState.currentCity
    }
}

@Composable
fun AppNavigator() {
    val navController: NavHostController = rememberNavController()

    NavHost(navController = navController, startDestination = "sensor_activity") {
        composable("sensor_activity") {
            SensorActivityContent(navController)
        }
        composable("gesture_activity") {
            GestureActivityContent()
        }

    }
}



