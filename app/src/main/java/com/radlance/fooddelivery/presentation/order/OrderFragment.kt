package com.radlance.fooddelivery.presentation.order

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.SimpleItemAnimator
import com.radlance.fooddelivery.R
import com.radlance.fooddelivery.databinding.FragmentOrderBinding
import com.radlance.fooddelivery.presentation.core.AbstractFragment


class OrderFragment : AbstractFragment<FragmentOrderBinding>() {
    private val token: String by lazy {
        requireArguments().getString(TOKEN_EXTRA)!!
    }
    val viewModel: OrderViewModel by lazy {
        ViewModelProvider(this, OrderViewModelFactory(token))[OrderViewModel::class.java]
    }
    private lateinit var orderListAdapter: OrderListRecyclerAdapter
    override fun bind(inflater: LayoutInflater, container: ViewGroup?): FragmentOrderBinding {
        return FragmentOrderBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.rvOrders.apply {
            orderListAdapter = OrderListRecyclerAdapter()
            adapter = orderListAdapter
        }
        (binding.rvOrders.itemAnimator as SimpleItemAnimator).supportsChangeAnimations = false

        binding.buttonOrder.setOnClickListener {
            viewModel.createDelivery(binding.etAddress.text.toString(), orderListAdapter.orderList)
        }
        viewModel.getFullCartItemInfo()

        viewModel.orderState.observe(viewLifecycleOwner) {
            it.show(
                binding.placeholder,
                binding.rvOrders,
                binding.viewPrice,
                binding.linearPrice,
                binding.buttonOrder,
                binding.tvOrderNow,
                orderListAdapter
            )
        }

        orderListAdapter.incrementButtonClickListener = {
            viewModel.incrementCount(it)
        }

        orderListAdapter.decrementButtonClickListener = {
            viewModel.decrementCount(it)
        }

        orderListAdapter.onCartItemLongClickListener = {
            viewModel.deleteCartItem(it)
        }

        viewModel.updatedCartItem.observe(viewLifecycleOwner) {
            orderListAdapter.updateItemCount(it)
        }

        viewModel.totalOrderCost.observe(viewLifecycleOwner) {
            binding.tvPrice.text = it.toString()
        }

        viewModel.addressInputError.observe(viewLifecycleOwner) {
            if (it) {
                Toast.makeText(
                    requireActivity().applicationContext,
                    getString(R.string.address_input_error),
                    Toast.LENGTH_LONG
                ).show()
            }
        }

        viewModel.deliveryResult.observe(viewLifecycleOwner) {
            it.show(this, binding.pbAdd, binding.buttonOrder, binding.tvOrderNow, viewModel)
        }
    }

    companion object {
        private const val TOKEN_EXTRA = "token"
        fun newInstance(token: String): OrderFragment {
            return OrderFragment().apply {
                arguments = Bundle().apply {
                    putString(
                        TOKEN_EXTRA, token
                    )
                }
            }
        }
    }
}