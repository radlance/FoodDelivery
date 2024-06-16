package com.radlance.fooddelivery.presentation.main

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class ProductsSlidePageAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int = 1

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> FragmentProductList.newInstance()
            else -> throw IllegalStateException("Invalid position")
        }
    }
}