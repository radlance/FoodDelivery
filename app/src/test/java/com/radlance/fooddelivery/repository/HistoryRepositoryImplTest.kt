package com.radlance.fooddelivery.repository

import com.radlance.fooddelivery.data.api.core.Service
import com.radlance.fooddelivery.data.api.response.HistoryResponse
import com.radlance.fooddelivery.data.repository.HistoryRepositoryImpl
import com.radlance.fooddelivery.domain.core.LoadHistoryResult
import com.radlance.fooddelivery.domain.entity.HistoryItem
import com.radlance.fooddelivery.domain.repository.HistoryRepository
import com.radlance.fooddelivery.domain.usecase.history.LoadHistoryUseCase
import kotlinx.coroutines.runBlocking
import okhttp3.ResponseBody.Companion.toResponseBody
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import retrofit2.HttpException
import retrofit2.Response

class HistoryRepositoryImplTest {
    private lateinit var service: Service
    private lateinit var repository: HistoryRepository

    @BeforeEach
    fun setup() {
        service = mock()
        repository = HistoryRepositoryImpl(service)
    }

    @Test
    fun test_load_history_success(): Unit = runBlocking {
        val history = listOf(
            HistoryResponse(
                id = 1,
                orderTime = "now",
                street = "street1",
                house = 1,
                productDeliveries = emptyList()
            )
        )

        whenever(service.history()).thenReturn(history)

        val loadHistoryUseCase = LoadHistoryUseCase(repository)
        val historyResult = history.map {
            HistoryItem(
                id = it.id,
                orderTime = it.orderTime,
                street = it.street,
                house = it.house,
                productDeliveries = emptyList()
            )
        }

        assertThat(loadHistoryUseCase()).isEqualTo(LoadHistoryResult.Success(historyResult))
    }

    @Test
    fun test_load_history_unauthorized_error(): Unit = runBlocking {
        whenever(service.history()).thenThrow(
            HttpException(
                Response.error<Any>(401, "".toResponseBody())
            )
        )
        val loadHistoryUseCase = LoadHistoryUseCase(repository)
        assertThat(loadHistoryUseCase()).isEqualTo(LoadHistoryResult.Error(unauthorized = true))
    }

    @Test
    fun test_load_history_service_error(): Unit = runBlocking {
        whenever(service.history()).thenThrow(HttpException::class.java)

        val loadHistoryUseCase = LoadHistoryUseCase(repository)
        assertThat(loadHistoryUseCase()).isEqualTo(LoadHistoryResult.Error(unauthorized = false))
    }

    @Test
    fun test_load_history_other_error(): Unit = runBlocking {
        whenever(service.history()).thenThrow(RuntimeException::class.java)

        val loadHistoryUseCase = LoadHistoryUseCase(repository)
        assertThat(loadHistoryUseCase()).isEqualTo(LoadHistoryResult.Error(unauthorized = false))
    }
}