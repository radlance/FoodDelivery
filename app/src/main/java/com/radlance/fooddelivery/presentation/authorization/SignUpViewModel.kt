package com.radlance.fooddelivery.presentation.authorization

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.radlance.fooddelivery.domain.core.LoadResult
import com.radlance.fooddelivery.domain.entity.User
import com.radlance.fooddelivery.domain.usecase.RegisterUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(private val registerUserUseCase: RegisterUserUseCase) :
    ViewModel() {
    private val _registerResult = MutableLiveData<LoadResult>()
    val registerResult: LiveData<LoadResult>
        get() = _registerResult


    private val viewModelScope = CoroutineScope(SupervisorJob() + Dispatchers.Main.immediate)

    private val _errorInputEmail = MutableLiveData<Unit>()
    val errorInputEmail: LiveData<Unit>
        get() = _errorInputEmail

    private val _errorInputPassword = MutableLiveData<Unit>()
    val errorInputPassword: LiveData<Unit>
        get() = _errorInputPassword

    private val _errorInputFullName = MutableLiveData<Unit>()
    val errorInputFullName: LiveData<Unit>
        get() = _errorInputFullName

    private val _errorInputNumber = MutableLiveData<Unit>()
    val errorInputNumber: LiveData<Unit>
        get() = _errorInputNumber

    fun registerUser(fullName: String, login: String, password: String, phoneNumber: String) {
        viewModelScope.launch {
            val formattedUser = User(
                fullName = parseString(fullName),
                login = parseString(login),
                password = parseString(password),
                phoneNumber = parseString(phoneNumber)
            )
            if (validateInput(formattedUser)) {
                _registerResult.value = registerUserUseCase(formattedUser)!!
            }
        }
    }

    private fun validateInput(user: User): Boolean {
        val result = true
        with(user) {
            if (login.isBlank() || !login.matches(emailRegex)) {
                _errorInputEmail.value = Unit
                return false
            }

            if (password.length < 8 || password.length > 20) {
                _errorInputPassword.value = Unit
                return false
            }

            if (fullName.isBlank() || fullName.split(" ").size < 2) {
                _errorInputFullName.value = Unit
                return false
            }

            if (phoneNumber.isBlank()) {
                _errorInputNumber.value = Unit
                return false
            }
        }
        return result
    }

    private fun parseString(string: String?) = string?.trim() ?: ""

    companion object {
        private val emailRegex =
            Regex("^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$")
    }
}