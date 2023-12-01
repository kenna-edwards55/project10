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
import androidx.core.content.ContextCompat
import com.google.android.gms.location.LocationServices
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import java.io.IOException

data class WeatherResponse(val main: WeatherMain)

data class WeatherMain(val temp: Float)

interface WeatherApi {
    @GET("weather")
    fun getWeather(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("appid") apiKey: String
    ): Call<WeatherResponse>
}
class MainActivity : ComponentActivity() {
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private var locationPermissionGranted = false
    private var currentLocation: Pair<Double, Double>? by mutableStateOf(null)
    private var currentState: String? by mutableStateOf(null)
    private var currentCity: String? by mutableStateOf(null)
    private var currentTemperature: Float? by mutableStateOf(null)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        // Request location permission
//        requestLocationPermission.launch(Manifest.permission.A)
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
//                        getWeatherData()
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
                    currentState = address.adminArea
                    currentCity = address.locality

                    // Now you can use 'currentState' and 'currentCity' as needed
                    Log.d("Location", "State: $currentState, City: $currentCity")
                } else {

                }
            } catch (e: IOException) {
                Log.e("Location", "Error getting location data", e)
            }
        }
    }

//    private fun getWeatherData() {
//        val retrofit = Retrofit.Builder()
//            .baseUrl("http://api.openweathermap.org/data/2.5/")
//            .addConverterFactory(GsonConverterFactory.create())
//            .build()
//
//        val weatherApi = retrofit.create(WeatherApi::class.java)
//        val apiKey = "YOUR_OPENWEATHERMAP_API_KEY"
//
//        currentLocation?.let { (latitude, longitude) ->
//            weatherApi.getWeather(latitude, longitude, apiKey)
//                .enqueue(object : retrofit2.Callback<WeatherResponse> {
//                    override fun onResponse(
//                        call: Call<WeatherResponse>,
//                        response: retrofit2.Response<WeatherResponse>
//                    ) {
//                        if (response.isSuccessful) {
//                            currentTemperature = response.body()?.main?.temp
//                        } else {
//                            Log.e("Weather", "Error: ${response.code()}")
//                        }
//                    }
//
//                    override fun onFailure(call: Call<WeatherResponse>, t: Throwable) {
//                        Log.e("Weather", "Failed to get weather data", t)
//                    }
//                })
//        }
//    }
}

@Composable
fun AppNavigator() {
    val navController: NavHostController = rememberNavController()

    NavHost(navController = navController, startDestination = "sensor_activity") {
        composable("sensor_activity") {
            SensorActivity(navController)
        }
        composable("gesture_activity") {
            GestureActivity(navController)
        }
        // Add a third activity if needed
    }
}
