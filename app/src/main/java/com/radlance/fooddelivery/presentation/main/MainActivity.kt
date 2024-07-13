package com.radlance.fooddelivery.presentation.main

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.radlance.fooddelivery.databinding.ActivityMainBinding
import com.radlance.fooddelivery.presentation.catalog.core.FragmentCatalog
import com.radlance.fooddelivery.presentation.order.OrderFragment

class MainActivity : AppCompatActivity(), FragmentReplaceListener {
    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by lazy {
        ViewModelProvider(this)[MainViewModel::class.java]
    }
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

        viewModel.navigationState.observe(this) {
            it.update(
                binding.buttonHomePage,
                binding.buttonShoppingCart,
                binding.buttonUser,
                binding.buttonHistory
            )
        }

    }
    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(binding.containerMain.id, fragment)
            .commit()
    }

    override fun catalogReplace() {
        viewModel.updateNavigationState(NavigationState.HomeSelected)
        replaceFragment(FragmentCatalog.newInstance())
    }

    override fun orderReplace() {
        viewModel.updateNavigationState(NavigationState.CartSelected)
        replaceFragment(OrderFragment.newInstance())
    }

    override fun userReplace() {
        viewModel.updateNavigationState(NavigationState.UserSelected)

    }

    override fun historyReplace() {
        viewModel.updateNavigationState(NavigationState.HistorySelected)
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