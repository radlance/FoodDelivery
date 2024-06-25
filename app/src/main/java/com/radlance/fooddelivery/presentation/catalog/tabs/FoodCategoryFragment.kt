package com.radlance.fooddelivery.presentation.catalog.tabs

import com.radlance.fooddelivery.presentation.catalog.core.AbstractProductListFragment

class FoodCategoryFragment : AbstractProductListFragment() {

    override fun getProductList() {
        viewModel.getProductList()
    }

    companion object {
        fun newInstance(): FoodCategoryFragment {
            return FoodCategoryFragment()
        }
    }
}