package com.radlance.fooddelivery.presentation.catalog.core

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.radlance.fooddelivery.databinding.FragmentProductListBinding
import com.radlance.fooddelivery.presentation.core.AbstractFragment
import com.radlance.fooddelivery.presentation.main.FragmentReplaceListener

abstract class AbstractProductListFragment : AbstractFragment<FragmentProductListBinding>() {
    private lateinit var productListAdapter: ProductListRecyclerAdapter
    private lateinit var fragmentReplaceListener: FragmentReplaceListener
    protected val viewModel: ProductListViewModel by lazy {
        ViewModelProvider(
            this,
            ProductListViewModelFactory()
        )[ProductListViewModel::class.java]
    }

    override fun bind(inflater: LayoutInflater, container: ViewGroup?): FragmentProductListBinding {
        return FragmentProductListBinding.inflate(inflater, container, false)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is FragmentReplaceListener) {
            fragmentReplaceListener = context
        } else {
            throw RuntimeException("Activity must implement FragmentReplaceListener")
        }
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.rvProductList.apply {
            productListAdapter = ProductListRecyclerAdapter()
            layoutManager = GridLayoutManager(requireActivity(), 2)
            adapter = productListAdapter
        }
        getProductList()

        productListAdapter.onProductItemClickListener = { product ->
            viewModel.showDetails(product)
        }

        binding.ivBack.setOnClickListener {
            viewModel.closeDetails()
        }

        view.setOnClickListener {
            viewModel.closeDetails()
        }

        binding.buttonContinue.setOnClickListener {
            viewModel.closeDetails()
        }

        binding.buttonGoToCart.setOnClickListener {
            fragmentReplaceListener.orderReplace()
        }

        viewModel.openedProductDetails.observe(viewLifecycleOwner) { state ->
            state.show(
                binding.cardViewDetails,
                binding.rvProductList,
                binding.tvTitle,
                binding.tvPrice,
                binding.ivProduct
            )

            binding.tvMore.setOnClickListener {
                val intent = DetailActivity.productInstance(
                    requireActivity(),
                    (state as DetailsState.Opened).product
                )
                startActivity(intent)
            }

            binding.buttonAdd.setOnClickListener {
                viewModel.addToCart(
                    binding.tvCount.text.toString(),
                    (state as DetailsState.Opened).product
                )
                viewModel.showActions()
            }
        }
        viewModel.loadState.observe(viewLifecycleOwner) { loadResult ->
            loadResult.show(
                viewModel,
                productListAdapter,
                binding.tvNoConnection,
                binding.progressLoading,
                binding.buttonRetry
            )
        }

        viewModel.localProducts.observe(viewLifecycleOwner) { productList ->
            productListAdapter.productList = productList
            binding.progressLoading.visibility = View.GONE
        }

        viewModel.actionsState.observe(viewLifecycleOwner) {
            with(binding) {
                it.more(
                    buttonGoToCart,
                    buttonContinue,
                    buttonPlus,
                    buttonMinus,
                    tvAdd,
                    tvGoToCart,
                    tvButtonContinueText,
                    tvCount,
                    ivCart
                )
            }
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