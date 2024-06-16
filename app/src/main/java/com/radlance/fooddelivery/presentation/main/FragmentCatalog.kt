package com.radlance.fooddelivery.presentation.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.tabs.TabLayoutMediator
import com.radlance.fooddelivery.databinding.FragmentCatalogBinding
import com.radlance.fooddelivery.presentation.core.AbstractFragment

class FragmentCatalog : AbstractFragment<FragmentCatalogBinding>() {
    override fun bind(inflater: LayoutInflater, container: ViewGroup?): FragmentCatalogBinding {
        return FragmentCatalogBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.pagerProducts.adapter = ProductsSlidePageAdapter(this)
        TabLayoutMediator(binding.tabLayout, binding.pagerProducts) { tab, _ ->
            tab.text = "Foods"
        }.attach()
    }

    companion object {
        fun newInstance(): FragmentCatalog {
            return FragmentCatalog()
        }
    }
}