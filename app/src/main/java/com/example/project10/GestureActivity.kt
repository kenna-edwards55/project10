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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.runtime.remember
import androidx.fragment.app.FragmentActivity
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
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

fun isWithinRange(targetValue: Float, referenceValue: Float, range: Float): Boolean {
    val lowerBound = referenceValue - range
    val upperBound = referenceValue + range

    return targetValue in lowerBound..upperBound
}
@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun BallCanvasContent() {
    var ballPosition by remember { mutableStateOf(Offset(50f, 50f)) }
    var startPoint = PointF(0f, 0f)
    var endPoint = PointF(0f,0f)
    var matrix by remember { mutableStateOf(android.graphics.Matrix()) }
    var deltaX : Float = 0.0F
    var deltaY : Float = 0.0F

    Canvas(
        modifier = Modifier.fillMaxSize().padding(10.dp).pointerInteropFilter { motionEvent ->
            when (motionEvent.action) {
                MotionEvent.ACTION_DOWN -> {
                    startPoint.set(motionEvent.x, motionEvent.y)
                }

                MotionEvent.ACTION_MOVE -> {
//                    Log.i("BallCanvasContent", "x: ${motionEvent.x}, y: ${motionEvent.y}")

                    deltaX = motionEvent.x - startPoint.x
                    deltaY = motionEvent.y - startPoint.y
                    matrix.postTranslate(deltaX, deltaY)
//                    startPoint.set(motionEvent.x, motionEvent.y)

//                    ballPosition = Offset(ballPosition.x + deltaX, ballPosition.y + deltaY)

                }


                MotionEvent.ACTION_UP -> {
                    ballPosition = Offset(ballPosition.x + deltaX, ballPosition.y + deltaY)
                    endPoint.set(motionEvent.x, motionEvent.y)

                    if ((startPoint.x == endPoint.x) && (startPoint.y == endPoint.y)) {
                        SharedState.gestureLogs = SharedState.gestureLogs + "You tapped"
                    } else if (isWithinRange(startPoint.x, endPoint.x, 20f) && (startPoint.y < endPoint.y )) {
                        SharedState.gestureLogs = SharedState.gestureLogs + "You swiped down"
                    } else if (isWithinRange(startPoint.x, endPoint.x, 20f) && (startPoint.y > endPoint.y )) {
                        SharedState.gestureLogs = SharedState.gestureLogs + "You swiped up"
                    } else if ((startPoint.x > endPoint.x ) && isWithinRange(startPoint.y, endPoint.y, 20f)) {
                        SharedState.gestureLogs = SharedState.gestureLogs + "You swiped left"
                    } else if ((startPoint.x < endPoint.x ) && isWithinRange(startPoint.y, endPoint.y, 20f)) {
                        SharedState.gestureLogs = SharedState.gestureLogs + "You swiped right"
                    } else if ((startPoint.x > endPoint.x) && (startPoint.y < endPoint.y)) {
                        SharedState.gestureLogs = SharedState.gestureLogs + "You swiped down- left"
                    } else if ((startPoint.x > endPoint.x) && (startPoint.y > endPoint.y)) {
                        SharedState.gestureLogs = SharedState.gestureLogs + "You swiped up- left"
                    } else if ((startPoint.x < endPoint.x) &&(startPoint.y < endPoint.y)) {
                        SharedState.gestureLogs = SharedState.gestureLogs + "You swiped down- right"
                    } else if ((startPoint.x < endPoint.x ) &&(startPoint.y > endPoint.y)) {
                        SharedState.gestureLogs = SharedState.gestureLogs + "You swiped up- right"
                    }
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
//    var gestureLogsText : MutableList<String> = mutableListOf("")

    Log.d("GestureLogs", SharedState.gestureLogs.toString())
//    SharedState.gestureLogs += "Hello"
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Text(
            text = "Gesture Logs",
            modifier = Modifier
                .padding(16.dp)
        )

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
        ) {
            items(SharedState.gestureLogs) { gesture ->
                Text(text = gesture)
//                GestureItem(gesture = gesture)
                Divider()
            }
        }
    }
}

//@Composable
//fun GestureItem(gesture: String) {
//    Row(
//        modifier = Modifier
//            .fillMaxWidth()
//            .padding(16.dp),
//        verticalAlignment = Alignment.CenterVertically
//    ) {
//        Spacer(modifier = Modifier.width(8.dp))
//        Text(text = gesture)
//    }
//}
