package com.example.project10

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController

// SensorActivity.kt
@Composable
fun SensorActivity(navController: NavHostController) {
    Column {
        // Add UI components for displaying sensor data
        Text("Your Name")
        Text("Your Location: State, City")
        Text("Current Temperature")
        // Add a button for fling operation
        Button(onClick = { navController.navigate("gesture_activity") }) {
            Text("Gesture Playground")
        }
        // Display sensor data
        // ...
    }
}