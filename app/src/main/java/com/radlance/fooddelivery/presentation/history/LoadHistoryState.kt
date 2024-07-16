package com.radlance.fooddelivery.presentation.history

import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.radlance.fooddelivery.R
import com.radlance.fooddelivery.domain.entity.HistoryItem

interface LoadHistoryState {
    fun show(
        adapter: HistoryRecyclerAdapter,
        constraintLayout: ConstraintLayout,
        recyclerView: RecyclerView,
        progressBar: ProgressBar,
        buttonRetry: Button,
        buttonExit: Button,
        errorTextView: TextView,
        fragment: Fragment
    )

    data class Success(private val history: List<HistoryItem>) : LoadHistoryState {
        override fun show(
            adapter: HistoryRecyclerAdapter,
            constraintLayout: ConstraintLayout,
            recyclerView: RecyclerView,
            progressBar: ProgressBar,
            buttonRetry: Button,
            buttonExit: Button,
            errorTextView: TextView,
            fragment: Fragment
        ) {
            if (history.isEmpty()) {
                constraintLayout.visibility = View.VISIBLE
                recyclerView.visibility = View.INVISIBLE
                progressBar.visibility = View.GONE
            } else {
                adapter.history = history
                constraintLayout.visibility = View.GONE
                recyclerView.visibility = View.VISIBLE
                progressBar.visibility = View.GONE
            }
        }
    }

    object Loading : LoadHistoryState {
        override fun show(
            adapter: HistoryRecyclerAdapter,
            constraintLayout: ConstraintLayout,
            recyclerView: RecyclerView,
            progressBar: ProgressBar,
            buttonRetry: Button,
            buttonExit: Button,
            errorTextView: TextView,
            fragment: Fragment
        ) {
            progressBar.visibility = View.VISIBLE
            buttonExit.visibility = View.INVISIBLE
            buttonRetry.visibility = View.INVISIBLE
            errorTextView.visibility = View.INVISIBLE
        }
    }
    class Error(private val unauthorized: Boolean) : LoadHistoryState {
        override fun show(
            adapter: HistoryRecyclerAdapter,
            constraintLayout: ConstraintLayout,
            recyclerView: RecyclerView,
            progressBar: ProgressBar,
            buttonRetry: Button,
            buttonExit: Button,
            errorTextView: TextView,
            fragment: Fragment
        ) {
            errorTextView.visibility = View.VISIBLE
            progressBar.visibility = View.GONE
            if (unauthorized) {
                buttonExit.visibility = View.VISIBLE
                errorTextView.text = fragment.getString(R.string.authorized_error)
            } else {
                buttonRetry.visibility = View.VISIBLE
                errorTextView.text = fragment.getString(R.string.no_connection)
            }
        }
    }
}