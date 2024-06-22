package com.radlance.fooddelivery.presentation.main

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class ProductsSlidePageAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int = 7

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> FragmentProductList.foodsInstance()
            1 -> FragmentProductList.burgersInstance()
            2 -> FragmentProductList.drinksInstance()
            3 -> FragmentProductList.pizzaInstance()
            4 -> FragmentProductList.chickenInstance()
            5 -> FragmentProductList.potatoInstance()
            6 -> FragmentProductList.dessertsInstance()

            else -> throw IllegalStateException("Invalid position")
        }
    }
}