package com.example.project10

// SharedState.kt
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.geometry.Offset

object SharedState {
    var currentState by mutableStateOf<String?>(null)
    var currentCity by mutableStateOf<String?>(null)
    var gestureLogs by mutableStateOf(listOf(""))
    var ballPosition by mutableStateOf(Offset(50f, 50f))
    var matrix by  mutableStateOf(android.graphics.Matrix())
}
