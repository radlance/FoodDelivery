package com.radlance.fooddelivery.presentation.catalog.core

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.radlance.fooddelivery.databinding.FragmentProductListBinding
import com.radlance.fooddelivery.domain.core.LoadResult
import com.radlance.fooddelivery.presentation.core.AbstractFragment

abstract class AbstractProductListFragment : AbstractFragment<FragmentProductListBinding>() {
    private lateinit var productListAdapter: ProductListRecyclerAdapter
    protected val viewModel: ProductListViewModel by lazy {
        ViewModelProvider(
            this,
            ProductListViewModelFactory()
        )[ProductListViewModel::class.java]
    }

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

        getProductList()

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

        viewModel.localProducts.observe(viewLifecycleOwner) { productList ->
            productListAdapter.productList = productList
            binding.progressLoading.visibility = View.GONE
        }

        binding.buttonRetry.setOnClickListener {
            viewModel.getProductList()
        }
    }

    abstract fun getProductList()
}