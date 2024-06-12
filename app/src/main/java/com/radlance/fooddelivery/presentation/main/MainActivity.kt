package com.radlance.fooddelivery.presentation.main

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.radlance.fooddelivery.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    companion object {
        private const val SIGN_IN_KEY = "sign_in"
        fun newInstance(context: Context, token: String): Intent {
            return Intent(context, MainActivity::class.java).apply {
                putExtra(SIGN_IN_KEY, token)
            }
        }
    }
}