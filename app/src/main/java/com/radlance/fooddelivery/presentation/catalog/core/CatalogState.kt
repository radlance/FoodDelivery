package com.radlance.fooddelivery.presentation.catalog.core

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout

interface CatalogState {
    fun show(
        textView: TextView,
        tabLayout: TabLayout,
        recyclerView: RecyclerView,
        viewPager: ViewPager2
    )

    object Show : CatalogState {
        override fun show(
            textView: TextView,
            tabLayout: TabLayout,
            recyclerView: RecyclerView,
            viewPager: ViewPager2
        ) {
            textView.visibility = View.VISIBLE
            tabLayout.visibility = View.GONE
            recyclerView.visibility = View.VISIBLE
            viewPager.visibility = View.GONE
        }

    }

    object Hide : CatalogState {
        override fun show(
            textView: TextView,
            tabLayout: TabLayout,
            recyclerView: RecyclerView,
            viewPager: ViewPager2
        ) {
            textView.visibility = View.GONE
            tabLayout.visibility = View.VISIBLE
            recyclerView.visibility = View.GONE
            viewPager.visibility = View.VISIBLE
        }

    }
}