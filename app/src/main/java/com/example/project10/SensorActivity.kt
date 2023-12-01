package com.example.project10

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController

//TODO add another sensor of my choice

// SensorActivity.kt
@Composable
fun SensorActivity(navController: NavHostController) {
    var ambientTemperature by remember { mutableStateOf<Float?>(null) }
    var relativeHumidity by remember { mutableStateOf<Float?>(null) }

    val context = LocalContext.current

    // Initialize the sensor manager
    val sensorManager = context.getSystemService(Context.SENSOR_SERVICE) as SensorManager

    // Define the sensor and listener
    val ambientTemperatureSensor = sensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE)
    val temperatureListener = object : SensorEventListener {
        override fun onSensorChanged(event: SensorEvent?) {
            if (event != null) {
                ambientTemperature = event.values[0]
            }
        }

        override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
            // Not needed for this example
        }
    }

    val humiditySensor = sensorManager.getDefaultSensor(Sensor.TYPE_RELATIVE_HUMIDITY)
    val humidityListener = object : SensorEventListener {
        override fun onSensorChanged(event: SensorEvent?) {
            if (event != null) {
                relativeHumidity = event.values[0]
            }
        }

        override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
            // Not needed for this example
        }
    }

    // Register the listener when the composable is first created
    DisposableEffect(context) {
        sensorManager.registerListener(temperatureListener, ambientTemperatureSensor, SensorManager.SENSOR_DELAY_NORMAL)
        sensorManager.registerListener(humidityListener, humiditySensor, SensorManager.SENSOR_DELAY_NORMAL)

        // Unregister the listener when the composable is disposed
        onDispose {
            sensorManager.unregisterListener(temperatureListener)
            sensorManager.unregisterListener(humidityListener)
        }
    }

    Column {
        // Add UI components for displaying sensor data
        Text("Your Name: Kenna Edwards")
        Text("Your Location: ${SharedState.currentState}, ${SharedState.currentCity}")
        Text("Current Temperature: ${ambientTemperature?.toString() ?: "N/A"} °C")
        Text("Current Humidity: ${relativeHumidity?.toString() ?: "N/A"} %")
        // Add a button for fling operation
        Button(onClick = { navController.navigate("gesture_activity") }) {
            Text("Gesture Playground")
        }
    }
}

