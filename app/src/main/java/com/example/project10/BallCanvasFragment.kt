package com.example.project10

// BallCanvasFragment.kt
import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Matrix
import android.graphics.Paint
import android.graphics.PointF
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment


class BallCanvasFragment : Fragment() {

    private lateinit var imageView: ImageView
    private lateinit var bitmap: Bitmap
    private lateinit var canvas: Canvas
    private lateinit var paint: Paint
    private lateinit var matrix: Matrix
    private var startPoint = PointF(0f, 0f)

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_ball_canvas, container, false)
        imageView = view.findViewById(R.id.ballImageView)

        // Initialize bitmap, canvas, paint, and matrix
        bitmap = Bitmap.createBitmap(500, 500, Bitmap.Config.ARGB_8888)
        canvas = Canvas(bitmap)
        paint = Paint().apply { color = Color.RED }
        matrix = Matrix()

        imageView.setImageBitmap(bitmap)

        imageView.setOnTouchListener { _, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    startPoint.set(event.x, event.y)
                }
                MotionEvent.ACTION_MOVE -> {
                    val deltaX = event.x - startPoint.x
                    val deltaY = event.y - startPoint.y
                    matrix.postTranslate(deltaX, deltaY)
                    startPoint.set(event.x, event.y)
                    updateCanvas()
                }
                MotionEvent.ACTION_UP -> {
                    // Perform additional actions on touch up if needed
                }
            }
            true
        }

        return view
    }

    private fun updateCanvas() {
        canvas.drawColor(Color.WHITE)
        canvas.drawCircle(250f, 250f, 50f, paint)
        imageView.invalidate()
    }
}
