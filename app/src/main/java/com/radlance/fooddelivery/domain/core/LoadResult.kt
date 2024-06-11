package com.radlance.fooddelivery.domain.core

interface LoadResult {
    class Success(val token: String = "") : LoadResult
    data class Error(val error: Boolean) : LoadResult
}