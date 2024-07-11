package com.radlance.fooddelivery.presentation.catalog.core

import com.radlance.fooddelivery.domain.core.LoadResult
import com.radlance.fooddelivery.domain.entity.Product

class LoadResultMapper : LoadResult.Mapper<LoadState> {
    override fun mapSuccess(productList: List<Product>): LoadState {
        return LoadState.Success(productList)
    }

    override fun mapError(): LoadState {
        return LoadState.Error
    }
}