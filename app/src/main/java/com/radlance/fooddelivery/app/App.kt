package com.radlance.fooddelivery.app

import android.app.Application
import com.radlance.fooddelivery.data.api.core.BaseService
import com.radlance.fooddelivery.data.database.DeliveryDatabase
import com.radlance.fooddelivery.data.repository.AuthorizationRepositoryImpl
import com.radlance.fooddelivery.data.repository.MainRepositoryImpl
import com.radlance.fooddelivery.domain.repository.AuthorizationRepository
import com.radlance.fooddelivery.domain.repository.MainRepository
import com.radlance.fooddelivery.presentation.core.ProvideRepository

class App : Application(), ProvideRepository {

    override fun authorizationRepository(): AuthorizationRepository {
        return AuthorizationRepositoryImpl(BaseService())
    }

    override fun mainRepository(token: String): MainRepository {
        val dao = DeliveryDatabase.newInstance(applicationContext).productsDao()
        return MainRepositoryImpl(BaseService(token), dao)
    }
}