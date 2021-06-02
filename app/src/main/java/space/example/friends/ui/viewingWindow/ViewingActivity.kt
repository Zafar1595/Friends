package space.example.friends.ui.viewingWindow

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.GestureDetector
import android.view.MotionEvent
import android.widget.Toast
import com.bumptech.glide.Glide
import space.example.friends.R
import space.example.friends.databinding.ActivityViewingBinding
import java.util.ArrayList

class ViewingActivity : AppCompatActivity(), GestureDetector.OnGestureListener {

    private lateinit var binding: ActivityViewingBinding

    lateinit var gestureDetector: GestureDetector
    private val swipeThreshold = 100
    private val swipeVelocityThreshold = 100

//    var adapter = ViewingAdapter()

    var index: Int = 0
    var list: ArrayList<String> = arrayListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityViewingBinding.inflate(layoutInflater)
        setContentView(binding.root)
        list = intent.getStringArrayListExtra("list")!!
        index = intent.getIntExtra("index", 0)
        Glide.with(this).load(list[index]).into(binding.imageViewViewing)
        gestureDetector = GestureDetector(this)

    }

    private fun setData() {
//        adapter.setMod(list, index)
//        adapter.models = list
    }

    private fun imageView(list: ArrayList<String>, index: Int) {
        Glide.with(this).load(list[index]).into(binding.imageViewViewing)
    }


    override fun onTouchEvent(event: MotionEvent): Boolean {
        return if (gestureDetector.onTouchEvent(event)) {
            true
        } else {
            super.onTouchEvent(event)
        }
    }

    override fun onDown(p0: MotionEvent?): Boolean {
        return false
    }

    override fun onShowPress(p0: MotionEvent?) {
        return
    }

    override fun onSingleTapUp(p0: MotionEvent?): Boolean {
        return false
    }

    override fun onScroll(p0: MotionEvent?, p1: MotionEvent?, p2: Float, p3: Float): Boolean {
        return false
    }

    override fun onLongPress(p0: MotionEvent?) {
        return
    }

    override fun onFling(
        e1: MotionEvent,
        e2: MotionEvent,
        velocityX: Float,
        velocityY: Float
    ): Boolean {
        try {
            val diffY = e2.y - e1.y
            val diffX = e2.x - e1.x
            if (Math.abs(diffX) > Math.abs(diffY)) {
                if (Math.abs(diffX) > swipeThreshold && Math.abs(velocityX) > swipeVelocityThreshold) {
                    if (diffX > 0) {
                        if (index > 0) {
                            index--
                            imageView(list, index)
                        } else {
                            index = list.size - 1
                            imageView(list, index)
                        }
                        Log.i("indext", index.toString())
//                        Toast.makeText(
//                            applicationContext,
//                            "Left to Right swipe gesture",
//                            Toast.LENGTH_SHORT
//                        ).show()
                    } else {
                        if (index < list.size - 1) {
                            index++
                            imageView(list, index)
                        } else {
                            index = 0
                            imageView(list, index)
                        }
                        Log.i("indext", index.toString())
//                        Toast.makeText(
//                            applicationContext,
//                            "Right to Left swipe gesture",
//                            Toast.LENGTH_SHORT
//                        ).show()
                    }
                }
            }
        } catch (exception: Exception) {
            exception.printStackTrace()
        }
        return true
    }

}