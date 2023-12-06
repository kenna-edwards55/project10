
package com.example.project10

import android.content.pm.PackageManager
import android.location.*
import android.os.Bundle
import android.os.Looper
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.*
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.example.project10.ui.theme.Project10Theme
import com.google.android.gms.location.FusedLocationProviderClient
import androidx.compose.runtime.*
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import java.io.IOException

/**
 * The main activity of the application, responsible for handling location-related functionality.
 *
 * This activity integrates with the FusedLocationProviderClient to obtain the device's location,
 * requests location permissions, and updates the UI with the current state and city information.
 */

class MainActivity : ComponentActivity() {
    // FusedLocationProviderClient instance for obtaining the device's location
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    // Flag indicating whether location permission is granted
    private var locationPermissionGranted = false
    // Current location represented as a pair of latitude and longitude
    private var currentLocation: Pair<Double, Double>? by mutableStateOf(null)
    // Current state information obtained from the device's location
    private var currentState by mutableStateOf<String?>(null)
    // Current city information obtained from the device's location
    private var currentCity by mutableStateOf<String?>(null)

    private lateinit var locationCallback: LocationCallback

    /**
     * Called when the activity is first created. Initializes the FusedLocationProviderClient,
     * requests location permissions, and sets up the Compose UI.
     *
     * @param savedInstanceState The saved instance state, if any.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialize FusedLocationProviderClient
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

    /**
     * ActivityResultLauncher for handling location permission requests.
     * Updates the locationPermissionGranted flag based on user's response.
     */
    private val requestLocationPermission =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            locationPermissionGranted = isGranted
            if (locationPermissionGranted) {
                requestLocation()
            }
        }

    /**
     * Requests the device's location using the FusedLocationProviderClient.
     * If permission is granted, updates the currentLocation and calls getLocationData().
     */
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

    /**
     * Retrieves additional location data (state and city) based on the currentLocation.
     * Updates SharedState.currentState and SharedState.currentCity, then triggers UI update.
     */
    private fun getLocationData() {
        currentLocation?.let { (latitude, longitude) ->
            try {
                val geocoder = Geocoder(this)
                val addresses: List<Address>? = geocoder.getFromLocation(latitude, longitude, 1)

                if (addresses!!.isNotEmpty()) {
                    Log.d("Addresses", "$addresses")
                    val address: Address = addresses!!.get(0)
                    SharedState.currentState = address.adminArea
                    SharedState.currentCity = address.locality

                    updateComposeUI()


                } else {

                }
            } catch (e: IOException) {
                Log.e("Location", "Error getting location data", e)
            }
        }
    }

    /**
     * Updates the Compose UI by triggering a re-composition based on SharedState changes.
     */
    private fun updateComposeUI() {
        // Notify Compose to re-compose the UI
        SharedState.currentState = SharedState.currentState
        SharedState.currentCity = SharedState.currentCity
    }
}

/**
 * Composable function responsible for setting up the navigation within the application.
 *
 * This function creates a [NavHost] that uses a [NavHostController] to navigate between different destinations.
 * Two destinations, "sensor_activity" and "gesture_activity," are defined, each associated with a specific
 * composable content: [SensorActivityContent] and [GestureActivityContent].
 */
@Composable
fun AppNavigator() {
    // Create a NavController to handle navigation
    val navController: NavHostController = rememberNavController()

    // Define the navigation graph using NavHost
    NavHost(navController = navController, startDestination = "sensor_activity") {
        // Define the composable content for the "sensor_activity" destination
        composable("sensor_activity") {
            SensorActivityContent(navController)
        }
        // Define the composable content for the "gesture_activity" destination
        composable("gesture_activity") {
            GestureActivityContent(navController)
        }
    }
}



