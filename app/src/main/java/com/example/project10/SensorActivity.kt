
// SensorActivity.kt
package com.example.project10

import android.content.Context.SENSOR_SERVICE
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavHostController


class SensorActivity(navController: NavHostController) : ComponentActivity() {

    val navController = navController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        setContent {
            SensorActivityContent(navController = navController)
        }
    }
}

@Composable
fun SensorActivityContent(navController: NavHostController) {
    var ambientTemperature by remember { mutableStateOf<Float?>(null) }
    var relativeHumidity by remember { mutableStateOf<Float?>(null) }

    val context = LocalContext.current

    // Initialize the sensor manager
    val sensorManager = context.getSystemService(SENSOR_SERVICE) as SensorManager

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

    Surface(
        modifier = Modifier.fillMaxWidth(),
        color = MaterialTheme.colorScheme.background
    ) {
        SensorActivityUI(
            ambientTemperature = ambientTemperature,
            relativeHumidity = relativeHumidity,
            navController = navController
        )
    }
}

@Composable
fun SensorActivityUI(
    ambientTemperature: Float?,
    relativeHumidity: Float?,
    navController: NavHostController
) {
    Column {
        // Add UI components for displaying sensor data
        Text("Your Name: Kenna Edwards", modifier = Modifier.padding(16.dp))
        Text("Your Location: ${SharedState.currentState}, ${SharedState.currentCity}", modifier = Modifier.padding(16.dp))
        Text("Current Temperature: ${ambientTemperature?.toString() ?: "N/A"} °C", modifier = Modifier.padding(16.dp))
        Text("Current Humidity: ${relativeHumidity?.toString() ?: "N/A"} %", modifier = Modifier.padding(16.dp))
        // Add a button for fling operation
        FlingButton(navController = navController)
    }
}

@Composable
fun FlingButton(navController: NavHostController) {
    Button(
        onClick = {
            navController.navigate("gesture_activity")
        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text("Gesture Playground")
    }
}
