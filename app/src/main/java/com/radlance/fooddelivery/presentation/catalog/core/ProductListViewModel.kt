package com.radlance.fooddelivery.presentation.catalog.core

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.radlance.fooddelivery.domain.core.LoadResult
import com.radlance.fooddelivery.domain.entity.Product
import com.radlance.fooddelivery.domain.usecase.main.GetLocalProductsUseCase
import com.radlance.fooddelivery.domain.usecase.main.GetProductByCategoryUseCase
import com.radlance.fooddelivery.domain.usecase.main.GetProductsUseCase
import com.radlance.fooddelivery.domain.usecase.main.SaveProductsUseCase
import com.radlance.fooddelivery.domain.usecase.main.SearchProductsLikeNameUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

class ProductListViewModel(
    private val getProductsUseCase: GetProductsUseCase,
    private val saveProductsUseCase: SaveProductsUseCase,
    private val getLocalProductsUseCase: GetLocalProductsUseCase,
    private val getProductByCategoryUseCase: GetProductByCategoryUseCase,
    private val searchProductsLikeNameUseCase: SearchProductsLikeNameUseCase
) : ViewModel() {
    private val viewModelScope = CoroutineScope(SupervisorJob() + Dispatchers.Main.immediate)

    private val _loadState = MutableLiveData<LoadResult>()

    private val _localProducts = MutableLiveData<List<Product>>()
    val localProducts: LiveData<List<Product>>
        get() = _localProducts

    val loadState: LiveData<LoadResult>
        get() = _loadState
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

    fun searchProductsLikeName(query: String) {
        viewModelScope.launch {
            _localProducts.value = searchProductsLikeNameUseCase(query)
        }
    }
}