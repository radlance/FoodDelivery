package com.radlance.fooddelivery.domain.core

import com.radlance.fooddelivery.domain.entity.User

interface LoadResult {
    class Success : LoadResult

    data class Error(private val error: Boolean) : LoadResult
}