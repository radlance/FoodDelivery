package com.radlance.fooddelivery.presentation.order

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.radlance.fooddelivery.domain.entity.CartItem
import com.radlance.fooddelivery.domain.usecase.order.GetFullCartItemInfoUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

class OrderViewModel(private val getFullCartItemInfoUseCase: GetFullCartItemInfoUseCase) :
    ViewModel() {
    private val viewModelScope = CoroutineScope(SupervisorJob() + Dispatchers.Main.immediate)

    private val _orderList = MutableLiveData<List<CartItem>>()
    val orderList: LiveData<List<CartItem>>
        get() = _orderList

    fun getFullCartItemInfo() {
        viewModelScope.launch {
            _orderList.value = getFullCartItemInfoUseCase()
        }
    }
}