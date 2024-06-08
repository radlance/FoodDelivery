package com.radlance.fooddelivery.presentation.authorization

import android.view.LayoutInflater
import android.view.ViewGroup
import com.radlance.fooddelivery.core.AbstractFragment
import com.radlance.fooddelivery.databinding.FragmentSignInBinding

class SignInFragment : AbstractFragment<FragmentSignInBinding>() {
    override fun bind(inflater: LayoutInflater, container: ViewGroup?): FragmentSignInBinding {
        return FragmentSignInBinding.inflate(inflater, container, false)
    }

    companion object {
        fun newInstance(): SignInFragment {
            return SignInFragment()
        }
    }
}