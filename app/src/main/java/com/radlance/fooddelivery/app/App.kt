package com.radlance.fooddelivery.app

import android.app.Application
import com.radlance.fooddelivery.data.api.core.BaseService
import com.radlance.fooddelivery.data.repository.AuthorizationRepositoryImpl
import com.radlance.fooddelivery.domain.repository.AuthorizationRepository
import com.radlance.fooddelivery.presentation.core.ProvideRepository

class App : Application(), ProvideRepository {

    override fun authorizationRepository(): AuthorizationRepository {
        return AuthorizationRepositoryImpl(BaseService())
    }
}