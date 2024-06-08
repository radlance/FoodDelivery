package com.radlance.fooddelivery.presentation.authorization

import android.view.LayoutInflater
import android.view.ViewGroup
import com.radlance.fooddelivery.core.AbstractFragment
import com.radlance.fooddelivery.databinding.FragmentSignUpBinding

class SignUpFragment : AbstractFragment<FragmentSignUpBinding>() {
    override fun bind(inflater: LayoutInflater, container: ViewGroup?): FragmentSignUpBinding {
        return FragmentSignUpBinding.inflate(inflater, container, false)
    }

    companion object {
        fun newInstance(): SignUpFragment {
            return SignUpFragment()
        }
    }
}