package com.radlance.fooddelivery.presentation.catalog.tabs

import com.radlance.fooddelivery.R
import com.radlance.fooddelivery.presentation.catalog.core.AbstractProductListFragment
import com.radlance.fooddelivery.presentation.catalog.core.SearchQueryListener

class BurgersCategoryFragment : AbstractProductListFragment(), SearchQueryListener {
    override fun getProductList() {
        viewModel.getProductsByCategory(getString(R.string.burgers))
    }

    companion object {
        fun newInstance(): BurgersCategoryFragment {
            return BurgersCategoryFragment()
        }
    }

    override fun onSearchQueryChanged(query: String) {

    }
}