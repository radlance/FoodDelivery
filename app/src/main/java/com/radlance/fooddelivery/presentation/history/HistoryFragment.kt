package com.radlance.fooddelivery.presentation.history

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.radlance.fooddelivery.R
import com.radlance.fooddelivery.databinding.FramentHistoryBinding
import com.radlance.fooddelivery.presentation.core.AbstractFragment

class HistoryFragment : AbstractFragment<FramentHistoryBinding>() {
    private val token: String by lazy {
        requireArguments().getString(TOKEN_EXTRA)!!
    }
    private lateinit var historyAdapter: HistoryRecyclerAdapter

    private val viewModel: HistoryViewModel by lazy {
        ViewModelProvider(this, HistoryViewModelFactory(token))[HistoryViewModel::class.java]
    }

    override fun bind(inflater: LayoutInflater, container: ViewGroup?): FramentHistoryBinding {
        return FramentHistoryBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.rvHistory.apply {
            historyAdapter = HistoryRecyclerAdapter()
            adapter = historyAdapter
        }

        viewModel.loadHistory()

        viewModel.history.observe(viewLifecycleOwner) {
            it.show(
                historyAdapter,
                binding.placeholder,
                binding.rvHistory,
                binding.pbHistory,
                binding.buttonRetry,
                binding.buttonExit,
                binding.tvError,
                this
            )
        }

        binding.buttonRetry.setOnClickListener {
            viewModel.loadHistory()
        }

        binding.buttonExit.setOnClickListener {
            requireActivity().finish()
        }

        historyAdapter.onHistoryItemClickListener = {
            requireActivity().supportFragmentManager
                .beginTransaction()
                .replace(R.id.container_main, CurrentHistoryFragment.newInstance(it, token))
                .addToBackStack(null)
                .commit()
        }
    }

    companion object {
        private const val TOKEN_EXTRA = "token"
        fun newInstance(token: String): HistoryFragment {
            return HistoryFragment().apply {
                arguments = Bundle().apply {
                    putString(
                        TOKEN_EXTRA, token
                    )
                }
            }
        }
    }
}