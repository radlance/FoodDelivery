package com.radlance.fooddelivery.presentation.catalog.tabs

import com.radlance.fooddelivery.R
import com.radlance.fooddelivery.presentation.catalog.core.AbstractProductListFragment

class ChickenCategoryFragment : AbstractProductListFragment() {
    override fun getProductList() {
        return viewModel.getProductsByCategory(getString(R.string.chicken))
    }

    companion object {
        fun newInstance(): ChickenCategoryFragment {
            return ChickenCategoryFragment()
        }
    }
}