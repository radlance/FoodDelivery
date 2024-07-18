package com.radlance.fooddelivery.presentation.history

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.radlance.fooddelivery.domain.entity.CartItem
import com.radlance.fooddelivery.domain.usecase.order.ClearCartUseCase
import com.radlance.fooddelivery.domain.usecase.order.RepeatOrderUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

class CurrentHistoryViewModel(
    private val repeatOrderUseCase: RepeatOrderUseCase,
    private val clearCartUseCase: ClearCartUseCase,
) : ViewModel() {

    private val _moveToCart = MutableLiveData<Unit>()
    val moveToCart: LiveData<Unit>
        get() = _moveToCart

    private val viewModelScope = CoroutineScope(SupervisorJob() + Dispatchers.Main.immediate)
    fun repeatOrder(order: List<CartItem>) {
        viewModelScope.launch {
            clearCartUseCase()
            _moveToCart.value = repeatOrderUseCase(order)
        }
    }
}