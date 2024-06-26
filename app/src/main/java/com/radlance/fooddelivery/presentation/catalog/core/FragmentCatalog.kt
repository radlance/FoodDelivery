package com.radlance.fooddelivery.presentation.catalog.core

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import com.google.android.material.tabs.TabLayoutMediator
import com.radlance.fooddelivery.databinding.FragmentCatalogBinding
import com.radlance.fooddelivery.presentation.core.AbstractFragment

class FragmentCatalog : AbstractFragment<FragmentCatalogBinding>() {
    private val tabTitles = listOf(
        "Foods", "Burgers", "Drinks", "Pizza", "Chicken", "Potato", "Desserts"
    )

    private var searchQueryListener: SearchQueryListener? = null

    fun setSearchQueryListener(listener: SearchQueryListener) {
        searchQueryListener = listener
    }
    
    override fun bind(inflater: LayoutInflater, container: ViewGroup?): FragmentCatalogBinding {
        return FragmentCatalogBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.pagerProducts.adapter = ProductsSlidePageAdapter(this)
        TabLayoutMediator(binding.tabLayout, binding.pagerProducts) { tab, position ->
            tab.text = tabTitles[position]
        }.attach()

        binding.searchMain.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                searchQueryListener?.onSearchQueryChanged(query ?: "")
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                searchQueryListener?.onSearchQueryChanged(newText ?: "")
                return true
            }
        })
    }

    companion object {
        fun newInstance(): FragmentCatalog {
            return FragmentCatalog()
        }
    }
}