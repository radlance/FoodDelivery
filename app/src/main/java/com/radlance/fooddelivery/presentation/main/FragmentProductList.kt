package com.radlance.fooddelivery.presentation.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.radlance.fooddelivery.databinding.FragmentProductListBinding
import com.radlance.fooddelivery.presentation.core.AbstractFragment

class FragmentProductList : AbstractFragment<FragmentProductListBinding>() {
    override fun bind(inflater: LayoutInflater, container: ViewGroup?): FragmentProductListBinding {
        return FragmentProductListBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.rvProductList.apply {
            val productListAdapter = ProductListAdapter()
            layoutManager = GridLayoutManager(requireActivity(), 2)
            adapter = productListAdapter
        }
    }

    companion object {
        fun newInstance(): FragmentProductList {
            return FragmentProductList()
        }
    }
}