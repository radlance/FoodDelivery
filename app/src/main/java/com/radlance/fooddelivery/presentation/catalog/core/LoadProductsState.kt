package com.radlance.fooddelivery.presentation.catalog.core

import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import com.radlance.fooddelivery.domain.entity.Product

interface LoadProductsState {
    fun show(
        adapter: ProductListRecyclerAdapter,
        noConnectionTextView: TextView,
        progressBar: ProgressBar,
        retryButton: Button
    )

    class Success(private val list: List<Product> = emptyList()) : LoadProductsState {
        override fun show(
            adapter: ProductListRecyclerAdapter,
            noConnectionTextView: TextView,
            progressBar: ProgressBar,
            retryButton: Button
        ) {
            adapter.productList = list
            noConnectionTextView.visibility = View.GONE
            progressBar.visibility = View.GONE
            retryButton.visibility = View.INVISIBLE
        }

    }

    object Error : LoadProductsState {
        override fun show(
            adapter: ProductListRecyclerAdapter,
            noConnectionTextView: TextView,
            progressBar: ProgressBar,
            retryButton: Button
        ) {
            noConnectionTextView.visibility = View.VISIBLE
            progressBar.visibility = View.GONE
            retryButton.visibility = View.VISIBLE
        }
    }
}