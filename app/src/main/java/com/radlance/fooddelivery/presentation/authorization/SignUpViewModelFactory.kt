package com.radlance.fooddelivery.presentation.authorization

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewmodel.CreationExtras
import com.radlance.fooddelivery.domain.usecase.RegisterUserUseCase
import com.radlance.fooddelivery.presentation.core.ProvideRepository

class SignUpViewModelFactory : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {

        val application = checkNotNull(extras[APPLICATION_KEY])
        val repositoryImpl = (application as ProvideRepository).authorizationRepository()
        val registrationUserUseCase = RegisterUserUseCase(repositoryImpl)
        return SignUpViewModel(registrationUserUseCase) as T
    }
}