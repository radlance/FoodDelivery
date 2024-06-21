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
    private lateinit var productListAdapter: ProductListAdapter
    override fun bind(inflater: LayoutInflater, container: ViewGroup?): FragmentProductListBinding {
        return FragmentProductListBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.rvProductList.apply {
            productListAdapter = ProductListAdapter()
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
        fun newInstance(): FragmentProductList {
            return FragmentProductList()
        }
    }
}