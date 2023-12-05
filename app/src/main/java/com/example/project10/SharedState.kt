package com.example.project10

import androidx.compose.runtime.*
import androidx.compose.ui.geometry.Offset

object SharedState {
    var currentState by mutableStateOf<String?>(null)
    var currentCity by mutableStateOf<String?>(null)
    var gestureLogs by mutableStateOf(listOf("You entered the gesture playground!"))
    var ballPosition by mutableStateOf(Offset(50f, 50f))
    var matrix by  mutableStateOf(android.graphics.Matrix())
}
