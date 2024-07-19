package com.radlance.fooddelivery.repository

import com.radlance.fooddelivery.data.api.core.Service
import com.radlance.fooddelivery.data.api.request.NewUser
import com.radlance.fooddelivery.data.api.request.UserData
import com.radlance.fooddelivery.data.api.response.Token
import com.radlance.fooddelivery.data.repository.AuthorizationRepositoryImpl
import com.radlance.fooddelivery.domain.core.AuthResult
import com.radlance.fooddelivery.domain.entity.User
import com.radlance.fooddelivery.domain.usecase.authorization.LoginUserUseCase
import com.radlance.fooddelivery.domain.usecase.authorization.RegisterUserUseCase
import kotlinx.coroutines.runBlocking
import okhttp3.ResponseBody.Companion.toResponseBody
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import retrofit2.HttpException
import retrofit2.Response
import java.time.LocalDate

class AuthorizationRepositoryImplTest {

    private val mockService = mock<Service>()
    private val authorizationRepository = AuthorizationRepositoryImpl(mockService)

    @Test
    fun testRegisterUserSuccess(): Unit = runBlocking {

        whenever(mockService.registerUser(NEW_USER)).thenReturn(Unit)
        whenever(
            mockService.loginUser(
                UserData(login = USER.login, password = USER.password)
            )
        ).thenReturn(TOKEN)

        val registerUserUseCase = RegisterUserUseCase(authorizationRepository)
        val result = registerUserUseCase(USER)

        assertThat(result).isEqualTo(AuthResult.Success(TOKEN.value))
    }

    @Test
    fun testRegisterUserServiceError(): Unit = runBlocking {
        whenever(mockService.registerUser(newUser = NEW_USER)).thenThrow(HttpException::class.java)

        val registerUserUseCase = RegisterUserUseCase(authorizationRepository)
        val result = registerUserUseCase(user = USER)

        assertThat(result).isEqualTo(AuthResult.Error(userAlreadyExist = false))
    }

    @Test
    fun testRegisterUserAlreadyExistError(): Unit = runBlocking {
        whenever(
            mockService.registerUser(NEW_USER)
        ).thenThrow(HttpException(Response.error<Any>(409, "".toResponseBody())))

        val registerUserUseCase = RegisterUserUseCase(authorizationRepository)
        val result = registerUserUseCase(USER)

        assertThat(result).isEqualTo(AuthResult.Error(userAlreadyExist = true))
    }

    @Test
    fun testRegisterUserRuntimeError(): Unit = runBlocking {
        whenever(
            mockService.registerUser(NEW_USER)
        ).thenThrow(RuntimeException::class.java)

        val registerUserUseCase = RegisterUserUseCase(authorizationRepository)
        val result = registerUserUseCase(USER)

        assertThat(result).isEqualTo(AuthResult.Error(userAlreadyExist = false))
    }

    @Test
    fun testLoginUserSuccess(): Unit = runBlocking {
        whenever(
            mockService.loginUser(
                UserData(login = USER.login, password = USER.password)
            )
        ).thenReturn(TOKEN)

        val loginUserUseCase = LoginUserUseCase(authorizationRepository)
        val result = loginUserUseCase(USER)

        assertThat(result).isEqualTo(AuthResult.Success(TOKEN.value))
    }

    @Test
    fun testLoginUserError(): Unit = runBlocking {
        whenever(
            mockService.loginUser(
                UserData(login = USER.login, password = USER.password)
            )
        ).thenThrow(HttpException::class.java)

        val loginUserUseCase = LoginUserUseCase(authorizationRepository)
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