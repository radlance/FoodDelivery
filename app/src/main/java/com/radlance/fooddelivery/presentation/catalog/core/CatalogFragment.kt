package com.radlance.fooddelivery.presentation.catalog.core

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.tabs.TabLayoutMediator
import com.radlance.fooddelivery.databinding.FragmentCatalogBinding
import com.radlance.fooddelivery.presentation.core.AbstractFragment

class CatalogFragment : AbstractFragment<FragmentCatalogBinding>() {
    private lateinit var productListAdapter: ProductListRecyclerAdapter
    private val viewModel: CatalogViewModel by lazy {
        ViewModelProvider(this, CatalogViewModelFactory())[CatalogViewModel::class.java]
    }
    private val tabTitles = listOf(
        "Foods", "Burgers", "Drinks", "Pizza", "Chicken", "Potato", "Desserts"
    )
    
    override fun bind(inflater: LayoutInflater, container: ViewGroup?): FragmentCatalogBinding {
        return FragmentCatalogBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.pagerProducts.adapter = ProductsSlidePageAdapter(this)
        TabLayoutMediator(binding.tabLayout, binding.pagerProducts) { tab, position ->
            tab.text = tabTitles[position]
        }.attach()

        binding.rvResult.apply {
            productListAdapter = ProductListRecyclerAdapter()
            layoutManager = GridLayoutManager(requireActivity(), 2)
            adapter = productListAdapter
        }

        binding.searchMain.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                viewModel.searchProductsLikeName(query ?: "")
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                viewModel.searchProductsLikeName(newText ?: "")
                return true
            }
        })

        viewModel.searchResult.observe(viewLifecycleOwner) {
            productListAdapter.productList = it
        }

        viewModel.shouldCloseSearch.observe(viewLifecycleOwner) {
            it.show(binding.tvResult, binding.tabLayout, binding.rvResult, binding.pagerProducts)
        }
    }

    companion object {
        fun newInstance(): CatalogFragment {
            return CatalogFragment()
        }
    }
}