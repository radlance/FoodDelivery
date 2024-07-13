package com.radlance.fooddelivery.presentation.main

import android.widget.ImageView
import com.radlance.fooddelivery.R

interface NavigationState {
    fun update(
        homeButton: ImageView,
        cartButton: ImageView,
        userButton: ImageView,
        historyButton: ImageView
    )

    object HomeSelected : NavigationState {
        override fun update(
            homeButton: ImageView,
            cartButton: ImageView,
            userButton: ImageView,
            historyButton: ImageView
        ) {
            homeButton.setImageResource(R.drawable.ic_home_filled)
            cartButton.setImageResource(R.drawable.ic_shopping_cart)
            userButton.setImageResource(R.drawable.ic_user)
            historyButton.setImageResource(R.drawable.ic_sharp_history)
        }

    }

    object CartSelected : NavigationState {
        override fun update(
            homeButton: ImageView,
            cartButton: ImageView,
            userButton: ImageView,
            historyButton: ImageView
        ) {
            homeButton.setImageResource(R.drawable.ic_home)
            cartButton.setImageResource(R.drawable.ic_shopping_cart_filled)
            userButton.setImageResource(R.drawable.ic_user)
            historyButton.setImageResource(R.drawable.ic_sharp_history)
        }

    }

    object UserSelected : NavigationState {
        override fun update(
            homeButton: ImageView,
            cartButton: ImageView,
            userButton: ImageView,
            historyButton: ImageView
        ) {
            homeButton.setImageResource(R.drawable.ic_home)
            cartButton.setImageResource(R.drawable.ic_shopping_cart)
            userButton.setImageResource(R.drawable.ic_user_filled)
            historyButton.setImageResource(R.drawable.ic_sharp_history)
        }

    }

    object HistorySelected : NavigationState {
        override fun update(
            homeButton: ImageView,
            cartButton: ImageView,
            userButton: ImageView,
            historyButton: ImageView
        ) {
            homeButton.setImageResource(R.drawable.ic_home)
            cartButton.setImageResource(R.drawable.ic_shopping_cart)
            userButton.setImageResource(R.drawable.ic_user)
            historyButton.setImageResource(R.drawable.ic_sharp_history_filled)
        }

    }
}