package com.radlance.fooddelivery.presentation.catalog.core

import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import com.radlance.fooddelivery.domain.entity.Product

interface LoadState {
    fun show(
        viewModel: ProductListViewModel,
        adapter: ProductListRecyclerAdapter,
        noConnectionTextView: TextView,
        progressBar: ProgressBar,
        retryButton: Button
    )

    class Success(private val list: List<Product> = emptyList()) : LoadState {
        override fun show(
            viewModel: ProductListViewModel,
            adapter: ProductListRecyclerAdapter,
            noConnectionTextView: TextView,
            progressBar: ProgressBar,
            retryButton: Button
        ) {
            if (list.isNotEmpty()) {
                viewModel.saveProducts(list)
            }
            adapter.productList = list
            noConnectionTextView.visibility = View.GONE
            progressBar.visibility = View.GONE
            retryButton.visibility = View.INVISIBLE
        }

    }

    object Error : LoadState {
        override fun show(
            viewModel: ProductListViewModel,
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