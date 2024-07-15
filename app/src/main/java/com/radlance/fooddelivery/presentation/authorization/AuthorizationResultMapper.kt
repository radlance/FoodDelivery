package com.radlance.fooddelivery.presentation.authorization

import com.radlance.fooddelivery.domain.core.AuthResult

class AuthorizationResultMapper : AuthResult.Mapper<AuthorizationState> {
    override fun mapSuccess(token: String): AuthorizationState {
        return AuthorizationState.Success(token)
    }

    override fun mapLoading(): AuthorizationState {
        return AuthorizationState.Loading
    }

    override fun mapError(): AuthorizationState {
        return AuthorizationState.Error
    }
}