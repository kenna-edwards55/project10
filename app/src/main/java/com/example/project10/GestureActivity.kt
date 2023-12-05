
package com.example.project10

import android.content.res.Configuration
import android.graphics.PointF
import androidx.compose.*
import android.view.MotionEvent
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.input.pointer.pointerInteropFilter
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController

@Composable
fun GestureActivityContent(navController:NavHostController) {
    val configuration = LocalConfiguration.current
    if (configuration.orientation == Configuration.ORIENTATION_PORTRAIT) {
        PortraitLayout(navController)
    } else {
        LandscapeLayout(navController)
    }
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
    var startPoint = PointF(0f, 0f)
    var endPoint = PointF(0f,0f)
    var deltaX : Float = 0.0F
    var deltaY : Float = 0.0F

    Canvas(
        modifier = Modifier.fillMaxSize().padding(10.dp).pointerInteropFilter { motionEvent ->
            when (motionEvent.action) {
                MotionEvent.ACTION_DOWN -> {
                    startPoint.set(motionEvent.x, motionEvent.y)
                }

                MotionEvent.ACTION_MOVE -> {
                    deltaX = motionEvent.x - startPoint.x
                    deltaY = motionEvent.y - startPoint.y
                    SharedState.matrix.postTranslate(deltaX, deltaY)
                }


                MotionEvent.ACTION_UP -> {

                    endPoint.set(motionEvent.x, motionEvent.y)

                    if ((startPoint.x == endPoint.x) && (startPoint.y == endPoint.y)) {
                        SharedState.gestureLogs = SharedState.gestureLogs + "You tapped"
                    } else {
                        SharedState.ballPosition = Offset(SharedState.ballPosition.x + deltaX, SharedState.ballPosition.y + deltaY)
                        if (isWithinRange(startPoint.x, endPoint.x, 20f) && (startPoint.y < endPoint.y )) {
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
                    }
                }
            }
            true
        }
    ) {
        drawRect(color = Color.Cyan, alpha = 0.5f, size = size)

        drawCircle(color = Color.Red, 50f, SharedState.ballPosition, 1.0f, style = Fill)
    }
}


@Composable
fun GestureLogsContent() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
        ) {
            items(SharedState.gestureLogs) { gesture ->
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.Gray.copy(alpha = 0.2f)) // Set gray low opacity background
                ) {
                    Text(
                        text = gesture,
                        fontStyle = FontStyle.Italic,
                        modifier = Modifier
                            .padding(16.dp)
                    )
                }
                Divider()
            }
        }
    }
}