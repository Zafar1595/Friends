package space.example.friends.ui.viewingWindow

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.SystemClock
import android.provider.MediaStore
import android.provider.MediaStore.Images.Media.getBitmap
import android.provider.MediaStore.Images.Media.insertImage
import android.util.Log
import android.view.GestureDetector
import android.view.MotionEvent
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.net.toUri
import com.bumptech.glide.Glide
import com.google.android.play.core.internal.ac
import space.example.friends.DataTransfer
import space.example.friends.R
import space.example.friends.data.Image
import space.example.friends.databinding.ActivityViewingBinding
import java.io.File
import java.util.ArrayList
import kotlin.math.abs

class ViewingActivity : AppCompatActivity(), GestureDetector.OnGestureListener {

    private lateinit var binding: ActivityViewingBinding

    lateinit var gestureDetector: GestureDetector
    private val swipeThreshold = 100
    private val swipeVelocityThreshold = 100

    var index: Int = 0
    var list: List<Image> = arrayListOf()

    companion object {
        private const val CAMERA_PERMISSION_CODE = 100
        private const val STORAGE_PERMISSION_CODE = 101
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityViewingBinding.inflate(layoutInflater)
        setContentView(binding.root)
        start()
    }

    private fun start(){
        val imageUrl = intent.getStringExtra("imageUrl")
        Glide.with(this).load(imageUrl).into(binding.imageViewViewing)
        gestureDetector = GestureDetector(this)

        list = DataTransfer.urlList
        index = DataTransfer.index
        checkPermission(
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            STORAGE_PERMISSION_CODE
        )

        binding.btnSave.setOnClickListener {
            saveImage(list[index].url.toUri())
        }
    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == CAMERA_PERMISSION_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(
                    this@ViewingActivity,
                    "Camera Permission Granted",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                Toast.makeText(this@ViewingActivity, "Camera Permission Denied", Toast.LENGTH_SHORT)
                    .show()
            }
        } else if (requestCode == STORAGE_PERMISSION_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(
                    this@ViewingActivity,
                    "Storage Permission Granted",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                Toast.makeText(
                    this@ViewingActivity,
                    "Storage Permission Denied",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    // Function to check and request permission.
    private fun checkPermission(permission: String, requestCode: Int) {
        if (ContextCompat.checkSelfPermission(
                this@ViewingActivity,
                permission
            ) == PackageManager.PERMISSION_DENIED
        ) {
            // Requesting the permission
            ActivityCompat.requestPermissions(
                this@ViewingActivity,
                arrayOf(permission),
                requestCode
            )
        }
        /*else {

            Toast.makeText(this@ViewingActivity, "Permission already granted", Toast.LENGTH_SHORT)
                .show()
        }*/
    }

    private fun imageView(list: List<Image>, index: Int) {
        Glide.with(this).load(list[index].url).into(binding.imageViewViewing)
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
//        Toast.makeText(this, p0?.downTime.toString(), Toast.LENGTH_SHORT).show()
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
            if (abs(diffX) > abs(diffY)) {
                if (abs(diffX) > swipeThreshold && abs(velocityX) > swipeVelocityThreshold) {
                    if (diffX > 0) {
                        if (index > 0) {
                            index--
                            imageView(list, index)
                        } else {
                            index = list.size - 1
                            imageView(list, index)
                        }
                        Log.i("indext", index.toString())
//                            "Left to Right swipe gesture",

                    } else {
                        if (index < list.size - 1) {
                            index++
                            imageView(list, index)
                        } else {
                            index = 0
                            imageView(list, index)
                        }
                        Log.i("indext", index.toString())
//                            "Right to Left swipe gesture",

                    }
                }
            }
        } catch (exception: Exception) {
            exception.printStackTrace()
        }
        return true
    }

    // Method to save an image to gallery and return uri
    private fun saveImage(uri: Uri) {
//        // Get the bitmap from drawable object
//        val bitmap = getBitmap(this.contentResolver, uri)
//
//        val savedImageURL = insertImage(
//            this.contentResolver,
//            bitmap,
//            System.currentTimeMillis().toString(),
//            "Image of ${System.currentTimeMillis()}"
//        )
    }
}