package com.radlance.fooddelivery.presentation.main

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.radlance.fooddelivery.R
import com.radlance.fooddelivery.databinding.ActivityMainBinding
import com.radlance.fooddelivery.presentation.catalog.core.FragmentCatalog

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .add(binding.containerMain.id, FragmentCatalog.newInstance())
                .commit()
        }

        val buttons = arrayOf(
            binding.buttonHomePage,
            binding.buttonShoppingCart,
            binding.buttonUser,
            binding.buttonHistory
        )
        val filledImages = arrayOf(
            R.drawable.ic_home_filled,
            R.drawable.ic_shopping_cart_filled,
            R.drawable.ic_user_filled,
            R.drawable.ic_sharp_history_filled
        )
        val defaultImages = arrayOf(
            R.drawable.ic_home,
            R.drawable.ic_shopping_cart,
            R.drawable.ic_user,
            R.drawable.ic_sharp_history
        )

        for (i in buttons.indices) {
            buttons[i].setOnClickListener {
                for (j in buttons.indices) {
                    buttons[j].setImageResource(defaultImages[j])
                }
                buttons[i].setImageResource(filledImages[i])
            }
        }
    }

    companion object {
        private const val SIGN_IN_KEY = "sign_in"
        fun newInstance(context: Context, token: String = ""): Intent {
            return Intent(context, MainActivity::class.java).apply {
                putExtra(SIGN_IN_KEY, token)
            }
        }
    }
}