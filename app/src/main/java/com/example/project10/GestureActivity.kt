
package com.example.project10

import android.content.res.Configuration
import android.graphics.PointF
import android.util.Log
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

/**
 * Composable function for the content of the GestureActivity, which dynamically chooses
 * between PortraitLayout and LandscapeLayout based on the device's orientation.
 *
 * @param navController The navigation controller used for navigating between different destinations.
 */
@Composable
fun GestureActivityContent(navController:NavHostController) {
    // Get the current device configuration
    val configuration = LocalConfiguration.current
    // Determine the device orientation and display the appropriate layout
    if (configuration.orientation == Configuration.ORIENTATION_PORTRAIT) {
        Log.d("GestureActivity.kt", "Orientation: Portrait")
        PortraitLayout(navController)
    } else {
        Log.d("GestureActivity.kt", "Orientation: Landscape")
        LandscapeLayout(navController)
    }
}

/**
 * Composable function for the portrait orientation layout of the GestureActivity.
 *
 * @param navController The navigation controller used for navigating between different destinations.
 */
@Composable
fun PortraitLayout(navController: NavHostController) {
    Column {
        // Top row containing the BallCanvasContent
        Row(
            modifier = Modifier
                .fillMaxSize()
                .weight(1f)
        ) {
            // Top Composable
            BallCanvasContent()
        }
        // Bottom row containing the GestureLogsContent
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

/**
 * Composable function for the landscape orientation layout of the GestureActivity.
 *
 * @param navController The navigation controller used for navigating between different destinations.
 */
@Composable
fun LandscapeLayout(navController: NavHostController) {
    Row {
        // Left box containing the BallCanvasContent
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Gray)
                .weight(1f)
        ) {
            // Left Composable
            BallCanvasContent()
        }
        // Right box containing the GestureLogsContent
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

/**
 * Checks if a target value is within a specified range around a reference value.
 *
 * @param targetValue The value to check if it's within the range.
 * @param referenceValue The reference value around which the range is defined.
 * @param range The allowed range around the reference value.
 * @return `true` if the target value is within the specified range, `false` otherwise.
 */
fun isWithinRange(targetValue: Float, referenceValue: Float, range: Float): Boolean {
    val lowerBound = referenceValue - range
    val upperBound = referenceValue + range

    return targetValue in lowerBound..upperBound
}

/**
 * Composable function representing the content of a canvas displaying a ball that can be moved
 * through user gestures.
 *
 * @param navController The navigation controller used for navigating between different destinations.
 */
@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun BallCanvasContent() {
    // Variables to store the start and end points of a gesture, as well as the changes in X and Y.
    var startPoint = PointF(0f, 0f)
    var endPoint = PointF(0f,0f)
    var deltaX : Float = 0.0F
    var deltaY : Float = 0.0F

    // Canvas composable with pointer interaction filter for handling touch gestures
    Canvas(
        modifier = Modifier.fillMaxSize().padding(10.dp).pointerInteropFilter { motionEvent ->
            when (motionEvent.action) {
                MotionEvent.ACTION_DOWN -> {
                    // Record the starting point when the user touches down
                    startPoint.set(motionEvent.x, motionEvent.y)
                }

                MotionEvent.ACTION_MOVE -> {
                    // Calculate the change in X and Y and update the translation matrix
                    deltaX = motionEvent.x - startPoint.x
                    deltaY = motionEvent.y - startPoint.y
                    SharedState.matrix.postTranslate(deltaX, deltaY)
                }


                MotionEvent.ACTION_UP -> {
                    // Record the end point when the user lifts their finger
                    endPoint.set(motionEvent.x, motionEvent.y)

                    // Handle different gestures and update gesture logs accordingly
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
        // Draw a semi-transparent cyan rectangle as the background
        drawRect(color = Color.Cyan, alpha = 0.5f, size = size)

        // Draw a red circle representing the ball
        drawCircle(color = Color.Red, 50f, SharedState.ballPosition, 1.0f, style = Fill)
    }
}

/**
 * Composable function for displaying gesture logs in a vertical column.
 * It utilizes a LazyColumn to efficiently handle a potentially large list of gesture logs.
 */
@Composable
fun GestureLogsContent() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        // LazyColumn for efficiently handling a large list of gesture logs
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
        ) {
            // Iterate over the gesture logs stored in SharedState and display each log in a Box
            items(SharedState.gestureLogs) { gesture ->
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.Gray.copy(alpha = 0.2f)) // Set gray low opacity background
                ) {
                    // Display the gesture log text
                    Text(
                        text = gesture,
                        fontStyle = FontStyle.Italic,
                        modifier = Modifier
                            .padding(16.dp)
                    )
                }
                Divider() // Add a divider between gesture logs for visual separation
            }
        }
    }
}