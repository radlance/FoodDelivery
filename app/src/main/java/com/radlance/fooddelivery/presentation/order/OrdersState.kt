package com.radlance.fooddelivery.presentation.order

import android.view.View
import android.widget.LinearLayout
import androidx.appcompat.widget.AppCompatButton
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.radlance.fooddelivery.domain.entity.CartItem

interface OrdersState {
    fun show(
        constraintLayout: ConstraintLayout,
        recyclerView: RecyclerView,
        priceView: View,
        linearLayout: LinearLayout,
        button: AppCompatButton,
        adapter: OrderListRecyclerAdapter
    )

    object Empty : OrdersState {
        override fun show(
            constraintLayout: ConstraintLayout,
            recyclerView: RecyclerView,
            priceView: View,
            linearLayout: LinearLayout,
            button: AppCompatButton,
            adapter: OrderListRecyclerAdapter
        ) {
            adapter.orderList = emptyList()
            recyclerView.visibility = View.GONE
            constraintLayout.visibility = View.VISIBLE
            priceView.visibility = View.INVISIBLE
            linearLayout.visibility = View.INVISIBLE
            button.visibility = View.INVISIBLE
        }

    }

    class Loaded(private val orderList: List<CartItem>) : OrdersState {
        override fun show(
            constraintLayout: ConstraintLayout,
            recyclerView: RecyclerView,
            priceView: View,
            linearLayout: LinearLayout,
            button: AppCompatButton,
            adapter: OrderListRecyclerAdapter
        ) {
            constraintLayout.visibility = View.INVISIBLE
            recyclerView.visibility = View.VISIBLE
            priceView.visibility = View.VISIBLE
            linearLayout.visibility = View.VISIBLE
            button.visibility = View.VISIBLE
            adapter.orderList = orderList
        }
    }
}