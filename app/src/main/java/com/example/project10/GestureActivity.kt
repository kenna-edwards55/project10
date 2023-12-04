// GestureActivity.kt
package com.example.project10


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.remember
import androidx.fragment.app.FragmentActivity

import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
class GestureActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            GestureActivityContent()
        }
    }
}

@Composable
fun GestureActivityContent() {
    var logText by remember { mutableStateOf("Log: ") }

    val fragmentManager = (LocalContext.current as? FragmentActivity)?.supportFragmentManager

    Column {
        if (fragmentManager != null) {
            val transaction = fragmentManager.beginTransaction()

            val topFragment = BallCanvasFragment()
            val bottomFragment = GestureLogsFragment()

            transaction.replace(R.id.fragmentContainer1, topFragment)
//            transaction.replace(R.id.fragmentContainer2, bottomFragment) //todo

            transaction.commit()
        } else {
            // Handle the case where fragmentManager is null
        }

        // Your other Compose UI elements here
    }
}

