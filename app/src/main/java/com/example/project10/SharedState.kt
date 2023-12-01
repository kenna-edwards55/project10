package com.example.project10

// SharedState.kt
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

object SharedState {
    var currentState by mutableStateOf<String?>(null)
    var currentCity by mutableStateOf<String?>(null)
}
