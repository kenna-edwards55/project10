package com.example.project10

import androidx.compose.runtime.*
import androidx.compose.ui.geometry.Offset

/**
 * Singleton object for managing shared state data across different components in the application.
 * It uses Compose's mutableStateOf to represent reactive state variables.
 */
object SharedState {
    // The current state information obtained from the device's location
    var currentState by mutableStateOf<String?>(null)
    // The current city information obtained from the device's location
    var currentCity by mutableStateOf<String?>(null)
    // List of gesture logs
    var gestureLogs by mutableStateOf(listOf("You entered the gesture playground!"))
    // The current position of the ball in the BallCanvasContent
    var ballPosition by mutableStateOf(Offset(50f, 50f))
    // Matrix for tracking translation transformations in BallCanvasContent
    var matrix by  mutableStateOf(android.graphics.Matrix())
}
