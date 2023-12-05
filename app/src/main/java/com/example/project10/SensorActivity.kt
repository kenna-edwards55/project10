
// SensorActivity.kt
package com.example.project10

import android.content.Context.SENSOR_SERVICE
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavHostController
//import com.example.project10.SharedState.currentCity
//import com.example.project10.SharedState.currentState


class SensorActivity(navController: NavHostController) : ComponentActivity() {

    val navController = navController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        updateComposeUI()
        setContent {
            SensorActivityContent(navController = navController)
        }
    }
}

@Composable
fun SensorActivityContent(navController: NavHostController) {
    var ambientTemperature by remember { mutableStateOf<Float?>(null) }
    var relativeHumidity by remember { mutableStateOf<Float?>(null) }
    var currentCity by remember { mutableStateOf<String?>(null) }
    var currentState by remember { mutableStateOf<String?>(null) }

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
//
//@Composable
//fun SensorActivityUI(
//    ambientTemperature: Float?,
//    relativeHumidity: Float?,
//    navController: NavHostController
//) {
//    Column {
//        // Add UI components for displaying sensor data
//        Text("Your Name: Kenna Edwards", modifier = Modifier.padding(16.dp))
//        Text("Your Location: ${SharedState.currentState}, ${SharedState.currentCity}", modifier = Modifier.padding(16.dp))
//        Text("Current Temperature: ${ambientTemperature?.toString() ?: "N/A"} °C", modifier = Modifier.padding(16.dp))
//        Text("Current Humidity: ${relativeHumidity?.toString() ?: "N/A"} %", modifier = Modifier.padding(16.dp))
//        // Add a button for fling operation
//        FlingButton(navController = navController)
//    }
//}

@Composable
fun SensorActivityUI(
    ambientTemperature: Float?,
    relativeHumidity: Float?,
    navController: NavHostController
) {


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Display user information
        Text(
            text = "Your Name: Kenna Edwards",
            modifier = Modifier.padding(bottom = 8.dp),
            style = TextStyle(
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
        )

        // Display location information
        Text(
            text = "Your Location: ${SharedState.currentState}, ${SharedState.currentCity}",
            modifier = Modifier.padding(bottom = 8.dp),
            style = TextStyle(
                fontSize = 16.sp,
                fontStyle = FontStyle.Italic
            )
        )

        // Display current temperature
        Text(
            text = "Current Temperature: ${ambientTemperature?.toString() ?: "N/A"} °C",
            modifier = Modifier.padding(bottom = 8.dp),
            style = TextStyle(
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Red // Customize color
            )
        )

        // Display current humidity
        Text(
            text = "Current Humidity: ${relativeHumidity?.toString() ?: "N/A"} %",
            modifier = Modifier.padding(bottom = 8.dp),
            style = TextStyle(
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Blue // Customize color
            )
        )

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

fun updateComposeUI() {
    // Notify Compose to re-compose the UI
    SharedState.currentState = SharedState.currentState
    SharedState.currentCity = SharedState.currentCity
}