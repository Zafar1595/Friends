package space.example.friends.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.GestureDetector
import android.view.MotionEvent
import android.widget.Toast
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import space.example.friends.R
import space.example.friends.setting.Setting
import java.lang.Math.abs

class MainActivity : AppCompatActivity() {

    private lateinit var setting: Setting

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        setting = Setting(this)
        setting.setFirstLaunched()

        val navView: BottomNavigationView = findViewById(R.id.nav_view)
        val navController = findNavController(R.id.nav_host_fragment)

        navView.setupWithNavController(navController)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finishAffinity()
    }
}