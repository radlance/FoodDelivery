package com.radlance.fooddelivery.data.repository

import com.radlance.fooddelivery.data.api.core.Service
import com.radlance.fooddelivery.domain.core.LoadHistoryResult
import com.radlance.fooddelivery.domain.entity.HistoryItem
import com.radlance.fooddelivery.domain.entity.Order
import com.radlance.fooddelivery.domain.repository.HistoryRepository
import retrofit2.HttpException

class HistoryRepositoryImpl(private val service: Service) : HistoryRepository {
    override suspend fun loadHistory(): LoadHistoryResult {
        return try {
            val history = service.history().map { response ->
                with(response) {
                    HistoryItem(
                        id,
                        orderTime,
                        house,
                        building,
                        apartment,
                        productDeliveries.map {
                            Order(
                                service.productById(it.productId), it.amount
                            )
                        }
                    )
                }
            }
            LoadHistoryResult.Success(history)
        } catch (e: HttpException) {
            LoadHistoryResult.Error(e.code() == 401)
        } catch (e: Exception) {
            LoadHistoryResult.Error(false)
        }
    }
}