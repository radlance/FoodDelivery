package com.radlance.fooddelivery.presentation.authorization

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.radlance.fooddelivery.R
import com.radlance.fooddelivery.databinding.FragmentSignUpBinding
import com.radlance.fooddelivery.presentation.core.AbstractFragment
import com.radlance.fooddelivery.presentation.core.IncorrectFillDialog
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignUpFragment : AbstractFragment<FragmentSignUpBinding>() {
    private val viewModel: SignUpViewModel by viewModels()
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
            showErrorDialog(getString(R.string.incorrect_fullname))
        }

        viewModel.errorInputEmail.observe(viewLifecycleOwner) {
            showErrorDialog(getString(R.string.incorrect_email))
        }

        viewModel.errorInputPassword.observe(viewLifecycleOwner) {
            showErrorDialog(getString(R.string.incorrect_password))
        }

        viewModel.errorInputNumber.observe(viewLifecycleOwner) {
            showErrorDialog(getString(R.string.incorrect_number))
        }

    }

    private fun showErrorDialog(errorMessage: String) {
        IncorrectFillDialog.newInstance(errorMessage)
            .show(requireActivity().supportFragmentManager, ERROR_DIALOG)
    }

    companion object {
        private const val ERROR_DIALOG = "error_dialog"
        fun newInstance(): SignUpFragment {
            return SignUpFragment()
        }
    }
}