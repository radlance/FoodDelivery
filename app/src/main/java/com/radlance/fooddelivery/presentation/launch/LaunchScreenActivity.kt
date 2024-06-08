package com.radlance.fooddelivery.presentation.launch

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.radlance.fooddelivery.R

@SuppressLint("CustomSplashScreen")
class LaunchScreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_launch_screen)
        supportActionBar?.hide()
        Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(this@LaunchScreenActivity, OnBoardingActivity::class.java)
            startActivity(intent)
            finish()
        }, 1000)
    }
}