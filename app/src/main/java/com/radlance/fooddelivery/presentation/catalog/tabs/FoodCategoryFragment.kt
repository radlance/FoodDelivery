package com.radlance.fooddelivery.presentation.catalog.tabs

import com.radlance.fooddelivery.presentation.catalog.core.AbstractProductListFragment
import com.radlance.fooddelivery.presentation.catalog.core.SearchQueryListener

class FoodCategoryFragment : AbstractProductListFragment(), SearchQueryListener {

    override fun getProductList() {
        viewModel.getProducts()
    }

    override fun onSearchQueryChanged(query: String) {
        viewModel.searchProductsLikeName(query)
    }

    companion object {
        fun newInstance(): FoodCategoryFragment {
            return FoodCategoryFragment()
        }
    }
}