package com.radlance.fooddelivery.presentation.order

import android.view.LayoutInflater
import android.view.ViewGroup
import com.radlance.fooddelivery.databinding.FragmentOrderBinding
import com.radlance.fooddelivery.presentation.core.AbstractFragment

class OrderFragment : AbstractFragment<FragmentOrderBinding>() {
    override fun bind(inflater: LayoutInflater, container: ViewGroup?): FragmentOrderBinding {
        return FragmentOrderBinding.inflate(inflater, container, false)
    }

    companion object {
        fun newInstance(): OrderFragment {
            return OrderFragment()
        }
    }
}