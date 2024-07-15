package com.radlance.fooddelivery.domain.core

interface AuthResult {
    fun <T : Any> map(mapper: Mapper<T>): T

    interface Mapper<T : Any> {
        fun mapSuccess(token: String): T
        fun mapLoading(): T
        fun mapError(): T
    }

    class Success(val token: String = "") : AuthResult {
        override fun <T : Any> map(mapper: Mapper<T>): T {
            return mapper.mapSuccess(token)
        }
    }

    object Error : AuthResult {
        override fun <T : Any> map(mapper: Mapper<T>): T {
            return mapper.mapError()
        }
    }
}