////package com.example.project10
//
//package com.example.project10
//// BallCanvasFragment.kt
//import android.os.Bundle
//import android.view.LayoutInflater
//import android.view.MotionEvent
//import android.view.View
//import android.view.ViewGroup
//import android.widget.FrameLayout
//import androidx.compose.foundation.Canvas
//import androidx.compose.foundation.gestures.detectTransformGestures
//import androidx.compose.foundation.layout.fillMaxSize
//import androidx.compose.foundation.layout.padding
//import androidx.compose.runtime.*
//import androidx.compose.ui.Modifier
//import android.graphics.PointF
//import android.util.Log
//import androidx.compose.ui.ExperimentalComposeUiApi
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.graphics.PaintingStyle
//import androidx.compose.ui.graphics.drawscope.DrawStyle
//import androidx.compose.ui.graphics.drawscope.Fill
//import androidx.compose.ui.input.pointer.pointerInteropFilter
//import androidx.compose.ui.platform.ComposeView
//import androidx.compose.ui.unit.dp
//import androidx.fragment.app.Fragment
//
//class GestureLogsFragment : Fragment() {
//
//    private var startPoint = PointF(0f, 0f)
//
//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//        Log.d("BallCanvasFragment", "Trying to compose")
//        return ComposeView(requireContext()).apply {
//            setContent {
//                BallCanvasContent()
//            }
//        }
//    }
//
//    @OptIn(ExperimentalComposeUiApi::class)
//    @Composable
//    fun BallCanvasContent() {
//        var matrix by remember { mutableStateOf(android.graphics.Matrix()) }
//
//        Canvas(
//            modifier = Modifier.fillMaxSize().padding(10.dp).pointerInteropFilter { motionEvent ->
//                when (motionEvent.action) {
//                    MotionEvent.ACTION_DOWN -> {
//                        startPoint.set(motionEvent.x, motionEvent.y)
//                    }
//
//                    MotionEvent.ACTION_MOVE -> {
//                        val deltaX = motionEvent.x - startPoint.x
//                        val deltaY = motionEvent.y - startPoint.y
//                        matrix.postTranslate(deltaX, deltaY)
//                        startPoint.set(motionEvent.x, motionEvent.y)
//                        // Invalidate to trigger a redraw
////                        invalidate()
//                    }
//
//                    MotionEvent.ACTION_UP -> {
//                        // Handle touch up
//                    }
//                }
//                true
//            }
//        ) {
//            drawCircle(color = Color.Red, 250f, this.center, 1.0f, style = Fill)
//        }
//    }
//}