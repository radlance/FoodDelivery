package com.radlance.fooddelivery.presentation.main

import android.view.LayoutInflater
import android.view.ViewGroup
import com.radlance.fooddelivery.databinding.FragmentProductListBinding
import com.radlance.fooddelivery.presentation.core.AbstractFragment

class FragmentProductList : AbstractFragment<FragmentProductListBinding>() {
    override fun bind(inflater: LayoutInflater, container: ViewGroup?): FragmentProductListBinding {
        return FragmentProductListBinding.inflate(inflater, container, false)
    }

    companion object {
        fun newInstance(): FragmentProductList {
            return FragmentProductList()
        }
    }
}