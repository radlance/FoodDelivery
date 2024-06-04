package com.radlance.fooddelivery.presentation.launch

import android.view.LayoutInflater
import android.view.ViewGroup
import com.radlance.fooddelivery.core.AbstractFragment
import com.radlance.fooddelivery.databinding.FragmentOnBoardingLastBinding

class LastOnBoardingFragment : AbstractFragment<FragmentOnBoardingLastBinding>() {
    override fun bind(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentOnBoardingLastBinding {
        return FragmentOnBoardingLastBinding.inflate(inflater, container, false)
    }
}