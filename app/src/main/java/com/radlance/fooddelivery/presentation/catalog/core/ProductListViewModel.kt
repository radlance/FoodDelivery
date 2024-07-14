package com.radlance.fooddelivery.presentation.catalog.core

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.radlance.fooddelivery.domain.core.LoadProductsResult
import com.radlance.fooddelivery.domain.entity.CartItem
import com.radlance.fooddelivery.domain.entity.Product
import com.radlance.fooddelivery.domain.usecase.catalog.AddCartItemUseCase
import com.radlance.fooddelivery.domain.usecase.catalog.GetLocalProductsUseCase
import com.radlance.fooddelivery.domain.usecase.catalog.GetProductByCategoryUseCase
import com.radlance.fooddelivery.domain.usecase.catalog.LoadProductsUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

class ProductListViewModel(
    private val loadProductsUseCase: LoadProductsUseCase,
    private val getLocalProductsUseCase: GetLocalProductsUseCase,
    private val getProductByCategoryUseCase: GetProductByCategoryUseCase,
    private val addCartItemUseCase: AddCartItemUseCase,

    private val mapper: LoadProductsResult.Mapper<LoadProductsState>
) : ViewModel() {
    private val viewModelScope = CoroutineScope(SupervisorJob() + Dispatchers.Main.immediate)

    private val _loadState = MutableLiveData<LoadProductsState>()

    val loadState: LiveData<LoadProductsState>
        get() = _loadState

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
                _loadState.value = LoadProductsState.Success(localProducts)
            }
        }
    }

    fun getProductsByCategory(categoryName: String) {
        viewModelScope.launch {

            if (getLocalProductsUseCase().isEmpty()) {
                if (loadProductsUseCase() is LoadProductsResult.Success) {
                    val localProductsByCategory = getProductByCategoryUseCase(categoryName).map {
                        Product(
                            it.product.id,
                            it.product.title,
                            it.product.price,
                            it.product.imageUrl,
                            it.product.categoryId
                        )
                    }
                    _loadState.value = LoadProductsState.Success(localProductsByCategory)
                } else {
                    _loadState.value = LoadProductsState.Error
                }
            } else {
                val localProductsByCategory = getProductByCategoryUseCase(categoryName).map {
                    Product(
                        it.product.id,
                        it.product.title,
                        it.product.price,
                        it.product.imageUrl,
                        it.product.categoryId
                    )
                }
                _loadState.value = LoadProductsState.Success(localProductsByCategory)
            }
        }
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