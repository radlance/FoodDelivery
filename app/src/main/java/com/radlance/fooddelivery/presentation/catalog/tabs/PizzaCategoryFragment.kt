package com.radlance.fooddelivery.presentation.catalog.tabs

import com.radlance.fooddelivery.R
import com.radlance.fooddelivery.presentation.catalog.core.AbstractProductListFragment

class PizzaCategoryFragment : AbstractProductListFragment() {
    override fun getProductList() {
        return viewModel.getProductsByCategory(getString(R.string.pizza))
    }

    companion object {
        fun newInstance(): PizzaCategoryFragment {
            return PizzaCategoryFragment()
        }
    }
}