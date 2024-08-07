package com.radlance.fooddelivery.repository

import com.radlance.fooddelivery.data.api.core.Service
import com.radlance.fooddelivery.data.api.request.NewUser
import com.radlance.fooddelivery.data.api.request.UserData
import com.radlance.fooddelivery.data.api.response.Token
import com.radlance.fooddelivery.data.repository.AuthorizationRepositoryImpl
import com.radlance.fooddelivery.domain.core.AuthResult
import com.radlance.fooddelivery.domain.entity.User
import com.radlance.fooddelivery.domain.repository.AuthorizationRepository
import com.radlance.fooddelivery.domain.usecase.authorization.LoginUserUseCase
import com.radlance.fooddelivery.domain.usecase.authorization.RegisterUserUseCase
import kotlinx.coroutines.runBlocking
import okhttp3.ResponseBody.Companion.toResponseBody
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import retrofit2.HttpException
import retrofit2.Response
import java.time.LocalDate

class AuthorizationRepositoryImplTest {

    private lateinit var mockService: Service
    private lateinit var repository: AuthorizationRepository

    @BeforeEach
    fun setup() {
        mockService = mock()
        repository = AuthorizationRepositoryImpl(mockService)
    }

    @Test
    fun test_register_user_success(): Unit = runBlocking {

        whenever(mockService.registerUser(NEW_USER)).thenReturn(Unit)
        whenever(
            mockService.loginUser(
                UserData(login = USER.login, password = USER.password)
            )
        ).thenReturn(TOKEN)

        val registerUserUseCase = RegisterUserUseCase(repository)
        val result = registerUserUseCase(USER)

        assertThat(result).isEqualTo(AuthResult.Success(TOKEN.value))
    }

    @Test
    fun test_register_user_service_error(): Unit = runBlocking {
        whenever(mockService.registerUser(newUser = NEW_USER)).thenThrow(HttpException::class.java)

        val registerUserUseCase = RegisterUserUseCase(repository)
        val result = registerUserUseCase(user = USER)

        assertThat(result).isEqualTo(AuthResult.Error(userAlreadyExist = false))
    }

    @Test
    fun test_register_user_already_exist_error(): Unit = runBlocking {
        whenever(
            mockService.registerUser(NEW_USER)
        ).thenThrow(HttpException(Response.error<Any>(409, "".toResponseBody())))

        val registerUserUseCase = RegisterUserUseCase(repository)
        val result = registerUserUseCase(USER)

        assertThat(result).isEqualTo(AuthResult.Error(userAlreadyExist = true))
    }

    @Test
    fun test_register_user_runtime_error(): Unit = runBlocking {
        whenever(
            mockService.registerUser(NEW_USER)
        ).thenThrow(RuntimeException::class.java)

        val registerUserUseCase = RegisterUserUseCase(repository)
        val result = registerUserUseCase(USER)

        assertThat(result).isEqualTo(AuthResult.Error(userAlreadyExist = false))
    }

    @Test
    fun test_login_user_success(): Unit = runBlocking {
        whenever(
            mockService.loginUser(
                UserData(login = USER.login, password = USER.password)
            )
        ).thenReturn(TOKEN)

        val loginUserUseCase = LoginUserUseCase(repository)
        val result = loginUserUseCase(USER)

        assertThat(result).isEqualTo(AuthResult.Success(TOKEN.value))
    }

    @Test
    fun test_login_user_error(): Unit = runBlocking {
        whenever(
            mockService.loginUser(
                UserData(login = USER.login, password = USER.password)
            )
        ).thenThrow(HttpException::class.java)

        val loginUserUseCase = LoginUserUseCase(repository)
        val result = loginUserUseCase(USER)

        assertThat(result).isEqualTo(AuthResult.Error(userAlreadyExist = false))
    }


    companion object {
        private val USER = User(
            login = "user1@gmail.com",
            password = "12345678",
            fullName = "name lastName"
        )

        private val NEW_USER = NewUser(
            firstName = "name",
            lastName = "lastName",
            login = "user1@gmail.com",
            password = "12345678",
            roleId = 3,
            dateOfBirth = LocalDate.now().toString()
        )

        private val TOKEN = Token(value = "token123")
    }
}