package com.radlance.fooddelivery.presentation.catalog.tabs

import com.radlance.fooddelivery.R
import com.radlance.fooddelivery.presentation.catalog.core.AbstractProductListFragment
import com.radlance.fooddelivery.presentation.catalog.core.SearchQueryListener

class PizzaCategoryFragment : AbstractProductListFragment(), SearchQueryListener {
    override fun getProductList() {
        return viewModel.getProductsByCategory(getString(R.string.pizza))
    }

    companion object {
        fun newInstance(): PizzaCategoryFragment {
            return PizzaCategoryFragment()
        }
    }

    override fun onSearchQueryChanged(query: String) {

    }
}