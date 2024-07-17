package com.radlance.fooddelivery.presentation.order

import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.radlance.fooddelivery.R

interface DeliveryState {
    fun show(
        fragment: Fragment,
        progressBar: ProgressBar,
        button: Button,
        textView: TextView,
        viewModel: OrderViewModel
    )

    object Success : DeliveryState {
        override fun show(
            fragment: Fragment,
            progressBar: ProgressBar,
            button: Button,
            textView: TextView,
            viewModel: OrderViewModel
        ) {
            Toast.makeText(
                fragment.requireContext(),
                fragment.getString(R.string.successs),
                Toast.LENGTH_SHORT
            ).show()
            progressBar.visibility = View.GONE
            textView.visibility = View.VISIBLE
            button.isEnabled = true

            viewModel.clearCart()
        }
    }

    object Loading : DeliveryState {
        override fun show(
            fragment: Fragment,
            progressBar: ProgressBar,
            button: Button,
            textView: TextView,
            viewModel: OrderViewModel
        ) {
            progressBar.visibility = View.VISIBLE
            textView.visibility = View.GONE
            button.isEnabled = false
        }
    }

    class Error(private val unauthorized: Boolean) : DeliveryState {
        override fun show(
            fragment: Fragment,
            progressBar: ProgressBar,
            button: Button,
            textView: TextView,
            viewModel: OrderViewModel
        ) {
            val errorMessage = if (unauthorized) {
                fragment.getString(R.string.authorized_error)
            } else {
                fragment.getString(R.string.error)
            }
            Toast.makeText(
                fragment.requireContext(),
                errorMessage,
                Toast.LENGTH_SHORT
            ).show()
            progressBar.visibility = View.GONE
            textView.visibility = View.VISIBLE
            button.isEnabled = true

        }
    }
}