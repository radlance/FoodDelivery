package com.radlance.fooddelivery

import android.content.Context
import android.database.sqlite.SQLiteConstraintException
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.radlance.fooddelivery.data.database.CartItemCache
import com.radlance.fooddelivery.data.database.CategoryCache
import com.radlance.fooddelivery.data.database.DeliveryDao
import com.radlance.fooddelivery.data.database.DeliveryDatabase
import com.radlance.fooddelivery.data.database.ProductCache
import kotlinx.coroutines.runBlocking
import org.assertj.core.api.Assertions.*
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class RoomTest {
    private lateinit var db: DeliveryDatabase
    private lateinit var dao: DeliveryDao

    @Before
    fun setup() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, DeliveryDatabase::class.java).build()
        dao = db.deliveryDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    fun testSaveCategories(): Unit = runBlocking {
        dao.saveCategories(categoryCache = CATEGORIES)

        val savedCategories = CATEGORIES.map { dao.getCategoryById(it.id) }
        assertThat(savedCategories).isEqualTo(CATEGORIES)

        dao.saveCategories(categoryCache = CATEGORIES)
    }

    @Test(expected = SQLiteConstraintException::class)
    fun testSaveProductsConstraintForeignKeyException(): Unit = runBlocking {

        assertThat(dao.getProductsInfo()).isEmpty()
        dao.saveProducts(productList = BASE_PRODUCTS_CACHE)
    }

    @Test
    fun testSaveProducts(): Unit = runBlocking {
        dao.saveCategories(categoryCache = CATEGORIES)
        dao.saveProducts(productList = BASE_PRODUCTS_CACHE)

        assertThat(dao.getProductsInfo()).isEqualTo(BASE_PRODUCTS_CACHE)
    }

    @Test
    fun testGetProductsByCategory(): Unit = runBlocking {
        dao.saveCategories(categoryCache = CATEGORIES)

        val productsCacheWithFirstCategoryId = mutableListOf(
            ProductCache(
                id = 1,
                title = "Product 1",
                price = 10.0,
                imageUrl = "imageUrl1",
                categoryId = CATEGORIES[0].id
            )
        )
        assertThat(dao.getProductsByCategory(CATEGORIES[0].title)).isEmpty()

        dao.saveProducts(productList = productsCacheWithFirstCategoryId)
        assertThat(dao.getProductsByCategory(CATEGORIES[0].title)).hasSize(
            productsCacheWithFirstCategoryId.size
        )

        productsCacheWithFirstCategoryId.add(
            ProductCache(
                id = 2,
                title = "Product 2",
                price = 20.0,
                imageUrl = "imageUrl2",
                categoryId = CATEGORIES[0].id
            )
        )

        dao.saveProducts(productList = productsCacheWithFirstCategoryId)
        assertThat(dao.getProductsByCategory(CATEGORIES[0].title)).hasSize(
            productsCacheWithFirstCategoryId.size
        )

        val productCacheWithSecondCategoryId = listOf(
            ProductCache(
                id = 3,
                title = "Product 3",
                price = 30.0,
                imageUrl = "imageUrl3",
                categoryId = CATEGORIES[1].id
            ),
            ProductCache(
                id = 4,
                title = "Product 4",
                price = 40.0,
                imageUrl = "imageUrl4",
                categoryId = CATEGORIES[1].id
            ),
        )
        dao.saveProducts(productList = productCacheWithSecondCategoryId)
        assertThat(dao.getProductsByCategory(CATEGORIES[0].title)).hasSize(
            productsCacheWithFirstCategoryId.size
        )

        assertThat(dao.getProductsByCategory(CATEGORIES[1].title)).hasSize(
            productCacheWithSecondCategoryId.size
        )

        assertThat(dao.getProductsInfo()).hasSize(
            productsCacheWithFirstCategoryId.size + productCacheWithSecondCategoryId.size
        )

        productsCacheWithFirstCategoryId.forEachIndexed { index, productCache ->
            assertThat(productCache.id).isEqualTo(
                dao.getProductsByCategory(CATEGORIES[0].title)[index].product.id
            )
        }
    }


    @Test
    fun testSearchProductsLikeName(): Unit = runBlocking {
        assertThat(dao.getProductsInfo()).isEmpty()

        dao.saveCategories(categoryCache = CATEGORIES)

        val productsCache = mutableListOf(
            ProductCache(
                id = 1, title = "Hamburger", price = 10.0, imageUrl = "imageUrl1", categoryId = 1
            ),
            ProductCache(
                id = 2, title = "Lemonade", price = 20.0, imageUrl = "imageUrl2", categoryId = 2
            ),
            ProductCache(
                id = 3, title = "Cheeseburger", price = 30.0, imageUrl = "imageUrl3", categoryId = 2
            )
        )
        dao.saveProducts(productList = productsCache)

        val burgersItems = productsCache.filter { it.title.contains("burger") }
        val foundedItem = dao.searchProductsLikeName(query = "burger")
        assertThat(foundedItem).isEqualTo(burgersItems)

        val allItems = dao.searchProductsLikeName(query = "")
        assertThat(allItems).isEqualTo(productsCache)

        val nothingFound = dao.searchProductsLikeName(query = "someone else")
        assertThat(nothingFound).isEmpty()

        val capsFoundedItems = dao.searchProductsLikeName(query = "BURGER")
        assertThat(capsFoundedItems).isEqualTo(burgersItems)

        val capitalizeFoundedItems = dao.searchProductsLikeName(query = "Burger")
        assertThat(capitalizeFoundedItems).isEqualTo(burgersItems)

        productsCache.add(
            ProductCache(
                id = 4, title = "Hamburger", price = 40.0, imageUrl = "imageUrl4", categoryId = 2
            )
        )

        dao.saveProducts(productsCache)

        val hamburgers = productsCache.filter { it.title.contains("Hamburger") }
        val duplicateProduct = dao.searchProductsLikeName(query = "hamburger")
        assertThat(duplicateProduct).isEqualTo(hamburgers)
    }

    @Test(expected = SQLiteConstraintException::class)
    fun testAddCartItemConstraintForeignKeyException(): Unit = runBlocking {
        dao.addCartItem(cartItem = CartItemCache(productId = 1, count = 1))
    }

    @Test
    fun testAddCartItem(): Unit = runBlocking {

        assertThat(dao.getFullCartItemInfo()).isEmpty()

        dao.saveCategories(categoryCache = CATEGORIES)
        dao.saveProducts(productList = BASE_PRODUCTS_CACHE)

        val cartItems = listOf(
            CartItemCache(productId = 1, count = 1),
            CartItemCache(productId = 2, count = 1)
        )
        dao.addCartItem(cartItem = cartItems[0])
        dao.addCartItem(cartItem = cartItems[1])

        var savedCartItems = dao.getFullCartItemInfo()

        cartItems.forEachIndexed { index, cartItemCache ->
            assertThat(cartItemCache.productId).isEqualTo(savedCartItems[index].cartItem.productId)
            assertThat(cartItemCache.count).isEqualTo(savedCartItems[index].cartItem.count)
        }

        dao.addCartItem(cartItem = cartItems[1])
        savedCartItems = dao.getFullCartItemInfo()

        cartItems.forEachIndexed { index, cartItemCache ->
            assertThat(cartItemCache.productId).isEqualTo(savedCartItems[index].cartItem.productId)
            assertThat(cartItemCache.count).isEqualTo(savedCartItems[index].cartItem.count)
        }
    }

    @Test
    fun testUpdateCartItem(): Unit = runBlocking {

        dao.saveCategories(categoryCache = CATEGORIES)
        dao.saveProducts(productList = BASE_PRODUCTS_CACHE)

        val cartItems = mutableListOf(
            CartItemCache(productId = 1, count = 1),
            CartItemCache(productId = 2, count = 1)
        )

        dao.addCartItem(cartItem = cartItems[0])
        dao.addCartItem(cartItem = cartItems[1])

        cartItems[0] = CartItemCache(productId = 1, count = 2)
        dao.updateCartItem(cartItem = cartItems[0])

        val savedCartItems = dao.getFullCartItemInfo()

        cartItems.forEachIndexed { index, cartItemCache ->
            assertThat(cartItemCache.productId).isEqualTo(savedCartItems[index].cartItem.productId)
        }
    }

    @Test
    fun testGetProductCountById(): Unit = runBlocking {

        dao.saveCategories(categoryCache = CATEGORIES)
        dao.saveProducts(productList = BASE_PRODUCTS_CACHE)

        val cartItems = mutableListOf(
            CartItemCache(productId = 1, count = 1),
            CartItemCache(productId = 2, count = 1)
        )
        dao.addCartItem(cartItem = cartItems[0])
        dao.addCartItem(cartItem = cartItems[1])

        assertThat(dao.getProductCountById(cartItems[0].productId)).isEqualTo(cartItems[0].count)
        assertThat(dao.getProductCountById(cartItems[1].productId)).isEqualTo(cartItems[1].count)
    }

    @Test
    fun testGetTotalOrderCost(): Unit = runBlocking {

        dao.saveCategories(categoryCache = CATEGORIES)
        dao.saveProducts(productList = BASE_PRODUCTS_CACHE)

        val cartItems = mutableListOf(
            CartItemCache(productId = 1, count = 1),
            CartItemCache(productId = 2, count = 1)
        )

        dao.addCartItem(cartItem = cartItems[0])
        dao.addCartItem(cartItem = cartItems[1])

        assertThat(dao.getFullCartItemInfo()).hasSize(cartItems.size)
    }

    @Test
    fun testDeleteCartItem(): Unit = runBlocking {
        dao.saveCategories(categoryCache = CATEGORIES)
        dao.saveProducts(productList = BASE_PRODUCTS_CACHE)

        val cartItems = mutableListOf(
            CartItemCache(productId = 1, count = 1),
            CartItemCache(productId = 2, count = 1)
        )

        dao.addCartItem(cartItem = cartItems[0])
        dao.addCartItem(cartItem = cartItems[1])

        var savedCartItems = dao.getFullCartItemInfo()
        cartItems.forEachIndexed { index, cartItemCache ->
            assertThat(cartItemCache.productId).isEqualTo(savedCartItems[index].cartItem.productId)
            assertThat(cartItemCache.count).isEqualTo(savedCartItems[index].cartItem.count)
        }

        dao.deleteCartItem(cartItem = cartItems[0])
        cartItems.removeAt(index = 0)

        savedCartItems = dao.getFullCartItemInfo()
        cartItems.forEachIndexed { index, cartItemCache ->
            assertThat(cartItemCache.productId).isEqualTo(savedCartItems[index].cartItem.productId)
            assertThat(cartItemCache.count).isEqualTo(savedCartItems[index].cartItem.count)
        }

        dao.deleteCartItem(cartItem = cartItems[0])
        assertThat(dao.getFullCartItemInfo()).isEmpty()
    }

    @Test
    fun testClearCart(): Unit = runBlocking {
        dao.saveCategories(categoryCache = CATEGORIES)
        dao.saveProducts(productList = BASE_PRODUCTS_CACHE)

        val cartItems = mutableListOf(
            CartItemCache(productId = 1, count = 1),
            CartItemCache(productId = 2, count = 2),
            CartItemCache(productId = 3, count = 3)
        )

        for (item in cartItems) {
            dao.addCartItem(cartItem = item)
        }

        assertThat(dao.getFullCartItemInfo()).hasSize(cartItems.size)
        dao.clearCart()
        assertThat(dao.getFullCartItemInfo()).isEmpty()
    }

    @Test
    fun testRepeatOrder(): Unit = runBlocking {
        dao.saveCategories(categoryCache = CATEGORIES)
        dao.saveProducts(productList = BASE_PRODUCTS_CACHE)

        val cartItems = mutableListOf(
            CartItemCache(productId = 1, count = 1),
            CartItemCache(productId = 2, count = 2),
            CartItemCache(productId = 3, count = 3)
        )

        for (item in cartItems) {
            dao.addCartItem(cartItem = item)
        }

        assertThat(dao.getFullCartItemInfo()).hasSize(cartItems.size)
        dao.clearCart()
        assertThat(dao.getFullCartItemInfo()).isEmpty()

        dao.repeatOrder(cartItems)

        val savedCartItems = dao.getFullCartItemInfo()
        cartItems.forEachIndexed { index, cartItemCache ->
            assertThat(cartItemCache.productId).isEqualTo(savedCartItems[index].cartItem.productId)
            assertThat(cartItemCache.count).isEqualTo(savedCartItems[index].cartItem.count)
        }
    }


    companion object {
        private val CATEGORIES = listOf(
            CategoryCache(id = 1, title = "Category1"),
            CategoryCache(id = 2, title = "Category2"),
            CategoryCache(id = 3, title = "Category3")
        )

        private val BASE_PRODUCTS_CACHE = listOf(
            ProductCache(
                id = 1, title = "Product 1", price = 10.0, imageUrl = "imageUrl1", categoryId = 1
            ),
            ProductCache(
                id = 2, title = "Product 2", price = 20.0, imageUrl = "imageUrl2", categoryId = 2
            ),
            ProductCache(
                id = 3, title = "Product 3", price = 30.0, imageUrl = "imageUrl3", categoryId = 3
            )
        )
    }
}