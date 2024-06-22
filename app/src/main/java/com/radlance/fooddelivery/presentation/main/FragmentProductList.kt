package com.radlance.fooddelivery.presentation.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.radlance.fooddelivery.databinding.FragmentProductListBinding
import com.radlance.fooddelivery.domain.core.LoadResult
import com.radlance.fooddelivery.presentation.core.AbstractFragment

class FragmentProductList : AbstractFragment<FragmentProductListBinding>() {
    private val token = DEFAULT_TOKEN
    private val viewModel: ProductListViewModel by lazy {
        ViewModelProvider(
            this,
            ProductListViewModelFactory(token)
        )[ProductListViewModel::class.java]
    }
    private lateinit var productListAdapter: ProductListRecyclerAdapter
    override fun bind(inflater: LayoutInflater, container: ViewGroup?): FragmentProductListBinding {
        return FragmentProductListBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.rvProductList.apply {
            productListAdapter = ProductListRecyclerAdapter()
            layoutManager = GridLayoutManager(requireActivity(), 2)
            adapter = productListAdapter
        }

        binding.progressLoading.visibility = View.VISIBLE

        viewModel.getProductList()
        viewModel.loadState.observe(viewLifecycleOwner) { loadResult ->
            when (loadResult) {
                is LoadResult.Success -> {
                    productListAdapter.productList = loadResult.productList
                    viewModel.saveProducts(loadResult.productList)

                    with(binding) {
                        tvNoConnection.visibility = View.GONE
                        progressLoading.visibility = View.GONE
                        buttonRetry.visibility = View.INVISIBLE
                    }
                }

                is LoadResult.Error -> {
                    with(binding) {
                        tvNoConnection.visibility = View.VISIBLE
                        progressLoading.visibility = View.GONE
                        buttonRetry.visibility = View.VISIBLE
                    }
                }
            }
        }

        binding.buttonRetry.setOnClickListener {
            viewModel.getProductList()
        }

        viewModel.localProducts.observe(viewLifecycleOwner) { productList ->
            productListAdapter.productList = productList
            binding.progressLoading.visibility = View.GONE
        }
    }

    companion object {
        private const val DEFAULT_TOKEN = ""
        private const val CATEGORY_KEY = "category"
        private const val CATEGORY_FOODS = "foods"
        private const val CATEGORY_DRINKS = "drinks"
        private const val CATEGORY_SNACKS = "snacks"
        private const val CATEGORY_SAUCE = "sauce"

        fun foodsInstance(): FragmentProductList {
            return FragmentProductList().apply {
                arguments = Bundle().apply {
                    putString(CATEGORY_KEY, CATEGORY_FOODS)
                }
            }
        }

        fun drinksInstance(): FragmentProductList {
            return FragmentProductList().apply {
                arguments = Bundle().apply {
                    putString(CATEGORY_KEY, CATEGORY_DRINKS)
                }
            }
        }

        fun snacksInstance(): FragmentProductList {
            return FragmentProductList().apply {
                arguments = Bundle().apply {
                    putString(CATEGORY_KEY, CATEGORY_SNACKS)
                }
            }
        }

        fun sauceInstance(): FragmentProductList {
            return FragmentProductList().apply {
                arguments = Bundle().apply {
                    putString(CATEGORY_KEY, CATEGORY_SAUCE)
                }
            }
        }
    }
}