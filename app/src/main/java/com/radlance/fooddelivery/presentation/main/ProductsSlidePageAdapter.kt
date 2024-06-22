package com.radlance.fooddelivery.presentation.main

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class ProductsSlidePageAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int = 4

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> FragmentProductList.foodsInstance()
            1 -> FragmentProductList.drinksInstance()
            2 -> FragmentProductList.snacksInstance()
            3 -> FragmentProductList.sauceInstance()
            else -> throw IllegalStateException("Invalid position")
        }
    }
}