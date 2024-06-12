package com.radlance.fooddelivery.data.repository

import com.radlance.fooddelivery.data.api.core.Service
import com.radlance.fooddelivery.data.api.request.NewUser
import com.radlance.fooddelivery.data.api.request.UserData
import com.radlance.fooddelivery.domain.core.LoadResult
import com.radlance.fooddelivery.domain.entity.User
import com.radlance.fooddelivery.domain.repository.AuthorizationRepository
import retrofit2.HttpException
import java.time.LocalDate

class AuthorizationRepositoryImpl(private val service: Service) : AuthorizationRepository {
    override suspend fun registerUser(user: User): LoadResult {
        val userInitials = user.fullName.split(" ")
        return try {
            service.registerUser(
                NewUser(
                    firstName = userInitials[0],
                    lastName = userInitials[1],
                    login = user.login,
                    password = user.password,
                    roleId = 3,
                    dateOfBirth = LocalDate.now().toString()
                )
            )
            val token = service.loginUser(UserData(user.login, user.password))
            LoadResult.Success(token.token)
        } catch (e: Exception) {
            LoadResult.Error(e is HttpException)
        }
    }

    override suspend fun loginUser(user: User): LoadResult {
        return try {
            val token = service.loginUser(UserData(user.login, user.password))
            LoadResult.Success(token.token)
        } catch (e: Exception) {
            LoadResult.Error(e is HttpException)
        }
    }
}