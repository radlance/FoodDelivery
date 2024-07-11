package com.radlance.fooddelivery.domain.core

import com.radlance.fooddelivery.domain.entity.Product

interface LoadResult {
    fun <T : Any> map(mapper: Mapper<T>): T

    interface Mapper<T : Any> {
        fun mapSuccess(productList: List<Product>): T
        fun mapError(): T
    }

    class Success(val productList: List<Product> = emptyList()) : LoadResult {
        override fun <T : Any> map(mapper: Mapper<T>): T {
            return mapper.mapSuccess(productList)
        }
    }

    object Error : LoadResult {
        override fun <T : Any> map(mapper: Mapper<T>): T {
            return mapper.mapError()
        }
    }
}