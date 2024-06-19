package com.radlance.fooddelivery.presentation.main

import com.radlance.fooddelivery.domain.usecase.main.GetProductsUseCase
import com.radlance.fooddelivery.domain.usecase.main.SaveProductsUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

class ProductListViewModel(
    getProductListUseCase: GetProductsUseCase,
    saveProductListUseCase: SaveProductsUseCase
) {
    private val viewModelScope = CoroutineScope(SupervisorJob() + Dispatchers.Main.immediate)

    fun getProductList() {
        viewModelScope.launch {

        }
    }
}