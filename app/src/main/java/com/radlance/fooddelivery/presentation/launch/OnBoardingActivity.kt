package com.radlance.fooddelivery.presentation.launch

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.radlance.fooddelivery.databinding.ActivityOnBoardingBinding

class OnBoardingActivity : AppCompatActivity() {
    private lateinit var binding: ActivityOnBoardingBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOnBoardingBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.viewPager2.adapter = LaunchSlidePageAdapter(this)
    }
}