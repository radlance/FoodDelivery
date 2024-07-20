package com.radlance.fooddelivery.repository

import com.radlance.fooddelivery.data.api.core.Service
import com.radlance.fooddelivery.data.api.response.CategoryResponse
import com.radlance.fooddelivery.data.api.response.ProductResponse
import com.radlance.fooddelivery.data.database.DeliveryDao
import com.radlance.fooddelivery.data.repository.CatalogRepositoryImpl
import com.radlance.fooddelivery.domain.core.LoadProductsResult
import com.radlance.fooddelivery.domain.entity.Product
import com.radlance.fooddelivery.domain.repository.CatalogRepository
import kotlinx.coroutines.runBlocking
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import retrofit2.HttpException

/**
 * for other methods
 * @see com.radlance.fooddelivery.RoomTest
 */

class CatalogRepositoryImplTest {
    private lateinit var service: Service
    private lateinit var repository: CatalogRepository
    private lateinit var dao: DeliveryDao

    @BeforeEach
    fun setup() {
        service = mock()
        dao = mock()
        repository = CatalogRepositoryImpl(service, dao)
    }

    @Test
    fun testLoadProductsSuccess(): Unit = runBlocking {
        val categories = listOf(
            CategoryResponse(id = 1, title = "Category 1")
        )

        val products = listOf(
            ProductResponse(
                id = 1,
                title = "Product 1",
                price = 10.0,
                imageUrl = "imageUrl1",
                category = categories[0]
            )
        )

        whenever(service.categories()).thenReturn(categories)
        whenever(service.products()).thenReturn(products)

        val result = repository.loadProducts()
        val productList = products.map {
            Product(it.id, it.title, it.price, it.imageUrl, it.category.id)
        }

        assertThat(result).isEqualTo(LoadProductsResult.Success(productList = productList))
    }

    @Test
    fun testLoadProductsError(): Unit = runBlocking {
        whenever(service.categories()).thenReturn(emptyList())
        whenever(service.products()).thenThrow(HttpException::class.java)

        assertThat(repository.loadProducts()).isEqualTo(LoadProductsResult.Error)
    }

    @Test
    fun testLoadCategoriesError(): Unit = runBlocking {
        whenever(service.categories()).thenThrow(HttpException::class.java)
        whenever(service.products()).thenReturn(emptyList())

        assertThat(repository.loadProducts()).isEqualTo(LoadProductsResult.Error)
    }
}