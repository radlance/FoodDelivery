package com.radlance.fooddelivery.presentation.authorization

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.radlance.fooddelivery.domain.core.LoadResult
import com.radlance.fooddelivery.domain.entity.User
import com.radlance.fooddelivery.domain.usecase.LoginUserUseCase
import com.radlance.fooddelivery.domain.usecase.RegisterUserUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

class SignUpViewModel(
    private val registerUserUseCase: RegisterUserUseCase,
    private val loginUserUseCase: LoginUserUseCase
) :
    ViewModel() {
        init {
            Log.d("AAA", "ViewModel created")
        }
    private val _registerResult = MutableLiveData<LoadResult>()
    val registerResult: LiveData<LoadResult>
        get() = _registerResult


    private val viewModelScope = CoroutineScope(SupervisorJob() + Dispatchers.Main.immediate)

    private val _errorInputEmail = MutableLiveData<Boolean>()
    val errorInputEmail: LiveData<Boolean>
        get() = _errorInputEmail

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
                fullName = parseString(fullName),
                login = parseString(login),
                password = parseString(password),
                phoneNumber = parseString(phoneNumber)
            )
            if (validateInput(registeredUser)) {
                _registerResult.value = registerUserUseCase(registeredUser)!!
            }
        }
    }

    fun loginUser() {
        viewModelScope.launch {
            loginUserUseCase(registeredUser)
        }
    }

    private fun validateInput(user: User): Boolean {
        val result = true
        with(user) {
            if (login.isBlank() || !login.matches(emailRegex)) {
                _errorInputEmail.value = true
                return false
            }

            if (password.length < 8 || password.length > 20) {
                _errorInputPassword.value = true
                return false
            }

            if (fullName.isBlank() || fullName.split(" ").size < 2) {
                _errorInputFullName.value = true
                return false
            }

            if (phoneNumber.isBlank()) {
                _errorInputNumber.value = true
                return false
            }
        }
        return result
    }

    fun resetErrorInputEmail() {
        _errorInputEmail.value = false
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

    private fun parseString(string: String?) = string?.trim() ?: ""

    companion object {
        private val emailRegex =
            Regex("^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$")
    }
}