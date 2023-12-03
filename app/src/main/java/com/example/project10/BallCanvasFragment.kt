import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import android.graphics.Matrix
import android.graphics.PointF
import com.example.project10.R

class BallCanvasFragment : Fragment() {

    private lateinit var imageView: ImageView
    private var matrix = Matrix()
    private val startPoint = PointF()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_gesture_playground, container, false)
        imageView = view.findViewById(R.id.ballImageView)

        imageView.setOnTouchListener { _, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    startPoint.set(event.x, event.y)
                }
                MotionEvent.ACTION_MOVE -> {
                    val deltaX = event.x - startPoint.x
                    val deltaY = event.y - startPoint.y
                    matrix.postTranslate(deltaX, deltaY)
                    imageView.imageMatrix = matrix
                    startPoint.set(event.x, event.y)
                }
                MotionEvent.ACTION_UP -> {
                    // You can perform additional actions on touch up if needed
                    Toast.makeText(requireContext(), "Ball moved", Toast.LENGTH_SHORT).show()
                }
            }
            true
        }

        return view
    }
}
