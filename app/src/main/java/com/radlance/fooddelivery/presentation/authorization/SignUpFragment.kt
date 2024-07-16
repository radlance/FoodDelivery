package com.radlance.fooddelivery.presentation.authorization

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.radlance.fooddelivery.R
import com.radlance.fooddelivery.databinding.FragmentSignUpBinding
import com.radlance.fooddelivery.presentation.core.AbstractFragment
import com.radlance.fooddelivery.presentation.core.IncorrectFillDialog

class SignUpFragment : AbstractFragment<FragmentSignUpBinding>() {
    private val viewModel: SignUpViewModel by lazy {
        ViewModelProvider(this, SignUpViewModelFactory())[SignUpViewModel::class.java]
    }
    override fun bind(inflater: LayoutInflater, container: ViewGroup?): FragmentSignUpBinding {
        return FragmentSignUpBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.buttonCancel.setOnClickListener {
            requireActivity()
                .supportFragmentManager
                .beginTransaction()
                .replace(R.id.container_auth, SignInFragment.newInstance())
                .commit()
        }

        with(binding) {
            buttonRegister.setOnClickListener {
                viewModel.registerUser(
                    fullName = etFullName.text.toString(),
                    login = etEmail.text.toString(),
                    password = etPassword.text.toString(),
                    phoneNumber = etPhoneNumber.text.toString()
                )
            }
        }

        viewModel.errorInputFullName.observe(viewLifecycleOwner) {
            if (it) {
                viewModel.resetErrorInputFullName()
                showErrorDialog(R.string.incorrect_fullname)
            }
        }

        viewModel.errorInputEmail.observe(viewLifecycleOwner) {
            if (it) {
                viewModel.resetErrorInputEmail()
                showErrorDialog(R.string.incorrect_email)
            }
        }

        viewModel.errorInputPassword.observe(viewLifecycleOwner) {
            if (it) {
                viewModel.resetErrorInputPassword()
                showErrorDialog(R.string.incorrect_password)
            }
        }

        viewModel.errorInputNumber.observe(viewLifecycleOwner) {
            if (it) {
                viewModel.resetErrorInputNumber()
                showErrorDialog(R.string.incorrect_number)
            }
        }

        viewModel.registerResult.observe(viewLifecycleOwner) {
            it.show(
                requireActivity(),
                binding.pbRegister,
                binding.tvRegister,
                binding.buttonRegister
            )
        }
    }

    private fun showErrorDialog(errorMessage: Int) {
        IncorrectFillDialog.newInstance(getString(errorMessage))
            .show(requireActivity().supportFragmentManager, ERROR_DIALOG)
    }

    companion object {
        const val ERROR_DIALOG = "error_dialog"
        fun newInstance(): SignUpFragment {
            return SignUpFragment()
        }
    }
}

