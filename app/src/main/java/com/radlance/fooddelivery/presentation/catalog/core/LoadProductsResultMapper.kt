package com.radlance.fooddelivery.presentation.catalog.core

import com.radlance.fooddelivery.domain.core.LoadProductsResult
import com.radlance.fooddelivery.domain.entity.Product

class LoadProductsResultMapper : LoadProductsResult.Mapper<LoadProductsState> {
    override fun mapSuccess(productList: List<Product>): LoadProductsState {
        return LoadProductsState.Success(productList)
    }

    override fun mapError(): LoadProductsState {
        return LoadProductsState.Error
    }
}