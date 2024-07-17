package com.radlance.fooddelivery.presentation.catalog.core

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.radlance.fooddelivery.domain.entity.Product
import com.radlance.fooddelivery.domain.usecase.catalog.SearchProductsLikeNameUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

class CatalogViewModel(private val searchProductsLikeNameUseCase: SearchProductsLikeNameUseCase) :
    ViewModel() {
    private val viewModelScope = CoroutineScope(SupervisorJob() + Dispatchers.Main.immediate)
    private val _searchResult = MutableLiveData<List<Product>>()
    val searchResult: LiveData<List<Product>>
        get() = _searchResult

    private val _shouldCloseSearch = MutableLiveData<CatalogState>()
    val shouldCloseSearch: LiveData<CatalogState>
        get() = _shouldCloseSearch

    fun searchProductsLikeName(query: String) {
        _shouldCloseSearch.value = if (query.isBlank()) {
            CatalogState.Hide
        } else {
            CatalogState.Show
        }
        viewModelScope.launch {
            _searchResult.value = searchProductsLikeNameUseCase(query)
        }
    }
}