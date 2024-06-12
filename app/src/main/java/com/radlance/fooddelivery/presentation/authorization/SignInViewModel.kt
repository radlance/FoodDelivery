package com.radlance.fooddelivery.presentation.authorization

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.radlance.fooddelivery.domain.core.LoadResult
import com.radlance.fooddelivery.domain.entity.User
import com.radlance.fooddelivery.domain.usecase.LoginUserUseCase
import com.radlance.fooddelivery.presentation.core.parseString
import com.radlance.fooddelivery.presentation.core.validateLogin
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

class SignInViewModel(private val loginUserUseCase: LoginUserUseCase) : ViewModel() {
    private val viewModelScope = CoroutineScope(SupervisorJob() + Dispatchers.Main.immediate)

    private val _loginResult = MutableLiveData<LoadResult>()
    val loginResult: LiveData<LoadResult>
        get() = _loginResult

    private val _errorInputLogin = MutableLiveData<Boolean>()
    val errorInputLogin: LiveData<Boolean>
        get() = _errorInputLogin

    private val _errorInputPassword = MutableLiveData<Boolean>()
    val errorInputPassword: LiveData<Boolean>
        get() = _errorInputPassword

    fun loginUser(login: String, password: String) {
        viewModelScope.launch {
            val currentUser = User(
                login = parseString(login),
                password = parseString(password)
            )
            if (validateInput(currentUser)) {
                _loginResult.value = loginUserUseCase(currentUser)
            }
        }
    }

    private fun validateInput(user: User): Boolean {
        val result = true
        with(user) {
            if (!validateLogin(login) || login.isBlank()) {
                _errorInputLogin.value = true
                return false
            }
            if (password.length < 8 || password.length > 30) {
                _errorInputPassword.value = true
                return false
            }
        }
        return result
    }

    fun resetErrorInputLogin() {
        _errorInputLogin.value = false
    }

    fun resetErrorInputPassword() {
        _errorInputPassword.value = false
    }
}