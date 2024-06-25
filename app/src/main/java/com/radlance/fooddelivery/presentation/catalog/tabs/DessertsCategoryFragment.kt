package com.radlance.fooddelivery.presentation.catalog.tabs

import com.radlance.fooddelivery.R
import com.radlance.fooddelivery.presentation.catalog.core.AbstractProductListFragment

class DessertsCategoryFragment : AbstractProductListFragment() {
    override fun getProductList() {
        viewModel.getProductsByCategory(getString(R.string.desserts))
    }

    companion object {
        fun newInstance(): DessertsCategoryFragment {
            return DessertsCategoryFragment()
        }
    }
}