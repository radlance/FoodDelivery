package com.radlance.fooddelivery.presentation.order

import com.radlance.fooddelivery.domain.core.DeliveryResult

class DeliveryResultMapper : DeliveryResult.Mapper<DeliveryState> {
    override fun mapSuccess(): DeliveryState {
        return DeliveryState.Success
    }

    override fun mapError(unauthorized: Boolean): DeliveryState {
        return DeliveryState.Error(unauthorized)
    }
}