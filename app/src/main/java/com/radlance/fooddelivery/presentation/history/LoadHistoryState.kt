package com.radlance.fooddelivery.presentation.history

import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.radlance.fooddelivery.domain.entity.HistoryItem

interface LoadHistoryState {
    fun show(
        adapter: HistoryRecyclerAdapter,
        constraintLayout: ConstraintLayout,
        recyclerView: RecyclerView
    )

    data class Success(private val history: List<HistoryItem>) : LoadHistoryState {
        override fun show(
            adapter: HistoryRecyclerAdapter,
            constraintLayout: ConstraintLayout,
            recyclerView: RecyclerView
        ) {
            adapter.history = history
            constraintLayout.visibility = View.GONE
            recyclerView.visibility = View.VISIBLE
        }
    }

    class Error(private val unauthorized: Boolean) : LoadHistoryState {
        override fun show(
            adapter: HistoryRecyclerAdapter,
            constraintLayout: ConstraintLayout,
            recyclerView: RecyclerView
        ) {
            adapter.history = emptyList()
            constraintLayout.visibility = View.VISIBLE
            recyclerView.visibility = View.INVISIBLE
        }
    }
}