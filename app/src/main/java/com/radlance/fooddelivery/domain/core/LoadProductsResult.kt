package com.radlance.fooddelivery.domain.core

import com.radlance.fooddelivery.domain.entity.Product

interface LoadProductsResult {
    fun <T : Any> map(mapper: Mapper<T>): T

    interface Mapper<T : Any> {
        fun mapSuccess(productList: List<Product>): T
        fun mapError(): T
    }

    data class Success(private val productList: List<Product> = emptyList()) : LoadProductsResult {
        override fun <T : Any> map(mapper: Mapper<T>): T {
            return mapper.mapSuccess(productList)
        }
    }

    object Error : LoadProductsResult {
        override fun <T : Any> map(mapper: Mapper<T>): T {
            return mapper.mapError()
        }
    }
}