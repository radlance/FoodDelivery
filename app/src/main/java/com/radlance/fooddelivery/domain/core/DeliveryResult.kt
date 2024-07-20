package com.radlance.fooddelivery.domain.core

interface DeliveryResult {
    fun <T : Any> map(mapper: Mapper<T>): T
    interface Mapper<T : Any> {
        fun mapSuccess(): T
        fun mapError(unauthorized: Boolean): T
    }

    object Success : DeliveryResult {
        override fun <T : Any> map(mapper: Mapper<T>): T {
            return mapper.mapSuccess()
        }
    }

    data class Error(private val unauthorized: Boolean = false) : DeliveryResult {
        override fun <T : Any> map(mapper: Mapper<T>): T {
            return mapper.mapError(unauthorized)
        }
    }
}