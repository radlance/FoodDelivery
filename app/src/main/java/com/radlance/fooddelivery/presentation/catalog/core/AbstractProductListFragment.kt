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
import com.squareup.picasso.Picasso

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

        productListAdapter.onProductItemClickListener = { product ->
            viewModel.showDetails(product)
        }

        binding.ivBack.setOnClickListener {
            viewModel.closeDetails()
        }

        view.setOnClickListener {
            viewModel.closeDetails()
        }

        viewModel.openedProductDetails.observe(viewLifecycleOwner) { product ->
            binding.cardViewDetails.visibility = View.VISIBLE
            binding.tvTitle.text = product.title
            binding.tvPrice.text = product.price.toString()
            Picasso.get().load(product.imageUrl).into(binding.ivProduct)
            binding.rvProductList.visibility = View.GONE

            binding.tvMore.setOnClickListener {
                val intent = DetailActivity.productInstance(requireActivity(), product)
                startActivity(intent)
            }
        }

        viewModel.shouldCloseDetails.observe(viewLifecycleOwner) {
            if (it) {
                binding.cardViewDetails.visibility = View.GONE
                binding.rvProductList.visibility = View.VISIBLE
            }
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
            getProductList()
        }

        binding.buttonMinus.setOnClickListener {
            viewModel.decrementCount()
        }

        binding.buttonPlus.setOnClickListener {
            viewModel.incrementCount()
        }

        viewModel.productCount.observe(viewLifecycleOwner) {
            binding.tvCount.text = it.toString()
        }
    }

    abstract fun getProductList()
}