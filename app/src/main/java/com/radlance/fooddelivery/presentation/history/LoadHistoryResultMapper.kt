package com.radlance.fooddelivery.presentation.history

import com.radlance.fooddelivery.domain.core.LoadHistoryResult
import com.radlance.fooddelivery.domain.entity.HistoryItem

class LoadHistoryResultMapper : LoadHistoryResult.Mapper<LoadHistoryState> {
    override fun mapSuccess(history: List<HistoryItem>): LoadHistoryState {
        return LoadHistoryState.Success(history)
    }

    override fun mapError(unauthorized: Boolean): LoadHistoryState {
        return LoadHistoryState.Error(unauthorized)
    }
}