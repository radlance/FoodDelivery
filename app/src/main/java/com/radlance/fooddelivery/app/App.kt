package com.radlance.fooddelivery.app

import android.app.Application
import com.radlance.fooddelivery.data.api.core.BaseService
import com.radlance.fooddelivery.data.database.DeliveryDatabase
import com.radlance.fooddelivery.data.repository.AuthorizationRepositoryImpl
import com.radlance.fooddelivery.data.repository.CatalogRepositoryImpl
import com.radlance.fooddelivery.data.repository.HistoryRepositoryImpl
import com.radlance.fooddelivery.data.repository.OrderRepositoryImpl
import com.radlance.fooddelivery.domain.repository.AuthorizationRepository
import com.radlance.fooddelivery.domain.repository.CatalogRepository
import com.radlance.fooddelivery.domain.repository.HistoryRepository
import com.radlance.fooddelivery.domain.repository.OrderRepository
import com.radlance.fooddelivery.presentation.core.ProvideRepository

class App : Application(), ProvideRepository {

    override fun authorizationRepository(): AuthorizationRepository {
        return AuthorizationRepositoryImpl(BaseService())
    }

    override fun catalogRepository(): CatalogRepository {
        val dao = DeliveryDatabase.newInstance(applicationContext).productsDao()
        return CatalogRepositoryImpl(BaseService(), dao)
    }

    override fun orderRepository(token: String?): OrderRepository {
        val dao = DeliveryDatabase.newInstance(applicationContext).productsDao()
        return OrderRepositoryImpl(BaseService(token), dao)
    }

    override fun historyRepository(token: String): HistoryRepository {
        return HistoryRepositoryImpl(BaseService(token))
    }
}