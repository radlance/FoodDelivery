package com.radlance.fooddelivery.app

import android.app.Application
import com.radlance.fooddelivery.data.api.core.BaseService
import com.radlance.fooddelivery.data.repository.UserRepositoryImpl
import com.radlance.fooddelivery.domain.repository.UserRepository
import com.radlance.fooddelivery.presentation.core.ProvideRepository

class App : Application(), ProvideRepository {

    override fun userRepository(): UserRepository {
        return UserRepositoryImpl(BaseService())
    }
}