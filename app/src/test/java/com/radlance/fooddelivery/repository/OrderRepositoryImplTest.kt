package com.radlance.fooddelivery.repository

import com.radlance.fooddelivery.data.api.core.Service
import com.radlance.fooddelivery.data.database.DeliveryDao
import com.radlance.fooddelivery.data.repository.OrderRepositoryImpl
import com.radlance.fooddelivery.domain.core.DeliveryResult
import com.radlance.fooddelivery.domain.entity.Delivery
import com.radlance.fooddelivery.domain.repository.OrderRepository
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
    fun testCreateDeliverySuccess(): Unit = runBlocking {
        whenever(service.createDelivery(any())).thenReturn(Unit)
        val result = repository.createDelivery(delivery)

        assertThat(result).isEqualTo(DeliveryResult.Success)
    }

    @Test
    fun testCreateDeliveryUnauthorizedError(): Unit = runBlocking {
        whenever(service.createDelivery(any())).thenThrow(
            HttpException(
                Response.error<Any>(401, "".toResponseBody())
            )
        )
        val result = repository.createDelivery(delivery)

        assertThat(result).isEqualTo(DeliveryResult.Error(unauthorized = true))
    }

    @Test
    fun testCreateDeliveryServiceError(): Unit = runBlocking {
        whenever(service.createDelivery(any())).thenThrow(HttpException::class.java)
        val result = repository.createDelivery(delivery)

        assertThat(result).isEqualTo(DeliveryResult.Error(unauthorized = false))
    }

    @Test
    fun testCreateDeliveryRuntimeError(): Unit = runBlocking {
        whenever(service.createDelivery(any())).thenThrow(RuntimeException::class.java)
        val result = repository.createDelivery(delivery)

        assertThat(result).isEqualTo(DeliveryResult.Error(unauthorized = false))
    }
}