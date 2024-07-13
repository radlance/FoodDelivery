package com.radlance.fooddelivery.presentation.order

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.radlance.fooddelivery.domain.entity.CartItem
import com.radlance.fooddelivery.domain.usecase.order.GetFullCartItemInfoUseCase
import com.radlance.fooddelivery.domain.usecase.order.UpdateCartItemUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

class OrderViewModel(
    private val getFullCartItemInfoUseCase: GetFullCartItemInfoUseCase,
    private val updateCartItemUseCase: UpdateCartItemUseCase
) :
    ViewModel() {
    private val viewModelScope = CoroutineScope(SupervisorJob() + Dispatchers.Main.immediate)

    private val _orderState = MutableLiveData<OrdersState>()
    val orderState: LiveData<OrdersState>
        get() = _orderState

    private val _updatedCartItem = MutableLiveData<CartItem>()
    val updatedCartItem: LiveData<CartItem>
        get() = _updatedCartItem

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

    fun incrementCount(cartItem: CartItem) {
        val currentCount = cartItem.count
        if (currentCount < 99) {
            cartItem.count += 1
        }
        _updatedCartItem.value = cartItem

        viewModelScope.launch {
            updateCartItemUseCase(cartItem)
        }
    }

    fun decrementCount(cartItem: CartItem) {
        val currentCount = cartItem.count
        if (currentCount > 1) {
            cartItem.count -= 1
        }
        _updatedCartItem.value = cartItem

        viewModelScope.launch {
            updateCartItemUseCase(cartItem)
        }
    }
}