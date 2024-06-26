package com.radlance.fooddelivery.presentation.catalog.tabs

import com.radlance.fooddelivery.R
import com.radlance.fooddelivery.presentation.catalog.core.AbstractProductListFragment
import com.radlance.fooddelivery.presentation.catalog.core.SearchQueryListener

class DessertsCategoryFragment : AbstractProductListFragment(), SearchQueryListener {
    override fun getProductList() {
        viewModel.getProductsByCategory(getString(R.string.desserts))
    }

    companion object {
        fun newInstance(): DessertsCategoryFragment {
            return DessertsCategoryFragment()
        }
    }

    override fun onSearchQueryChanged(query: String) {

    }
}