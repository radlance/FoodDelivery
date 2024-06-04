package com.radlance.fooddelivery.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.radlance.fooddelivery.databinding.ActivityMainBinding
import com.radlance.fooddelivery.presentation.launch.ScreenSlidePageAdapter

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.viewPager2.adapter = ScreenSlidePageAdapter(this)
    }
}