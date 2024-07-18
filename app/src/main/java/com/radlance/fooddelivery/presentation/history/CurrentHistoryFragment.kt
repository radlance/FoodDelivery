package com.radlance.fooddelivery.presentation.history

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.radlance.fooddelivery.R
import com.radlance.fooddelivery.databinding.FragmentCurrentHistoryBinding
import com.radlance.fooddelivery.domain.entity.HistoryItem
import com.radlance.fooddelivery.presentation.core.AbstractFragment
import com.radlance.fooddelivery.presentation.order.OrderFragment

class CurrentHistoryFragment : AbstractFragment<FragmentCurrentHistoryBinding>() {
    private val currentHistoryItem: HistoryItem by lazy {
        @Suppress("DEPRECATION")
        requireArguments().getParcelable(CURRENT_HISTORY_KEY)!!
    }

    private val token: String by lazy {
        requireArguments().getString(TOKEN_EXTRA)!!
    }

    private val viewModel: CurrentHistoryViewModel by lazy {
        ViewModelProvider(
            this,
            CurrentHistoryViewModelFactory()
        )[CurrentHistoryViewModel::class.java]
    }

    private lateinit var currentHistoryAdapter: CurrentHistoryAdapter

    override fun bind(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentCurrentHistoryBinding {
        return FragmentCurrentHistoryBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.rvCurrentHistory.apply {
            currentHistoryAdapter = CurrentHistoryAdapter()
            adapter = currentHistoryAdapter
        }
        currentHistoryAdapter.orderList = currentHistoryItem.productDeliveries

        binding.tvDate.text = setOrderDate(currentHistoryItem)
        binding.tvAddress.text =
            setOrderAddress(currentHistoryItem.street, currentHistoryItem.house)
        binding.tvOrderPrice.text =
            currentHistoryItem.productDeliveries.sumOf {
                it.product.price * it.count
            }.toInt().toString()

        binding.buttonRepeatOrder.setOnClickListener {
            viewModel.repeatOrder(currentHistoryItem.productDeliveries)
        }

        viewModel.moveToCart.observe(viewLifecycleOwner) {
            requireActivity().supportFragmentManager
                .beginTransaction()
                .replace(R.id.container_main, OrderFragment.newInstance(token))
                .commit()
        }
    }

    companion object {
        private const val CURRENT_HISTORY_KEY = "current_history"
        private const val TOKEN_EXTRA = "token"
        fun newInstance(historyItem: HistoryItem, token: String): CurrentHistoryFragment {
            return CurrentHistoryFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(CURRENT_HISTORY_KEY, historyItem)
                    putString(TOKEN_EXTRA, token)
                }
            }
        }
    }
}