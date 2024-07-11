package com.radlance.fooddelivery.presentation.order

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.radlance.fooddelivery.domain.usecase.order.GetFullCartItemInfoUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

class OrderViewModel(private val getFullCartItemInfoUseCase: GetFullCartItemInfoUseCase) :
    ViewModel() {
    private val viewModelScope = CoroutineScope(SupervisorJob() + Dispatchers.Main.immediate)

    private val _orderState = MutableLiveData<OrdersState>()
    val orderState: LiveData<OrdersState>
        get() = _orderState

    fun getFullCartItemInfo() {
        viewModelScope.launch {
            val orderList = getFullCartItemInfoUseCase()
            _orderState.value = if (orderList.isEmpty()) {
                OrdersState.Empty
            } else {
                OrdersState.Loaded(orderList)
            }
        }
    }
}