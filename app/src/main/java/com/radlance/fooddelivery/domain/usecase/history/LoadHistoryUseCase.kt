package com.radlance.fooddelivery.domain.usecase.history

import com.radlance.fooddelivery.domain.core.LoadHistoryResult
import com.radlance.fooddelivery.domain.repository.HistoryRepository

class LoadHistoryUseCase(private val historyRepository: HistoryRepository) {
    suspend operator fun invoke(): LoadHistoryResult {
        return historyRepository.loadHistory()
    }
}