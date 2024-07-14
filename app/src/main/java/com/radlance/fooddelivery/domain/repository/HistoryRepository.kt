package com.radlance.fooddelivery.domain.repository

import com.radlance.fooddelivery.domain.core.LoadHistoryResult

interface HistoryRepository {
    suspend fun loadHistory(): LoadHistoryResult
}