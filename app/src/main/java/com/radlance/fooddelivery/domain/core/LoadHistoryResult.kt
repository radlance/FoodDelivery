package com.radlance.fooddelivery.domain.core

import com.radlance.fooddelivery.domain.entity.HistoryItem

interface LoadHistoryResult {
    fun <T : Any> map(mapper: Mapper<T>): T

    interface Mapper<T : Any> {
        fun mapSuccess(history: List<HistoryItem>): T
        fun mapError(unauthorized: Boolean): T
    }
    data class Success(val history: List<HistoryItem>) : LoadHistoryResult {
        override fun <T : Any> map(mapper: Mapper<T>): T {
            return mapper.mapSuccess(history)
        }
    }

    data class Error(val unauthorized: Boolean): LoadHistoryResult {
        override fun <T : Any> map(mapper: Mapper<T>): T {
            return mapper.mapError(unauthorized)
        }
    }
}