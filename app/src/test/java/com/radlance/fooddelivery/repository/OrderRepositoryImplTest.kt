package com.radlance.fooddelivery.repository

import com.radlance.fooddelivery.data.api.core.Service
import com.radlance.fooddelivery.data.database.DeliveryDao
import com.radlance.fooddelivery.data.repository.OrderRepositoryImpl
import com.radlance.fooddelivery.domain.core.DeliveryResult
import com.radlance.fooddelivery.domain.entity.Delivery
import com.radlance.fooddelivery.domain.repository.OrderRepository
import com.radlance.fooddelivery.domain.usecase.order.CreateDeliveryUseCase
import kotlinx.coroutines.runBlocking
import okhttp3.ResponseBody.Companion.toResponseBody
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.kotlin.any
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import retrofit2.HttpException
import retrofit2.Response

/**
 * for other methods
 * @see com.radlance.fooddelivery.RoomTest
 */

class OrderRepositoryImplTest {
    private lateinit var service: Service
    private lateinit var dao: DeliveryDao
    private lateinit var repository: OrderRepository

    private lateinit var delivery: Delivery

    @BeforeEach
    fun setup(): Unit = runBlocking {
        service = mock()
        dao = mock()

        repository = OrderRepositoryImpl(service, dao)

        delivery = Delivery(street = "street1", house = 1, products = emptyList())
    }

    @Test
    fun test_create_delivery_success(): Unit = runBlocking {
        whenever(service.createDelivery(any())).thenReturn(Unit)
        val createDeliveryUseCase = CreateDeliveryUseCase(repository)

        assertThat(createDeliveryUseCase(delivery)).isEqualTo(DeliveryResult.Success)
    }

    @Test
    fun test_create_delivery_unauthorized_error(): Unit = runBlocking {
        whenever(service.createDelivery(any())).thenThrow(
            HttpException(
                Response.error<Any>(401, "".toResponseBody())
            )
        )
        val createDeliveryUseCase = CreateDeliveryUseCase(repository)

        assertThat(createDeliveryUseCase(delivery)).isEqualTo(DeliveryResult.Error(unauthorized = true))
    }

    @Test
    fun test_create_delivery_service_error(): Unit = runBlocking {
        whenever(service.createDelivery(any())).thenThrow(HttpException::class.java)
        val createDeliveryUseCase = CreateDeliveryUseCase(repository)

        assertThat(createDeliveryUseCase(delivery)).isEqualTo(DeliveryResult.Error(unauthorized = false))
    }

    @Test
    fun test_create_delivery_runtime_error(): Unit = runBlocking {
        whenever(service.createDelivery(any())).thenThrow(RuntimeException::class.java)
        val createDeliveryUseCase = CreateDeliveryUseCase(repository)

        assertThat(createDeliveryUseCase(delivery)).isEqualTo(DeliveryResult.Error(unauthorized = false))
    }
}