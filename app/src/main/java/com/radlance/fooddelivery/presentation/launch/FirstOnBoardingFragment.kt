package com.radlance.fooddelivery.presentation.launch

import android.view.LayoutInflater
import android.view.ViewGroup
import com.radlance.fooddelivery.core.AbstractFragment
import com.radlance.fooddelivery.databinding.FragmentOnBoardingFirstBinding

class FirstOnBoardingFragment : AbstractFragment<FragmentOnBoardingFirstBinding>() {
    override fun bind(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentOnBoardingFirstBinding {
        return FragmentOnBoardingFirstBinding.inflate(inflater, container, false)
    }
}