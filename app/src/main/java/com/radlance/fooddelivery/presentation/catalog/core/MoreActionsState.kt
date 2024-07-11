package com.radlance.fooddelivery.presentation.catalog.core

import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView

interface MoreActionsState {
    fun more(
        cartButton: Button,
        continueButton: Button,
        plusButton: ImageView,
        minusButton: ImageView,
        addTextView: TextView,
        cartTextView: TextView,
        continueTextView: TextView,
        countTextView: TextView,
        cartImageView: ImageView,
    )

    object More : MoreActionsState {
        override fun more(
            cartButton: Button,
            continueButton: Button,
            plusButton: ImageView,
            minusButton: ImageView,
            addTextView: TextView,
            cartTextView: TextView,
            continueTextView: TextView,
            countTextView: TextView,
            cartImageView: ImageView
        ) {
            cartButton.visibility = VISIBLE
            continueButton.visibility = VISIBLE
            plusButton.visibility = INVISIBLE
            minusButton.visibility = INVISIBLE
            addTextView.visibility = INVISIBLE
            continueButton.visibility = VISIBLE
            countTextView.visibility = INVISIBLE
            cartImageView.visibility = INVISIBLE
            cartTextView.visibility = VISIBLE
        }

    }

    object Less : MoreActionsState {
        override fun more(
            cartButton: Button,
            continueButton: Button,
            plusButton: ImageView,
            minusButton: ImageView,
            addTextView: TextView,
            cartTextView: TextView,
            continueTextView: TextView,
            countTextView: TextView,
            cartImageView: ImageView
        ) {
            cartButton.visibility = INVISIBLE
            continueButton.visibility = INVISIBLE
            plusButton.visibility = VISIBLE
            minusButton.visibility = VISIBLE
            addTextView.visibility = VISIBLE
            continueButton.visibility = INVISIBLE
            countTextView.visibility = VISIBLE
            cartImageView.visibility = VISIBLE
            cartTextView.visibility = INVISIBLE
        }

    }
}