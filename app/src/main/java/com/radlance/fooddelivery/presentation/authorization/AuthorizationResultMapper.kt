package com.radlance.fooddelivery.presentation.authorization

import com.radlance.fooddelivery.domain.core.AuthResult

class AuthorizationResultMapper : AuthResult.Mapper<AuthorizationState> {
    override fun mapSuccess(token: String): AuthorizationState {
        return AuthorizationState.Success(token)
    }

    override fun mapError(userAlreadyExist: Boolean): AuthorizationState {
        return AuthorizationState.Error
    }
}