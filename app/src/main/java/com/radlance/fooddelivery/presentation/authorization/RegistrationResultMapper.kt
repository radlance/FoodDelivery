package com.radlance.fooddelivery.presentation.authorization

import com.radlance.fooddelivery.domain.core.AuthResult

class RegistrationResultMapper : AuthResult.Mapper<RegistrationState> {
    override fun mapSuccess(token: String): RegistrationState {
        return RegistrationState.Success(token)
    }

    override fun mapLoading(): RegistrationState {
        return RegistrationState.Loading
    }

    override fun mapError(): RegistrationState {
        return RegistrationState.Error
    }
}