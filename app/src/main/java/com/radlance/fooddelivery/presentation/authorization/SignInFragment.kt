package com.radlance.fooddelivery.presentation.authorization

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.radlance.fooddelivery.R
import com.radlance.fooddelivery.databinding.FragmentSignInBinding
import com.radlance.fooddelivery.domain.core.AuthResult
import com.radlance.fooddelivery.presentation.core.AbstractFragment
import com.radlance.fooddelivery.presentation.core.IncorrectFillDialog
import com.radlance.fooddelivery.presentation.main.MainActivity

class SignInFragment : AbstractFragment<FragmentSignInBinding>() {
    private val viewModel: SignInViewModel by lazy {
        ViewModelProvider(this, SignInViewModelFactory())[SignInViewModel::class.java]
    }
    override fun bind(inflater: LayoutInflater, container: ViewGroup?): FragmentSignInBinding {
        return FragmentSignInBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.buttonLogin.setOnClickListener {
            viewModel.loginUser(
                login = binding.etEmail.text.toString(),
                password = binding.etPassword.text.toString()
            )
        }

        viewModel.loginResult.observe(viewLifecycleOwner) { loginResult ->
            when (loginResult) {
                is AuthResult.Success -> {
                    val intent = MainActivity
                        .newInstance(requireActivity().applicationContext, loginResult.token)
                    startActivity(intent)
                }

                is AuthResult.Error -> showErrorDialog(R.string.failed_registration)
            }
        }

        viewModel.errorInputLogin.observe(viewLifecycleOwner) {
            if (it) {
                viewModel.resetErrorInputLogin()
                showErrorDialog(R.string.incorrect_email)
            }
        }

        viewModel.errorInputPassword.observe(viewLifecycleOwner) {
            if (it) {
                viewModel.resetErrorInputPassword()
                showErrorDialog(R.string.incorrect_password)
            }
        }
    }

    private fun showErrorDialog(errorMessage: Int) {
        IncorrectFillDialog.newInstance(getString(errorMessage))
            .show(requireActivity().supportFragmentManager, ERROR_DIALOG)
    }

    companion object {
        private const val ERROR_DIALOG = "error_dialog"
        fun newInstance(): SignInFragment {
            return SignInFragment()
        }
    }
}