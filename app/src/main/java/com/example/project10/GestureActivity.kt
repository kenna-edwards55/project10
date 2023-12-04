// GestureActivity.kt
package com.example.project10
import android.content.res.Configuration
import android.graphics.PointF
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.remember
import androidx.fragment.app.FragmentActivity

import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.EmptyBuildDrawCacheParams.size
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.input.pointer.pointerInteropFilter
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.widget.ConstraintSet.Motion
import androidx.navigation.NavHostController

//class GestureActivity : ComponentActivity() {
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//
//        setContent {
//            GestureActivityContent()
//        }
//    }
//}

@Composable
fun GestureActivityContent(navController:NavHostController) {
    val configuration = LocalConfiguration.current
    if (configuration.orientation == Configuration.ORIENTATION_PORTRAIT) {
        PortraitLayout(navController)
    } else {
        LandscapeLayout(navController)
    }

    var logText by remember { mutableStateOf("Log: ") }


}

@Composable
fun PortraitLayout(navController: NavHostController) {
    Column {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .weight(1f)
        ) {
            // Top Composable
            BallCanvasContent()
        }
        Row(
            modifier = Modifier
                .fillMaxSize()
                .weight(1f)
        ) {
            // Bottom Composable
            GestureLogsContent()
        }
    }
}

@Composable
fun LandscapeLayout(navController: NavHostController) {
    Row {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Gray)
                .weight(1f)
        ) {
            // Left Composable
            BallCanvasContent()
        }
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.LightGray)
                .weight(1f)
        ) {
            // Right Composable
            GestureLogsContent()
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun BallCanvasContent() {
    var ballPosition by remember { mutableStateOf(Offset(0f, 0f)) }
//    var startPoint by remember { mutableStateOf(PointF(0f, 0f)) }
    var startPoint = PointF(0f, 0f)
    var matrix by remember { mutableStateOf(android.graphics.Matrix()) }

    Canvas(
        modifier = Modifier.fillMaxSize().padding(10.dp).pointerInteropFilter { motionEvent ->
            when (motionEvent.action) {
                MotionEvent.ACTION_DOWN -> {
                    startPoint.set(motionEvent.x, motionEvent.y)
                }

                MotionEvent.ACTION_MOVE -> {
                    Log.i("BallCanvasContent", "x: ${motionEvent.x}, y: ${motionEvent.y}")

                    val deltaX = motionEvent.x - startPoint.x
                    val deltaY = motionEvent.y - startPoint.y
                    matrix.postTranslate(deltaX, deltaY)
                    startPoint.set(motionEvent.x, motionEvent.y)

                    ballPosition = Offset(ballPosition.x + deltaX, ballPosition.y + deltaY)
                    // Invalidate to trigger a redraw

                }

                MotionEvent.ACTION_UP -> {
                    // Handle touch up
                }
            }
            true
        }
    ) {
        drawRect(color = Color.Blue, size = size)

        drawCircle(color = Color.Red, 100f, ballPosition, 1.0f, style = Fill)
    }
}

@Composable
fun GestureLogsContent() {

}






//    val fragmentManager = (LocalContext.current as? FragmentActivity)?.supportFragmentManager
//
//    Column {
//        if (fragmentManager != null) {
//            val transaction = fragmentManager.beginTransaction()
//
//            val topFragment = BallCanvasFragment()
//            val bottomFragment = GestureLogsFragment()
//
//            transaction.replace(R.id.fragmentContainer1, topFragment)
////            transaction.replace(R.id.fragmentContainer2, bottomFragment) //todo
//
//            transaction.commit()
//        } else {
//            // Handle the case where fragmentManager is null
//        }

// Your other Compose UI elements here
//    }