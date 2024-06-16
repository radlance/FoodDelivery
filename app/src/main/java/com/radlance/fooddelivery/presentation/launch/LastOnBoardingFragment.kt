package com.radlance.fooddelivery.presentation.launch

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.radlance.fooddelivery.presentation.core.AbstractFragment
import com.radlance.fooddelivery.databinding.FragmentOnBoardingLastBinding
import com.radlance.fooddelivery.presentation.authorization.AuthorizationActivity
import com.radlance.fooddelivery.presentation.main.MainActivity

class LastOnBoardingFragment : AbstractFragment<FragmentOnBoardingLastBinding>() {
    override fun bind(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentOnBoardingLastBinding {
        return FragmentOnBoardingLastBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.buttonSingIn.setOnClickListener {
            val intent = AuthorizationActivity.newInstanceSignIn(requireContext())
            startActivity(intent)
        }

        binding.buttonSignUp.setOnClickListener {
            val intent = AuthorizationActivity.newInstanceSignUp(requireContext())
            startActivity(intent)
        }

        binding.tvSkipAuthorization.setOnClickListener {
            val intent = MainActivity.newInstance(requireActivity().applicationContext)
            startActivity(intent)
        }
    }
}