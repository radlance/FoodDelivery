package com.radlance.fooddelivery.presentation.catalog.core

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.radlance.fooddelivery.domain.core.LoadResult
import com.radlance.fooddelivery.domain.entity.CartItem
import com.radlance.fooddelivery.domain.entity.Product
import com.radlance.fooddelivery.domain.usecase.catalog.AddCartItemUseCase
import com.radlance.fooddelivery.domain.usecase.catalog.GetLocalProductsUseCase
import com.radlance.fooddelivery.domain.usecase.catalog.GetProductByCategoryUseCase
import com.radlance.fooddelivery.domain.usecase.catalog.LoadProductsUseCase
import com.radlance.fooddelivery.domain.usecase.catalog.SaveProductsUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

class ProductListViewModel(
    private val loadProductsUseCase: LoadProductsUseCase,
    private val saveProductsUseCase: SaveProductsUseCase,
    private val getLocalProductsUseCase: GetLocalProductsUseCase,
    private val getProductByCategoryUseCase: GetProductByCategoryUseCase,
    private val addCartItemUseCase: AddCartItemUseCase,

    private val mapper: LoadResult.Mapper<LoadState>
) : ViewModel() {
    private val viewModelScope = CoroutineScope(SupervisorJob() + Dispatchers.Main.immediate)

    private val _loadState = MutableLiveData<LoadState>()

    val loadState: LiveData<LoadState>
        get() = _loadState

    private val _localProducts = MutableLiveData<List<Product>>()
    val localProducts: LiveData<List<Product>>
        get() = _localProducts

    private val _detailsState = MutableLiveData<DetailsState>()
    val openedProductDetails: LiveData<DetailsState>
        get() = _detailsState

    private val _actionsState = MutableLiveData<MoreActionsState>()
    val actionsState: MutableLiveData<MoreActionsState>
        get() = _actionsState

    private val _productCount = MutableLiveData<Int>()
    val productCount: LiveData<Int>
        get() = _productCount


    fun getProducts() {
        viewModelScope.launch {
            val localProducts = getLocalProductsUseCase()
            if (localProducts.isEmpty()) {
                val loadResult = loadProductsUseCase()
                _loadState.value = loadResult.map(mapper)
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
            val loadResult = loadProductsUseCase()
            if (localProducts.isEmpty()) {
                _loadState.value = loadResult.map(mapper)
            } else {
                _localProducts.value = getProductByCategoryUseCase(categoryName).map {
                    Product(
                        it.product.id,
                        it.product.title,
                        it.product.price,
                        it.product.imageUrl,
                        it.product.categoryId
                    )
                }
            }
        }
        //TODO сделать загрузку по категорим после retry
    }

    fun showDetails(product: Product) {
        _detailsState.value = DetailsState.Opened(product)
    }

    fun showActions() {
        _actionsState.value = MoreActionsState.More
    }

    fun closeDetails() {
        _detailsState.value = DetailsState.Closed
        _actionsState.value = MoreActionsState.Less
        _productCount.value = 1
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
            addCartItemUseCase(CartItem(count.toInt(), product))
            _actionsState.value = MoreActionsState.More
        }
    }
}