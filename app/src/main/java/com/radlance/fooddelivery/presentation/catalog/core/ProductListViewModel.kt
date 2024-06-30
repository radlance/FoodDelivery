package com.radlance.fooddelivery.presentation.catalog.core

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.radlance.fooddelivery.domain.core.LoadResult
import com.radlance.fooddelivery.domain.entity.CartItem
import com.radlance.fooddelivery.domain.entity.Product
import com.radlance.fooddelivery.domain.usecase.catalog.AddToCartUseCase
import com.radlance.fooddelivery.domain.usecase.catalog.GetLocalProductsUseCase
import com.radlance.fooddelivery.domain.usecase.catalog.GetProductByCategoryUseCase
import com.radlance.fooddelivery.domain.usecase.catalog.GetProductsUseCase
import com.radlance.fooddelivery.domain.usecase.catalog.SaveProductsUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

class ProductListViewModel(
    private val getProductsUseCase: GetProductsUseCase,
    private val saveProductsUseCase: SaveProductsUseCase,
    private val getLocalProductsUseCase: GetLocalProductsUseCase,
    private val getProductByCategoryUseCase: GetProductByCategoryUseCase,
    private val addToCartUseCase: AddToCartUseCase
) : ViewModel() {
    private val viewModelScope = CoroutineScope(SupervisorJob() + Dispatchers.Main.immediate)

    private val _loadState = MutableLiveData<LoadResult>()

    private val _localProducts = MutableLiveData<List<Product>>()
    val localProducts: LiveData<List<Product>>
        get() = _localProducts

    val loadState: LiveData<LoadResult>
        get() = _loadState

    private val _openedProductDetails = MutableLiveData<Product>()
    val openedProductDetails: LiveData<Product>
        get() = _openedProductDetails

    private val _shouldCloseDetails = MutableLiveData<Boolean>()
    val shouldCloseDetails: LiveData<Boolean>
        get() = _shouldCloseDetails

    private val _productCount = MutableLiveData<Int>()
    val productCount: LiveData<Int>
        get() = _productCount


    fun getProducts() {
        viewModelScope.launch {
            val localProducts = getLocalProductsUseCase()
            if (localProducts.isEmpty()) {
                _loadState.value = getProductsUseCase()
            } else {
                _localProducts.value = localProducts
            }
        }
    }

    fun saveProducts(productList: List<Product>) {
        viewModelScope.launch {
            saveProductsUseCase(productList)
        }
    }

    fun getProductsByCategory(categoryName: String) {
        viewModelScope.launch {
            val localProducts = getLocalProductsUseCase()
            if (localProducts.isEmpty()) {
                _loadState.value = getProductsUseCase()
            } else {
                _loadState.value = LoadResult.Success()
                _localProducts.value = getProductByCategoryUseCase(categoryName)
            }
        }
    }

    fun showDetails(product: Product) {
        _openedProductDetails.value = product
        _shouldCloseDetails.value = false
    }

    fun closeDetails() {
        _shouldCloseDetails.value = true
    }

    fun incrementCount() {
        val newCount = _productCount.value ?: 1
        if (newCount < 99) {
            _productCount.value = newCount + 1
        }
    }

    fun decrementCount() {
        val newCount = _productCount.value ?: 1
        if (newCount > 1) {
            _productCount.value = newCount - 1
        }
    }

    fun addToCart(count: String, product: Product) {
        viewModelScope.launch {
            addToCartUseCase(CartItem(count.toInt(), product))
        }
    }
}