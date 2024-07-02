package com.radlance.fooddelivery.presentation.main

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.radlance.fooddelivery.R
import com.radlance.fooddelivery.databinding.ActivityMainBinding
import com.radlance.fooddelivery.presentation.catalog.core.FragmentCatalog
import com.radlance.fooddelivery.presentation.order.OrderFragment

class MainActivity : AppCompatActivity(), FragmentReplaceListener {
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
        binding.buttonShoppingCart.setOnClickListener {
            orderReplace()
        }

        binding.buttonHomePage.setOnClickListener {
            catalogReplace()
        }

        binding.buttonUser.setOnClickListener {
            userReplace()
        }

        binding.buttonHistory.setOnClickListener {
            historyReplace()
        }

    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(binding.containerMain.id, fragment)
            .commit()
    }

    override fun catalogReplace() {
        with(binding) {
            buttonHomePage.setImageResource(R.drawable.ic_home_filled)
            buttonShoppingCart.setImageResource(R.drawable.ic_shopping_cart)
            buttonUser.setImageResource(R.drawable.ic_user)
            buttonHistory.setImageResource(R.drawable.ic_sharp_history)
        }
        replaceFragment(FragmentCatalog.newInstance())
    }

    override fun orderReplace() {
        with(binding) {
            buttonHomePage.setImageResource(R.drawable.ic_home)
            buttonShoppingCart.setImageResource(R.drawable.ic_shopping_cart_filled)
            buttonUser.setImageResource(R.drawable.ic_user)
            buttonHistory.setImageResource(R.drawable.ic_sharp_history)
        }
        replaceFragment(OrderFragment.newInstance())
    }

    override fun userReplace() {
        with(binding) {
            buttonHomePage.setImageResource(R.drawable.ic_home)
            buttonShoppingCart.setImageResource(R.drawable.ic_shopping_cart)
            buttonUser.setImageResource(R.drawable.ic_user_filled)
            buttonHistory.setImageResource(R.drawable.ic_sharp_history)
        }
    }

    override fun historyReplace() {
        with(binding) {
            buttonHomePage.setImageResource(R.drawable.ic_home)
            buttonShoppingCart.setImageResource(R.drawable.ic_shopping_cart)
            buttonUser.setImageResource(R.drawable.ic_user)
            buttonHistory.setImageResource(R.drawable.ic_sharp_history_filled)
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