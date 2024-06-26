package com.radlance.fooddelivery.presentation.catalog.tabs

import com.radlance.fooddelivery.R
import com.radlance.fooddelivery.presentation.catalog.core.AbstractProductListFragment
import com.radlance.fooddelivery.presentation.catalog.core.SearchQueryListener

class PotatoCategoryFragment : AbstractProductListFragment(), SearchQueryListener {
    override fun getProductList() {
        viewModel.getProductsByCategory(getString(R.string.potato))
    }

    companion object {
        fun newInstance(): PotatoCategoryFragment {
            return PotatoCategoryFragment()
        }
    }

    override fun onSearchQueryChanged(query: String) {

    }
}