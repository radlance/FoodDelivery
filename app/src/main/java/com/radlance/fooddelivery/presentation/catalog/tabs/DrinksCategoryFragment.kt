package com.radlance.fooddelivery.presentation.catalog.tabs

import com.radlance.fooddelivery.R
import com.radlance.fooddelivery.presentation.catalog.core.AbstractProductListFragment

class DrinksCategoryFragment : AbstractProductListFragment() {
    override fun getProductList() {
        viewModel.getProductsByCategory(getString(R.string.drinks))
    }

    companion object {
        fun newInstance(): DrinksCategoryFragment {
            return DrinksCategoryFragment()
        }
    }
}