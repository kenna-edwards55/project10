package com.example.project10

import BallCanvasFragment
import androidx.activity.ComponentActivity
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.view.KeyEventDispatcher.Component
import androidx.navigation.NavHostController

// GestureActivity.kt
@Composable
fun GestureActivity(navController: NavHostController):ComponentActivity {

    Surface(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Use AndroidView to embed the fragment
        AndroidView(
            factory = { context ->
                // Create your fragment here
               BallCanvasFragment()
            },
            modifier = Modifier
                .fillMaxSize()
        ) { fragmentView ->
            // Fragment view is automatically added to the Compose UI
        }
    }
    // Add UI components for GestureActivity
    // ...
    // Use fragments to divide the screen
    // ...
    // Use GestureDetector or onTouchEvent for red ball operation
    // ...
}

//@Composable
//fun BallControlFragment() {
//    TODO("Not yet implemented")
//}
