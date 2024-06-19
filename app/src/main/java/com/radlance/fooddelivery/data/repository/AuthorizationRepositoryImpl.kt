package com.radlance.fooddelivery.data.repository

import com.radlance.fooddelivery.data.api.core.Service
import com.radlance.fooddelivery.data.api.request.NewUser
import com.radlance.fooddelivery.data.api.request.UserData
import com.radlance.fooddelivery.domain.core.AuthResult
import com.radlance.fooddelivery.domain.entity.User
import com.radlance.fooddelivery.domain.repository.AuthorizationRepository
import java.time.LocalDate

class AuthorizationRepositoryImpl(private val service: Service) : AuthorizationRepository {
    override suspend fun registerUser(user: User): AuthResult {
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
            AuthResult.Success(token.token)
        } catch (e: Exception) {
            AuthResult.Error
        }
    }

    override suspend fun loginUser(user: User): AuthResult {
        return try {
            val token = service.loginUser(UserData(user.login, user.password))
            AuthResult.Success(token.token)
        } catch (e: Exception) {
            AuthResult.Error
        }
    }
}