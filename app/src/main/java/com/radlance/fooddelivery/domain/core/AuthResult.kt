package com.radlance.fooddelivery.domain.core

interface AuthResult {
    fun <T : Any> map(mapper: Mapper<T>): T

    interface Mapper<T : Any> {
        fun mapSuccess(token: String): T
        fun mapError(userAlreadyExist: Boolean): T
    }

    data class Success(val token: String = "") : AuthResult {
        override fun <T : Any> map(mapper: Mapper<T>): T {
            return mapper.mapSuccess(token)
        }
    }

    data class Error(val userAlreadyExist: Boolean = false) : AuthResult {
        override fun <T : Any> map(mapper: Mapper<T>): T {
            return mapper.mapError(userAlreadyExist)
        }
    }
}