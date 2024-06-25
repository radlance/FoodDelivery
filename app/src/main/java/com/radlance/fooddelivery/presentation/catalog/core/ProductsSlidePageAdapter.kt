package com.radlance.fooddelivery.presentation.catalog.core

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.radlance.fooddelivery.presentation.catalog.tabs.BurgersCategoryFragment
import com.radlance.fooddelivery.presentation.catalog.tabs.ChickenCategoryFragment
import com.radlance.fooddelivery.presentation.catalog.tabs.DessertsCategoryFragment
import com.radlance.fooddelivery.presentation.catalog.tabs.DrinksCategoryFragment
import com.radlance.fooddelivery.presentation.catalog.tabs.FoodCategoryFragment
import com.radlance.fooddelivery.presentation.catalog.tabs.PizzaCategoryFragment
import com.radlance.fooddelivery.presentation.catalog.tabs.PotatoCategoryFragment

class ProductsSlidePageAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int = 7

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> FoodCategoryFragment.newInstance()
            1 -> BurgersCategoryFragment.newInstance()
            2 -> DrinksCategoryFragment.newInstance()
            3 -> PizzaCategoryFragment.newInstance()
            4 -> ChickenCategoryFragment.newInstance()
            5 -> PotatoCategoryFragment.newInstance()
            6 -> DessertsCategoryFragment.newInstance()

            else -> throw IllegalStateException("Invalid position")
        }
    }
}