package com.radlance.fooddelivery.presentation.authorization

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.radlance.fooddelivery.domain.core.AuthResult
import com.radlance.fooddelivery.domain.entity.User
import com.radlance.fooddelivery.domain.usecase.authorization.RegisterUserUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

class SignUpViewModel(
    private val registerUserUseCase: RegisterUserUseCase,
    private val mapper: AuthResult.Mapper<RegistrationState>
) :
    ViewModel() {
    private val _registerResult = MutableLiveData<RegistrationState>()
    val registerResult: LiveData<RegistrationState>
        get() = _registerResult


    private val viewModelScope = CoroutineScope(SupervisorJob() + Dispatchers.Main.immediate)

    private val _errorInputLogin = MutableLiveData<Boolean>()
    val errorInputLogin: LiveData<Boolean>
        get() = _errorInputLogin

    private val _errorInputPassword = MutableLiveData<Boolean>()
    val errorInputPassword: LiveData<Boolean>
        get() = _errorInputPassword

    private val _errorInputFullName = MutableLiveData<Boolean>()
    val errorInputFullName: LiveData<Boolean>
        get() = _errorInputFullName

    private val _errorInputNumber = MutableLiveData<Boolean>()
    val errorInputNumber: LiveData<Boolean>
        get() = _errorInputNumber

    private var _registeredUser: User? = null
    private val registeredUser: User
        get() = _registeredUser ?: throw RuntimeException("user == null")

    fun registerUser(fullName: String, login: String, password: String, phoneNumber: String) {
        viewModelScope.launch {
            _registeredUser = User(
                login = parseString(login),
                password = parseString(password),
                fullName = parseString(fullName),
                phoneNumber = parseString(phoneNumber)
            )
            if (validateInput(registeredUser)) {
                _registerResult.value = RegistrationState.Loading
                _registerResult.value = registerUserUseCase(registeredUser).map(mapper)
            }
        }
    }

    private fun validateInput(user: User): Boolean {
        val result = true
        with(user) {
            if (login.length < 5 || login.length > 15) {
                _errorInputLogin.value = true
                return false
            }

            if (password.length < 8 || password.length > 20) {
                _errorInputPassword.value = true
                return false
            }

            if (fullName.isBlank() || fullName.split(" ").size < 2) {
                _errorInputFullName.value = true
                return false
            } else {
                val splitInitials = fullName.split(" ")
                if ((splitInitials[0].length < 3 || splitInitials[0].length > 30) || (splitInitials[1].length < 3 || splitInitials[1].length > 30)) {
                    _errorInputFullName.value = true
                    return false
                }
            }

            if (phoneNumber.isBlank()) {
                _errorInputNumber.value = true
                return false
            }
        }
        return result
    }

    fun resetErrorInputEmail() {
        _errorInputLogin.value = false
    }

    fun resetErrorInputFullName() {
        _errorInputFullName.value = false
    }

    fun resetErrorInputPassword() {
        _errorInputPassword.value = false
    }

    fun resetErrorInputNumber() {
        _errorInputNumber.value = false
    }

}