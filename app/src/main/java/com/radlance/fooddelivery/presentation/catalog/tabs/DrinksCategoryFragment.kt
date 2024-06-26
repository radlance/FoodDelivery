package com.radlance.fooddelivery.presentation.catalog.tabs

import com.radlance.fooddelivery.R
import com.radlance.fooddelivery.presentation.catalog.core.AbstractProductListFragment
import com.radlance.fooddelivery.presentation.catalog.core.SearchQueryListener

class DrinksCategoryFragment : AbstractProductListFragment(), SearchQueryListener {
    override fun getProductList() {
        viewModel.getProductsByCategory(getString(R.string.drinks))
    }

    companion object {
        fun newInstance(): DrinksCategoryFragment {
            return DrinksCategoryFragment()
        }
    }

    override fun onSearchQueryChanged(query: String) {

    }
}