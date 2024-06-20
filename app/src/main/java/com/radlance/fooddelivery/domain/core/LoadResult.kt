package com.radlance.fooddelivery.domain.core

import com.radlance.fooddelivery.domain.entity.Product

interface LoadResult {
    class Success(val productList: List<Product> = emptyList()) : LoadResult
    object Error : LoadResult
}