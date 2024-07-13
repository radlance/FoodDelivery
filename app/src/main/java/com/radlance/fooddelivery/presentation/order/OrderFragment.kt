package com.radlance.fooddelivery.presentation.order

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.SimpleItemAnimator
import com.radlance.fooddelivery.databinding.FragmentOrderBinding
import com.radlance.fooddelivery.presentation.core.AbstractFragment


class OrderFragment : AbstractFragment<FragmentOrderBinding>() {
    val viewModel: OrderViewModel by lazy {
        ViewModelProvider(this, OrderViewModelFactory())[OrderViewModel::class.java]
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

        viewModel.getFullCartItemInfo()

        viewModel.orderState.observe(viewLifecycleOwner) {
            it.show(
                binding.placeholder,
                binding.viewPrice,
                binding.linearPrice,
                binding.buttonOrder,
                orderListAdapter
            )
        }

        orderListAdapter.incrementButtonClickListener = {
            viewModel.incrementCount(it)
        }

        orderListAdapter.decrementButtonClickListener = {
            viewModel.decrementCount(it)
        }

        viewModel.updatedCartItem.observe(viewLifecycleOwner) {
            orderListAdapter.updateItemCount(it)
        }
    }

    companion object {
        fun newInstance(): OrderFragment {
            return OrderFragment()
        }
    }
}