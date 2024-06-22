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
    private var category = DEFAULT_CATEGORY
    private val viewModel: ProductListViewModel by lazy {
        ViewModelProvider(
            this,
            ProductListViewModelFactory()
        )[ProductListViewModel::class.java]
    }
    private lateinit var productListAdapter: ProductListRecyclerAdapter
    override fun bind(inflater: LayoutInflater, container: ViewGroup?): FragmentProductListBinding {
        return FragmentProductListBinding.inflate(inflater, container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        category = requireArguments().getString(CATEGORY_KEY)!!
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.rvProductList.apply {
            productListAdapter = ProductListRecyclerAdapter()
            layoutManager = GridLayoutManager(requireActivity(), 2)
            adapter = productListAdapter
        }

        binding.progressLoading.visibility = View.VISIBLE

        if (category == CATEGORY_FOODS) {
            viewModel.getProductList()
        }
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
        private const val DEFAULT_CATEGORY = "unknown category"

        private const val CATEGORY_KEY = "category"
        private const val CATEGORY_FOODS = "foods"
        private const val CATEGORY_BURGERS = "burgers"
        private const val CATEGORY_DRINKS = "drinks"
        private const val CATEGORY_PIZZA = "pizza"
        private const val CATEGORY_CHICKEN = "chicken"
        private const val CATEGORY_POTATO = "potato"
        private const val CATEGORY_DESSERTS = "desserts"

        fun foodsInstance(): FragmentProductList {
            return FragmentProductList().apply {
                arguments = Bundle().apply {
                    putString(CATEGORY_KEY, CATEGORY_FOODS)
                }
            }
        }

        fun burgersInstance(): FragmentProductList {
            return FragmentProductList().apply {
                arguments = Bundle().apply {
                    putString(CATEGORY_KEY, CATEGORY_BURGERS)
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

        fun pizzaInstance(): FragmentProductList {
            return FragmentProductList().apply {
                arguments = Bundle().apply {
                    putString(CATEGORY_KEY, CATEGORY_PIZZA)
                }
            }
        }

        fun chickenInstance(): FragmentProductList {
            return FragmentProductList().apply {
                arguments = Bundle().apply {
                    putString(CATEGORY_KEY, CATEGORY_CHICKEN)
                }
            }
        }

        fun potatoInstance(): FragmentProductList {
            return FragmentProductList().apply {
                arguments = Bundle().apply {
                    putString(CATEGORY_KEY, CATEGORY_POTATO)
                }
            }
        }

        fun dessertsInstance(): FragmentProductList {
            return FragmentProductList().apply {
                arguments = Bundle().apply {
                    putString(CATEGORY_KEY, CATEGORY_DESSERTS)
                }
            }
        }
    }
}