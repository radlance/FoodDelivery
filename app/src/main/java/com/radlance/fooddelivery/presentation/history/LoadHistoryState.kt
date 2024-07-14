package com.radlance.fooddelivery.presentation.history

import android.content.Context
import android.widget.Toast
import com.radlance.fooddelivery.domain.entity.HistoryItem

interface LoadHistoryState {
    fun show(context: Context)
    class Success(history: List<HistoryItem>) : LoadHistoryState {
        override fun show(context: Context) {
            Toast.makeText(
                context,
                "Success",
                Toast.LENGTH_LONG
            ).show()
        }

    }

    class Error(private val unauthorized: Boolean) : LoadHistoryState {
        override fun show(context: Context) {
            if (unauthorized) {
                Toast.makeText(
                    context,
                    "User should be authorize",
                    Toast.LENGTH_LONG
                ).show()
            } else {
                Toast.makeText(
                    context,
                    "Failure",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }
}