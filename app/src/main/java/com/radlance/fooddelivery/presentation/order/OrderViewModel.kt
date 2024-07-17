package com.radlance.fooddelivery.presentation.order

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.radlance.fooddelivery.domain.core.DeliveryResult
import com.radlance.fooddelivery.domain.entity.CartItem
import com.radlance.fooddelivery.domain.entity.Delivery
import com.radlance.fooddelivery.domain.usecase.order.ClearCartUseCase
import com.radlance.fooddelivery.domain.usecase.order.CreateDeliveryUseCase
import com.radlance.fooddelivery.domain.usecase.order.DeleteCartItemUseCase
import com.radlance.fooddelivery.domain.usecase.order.GetFullCartItemInfoUseCase
import com.radlance.fooddelivery.domain.usecase.order.GetTotalOrderCostUseCase
import com.radlance.fooddelivery.domain.usecase.order.UpdateCartItemUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

class OrderViewModel(
    private val getFullCartItemInfoUseCase: GetFullCartItemInfoUseCase,
    private val updateCartItemUseCase: UpdateCartItemUseCase,
    private val getTotalOrderCostUseCase: GetTotalOrderCostUseCase,
    private val deleteCartItemUseCase: DeleteCartItemUseCase,
    private val createDeliveryUseCase: CreateDeliveryUseCase,
    private val clearCartUseCase: ClearCartUseCase,
    private val mapper: DeliveryResult.Mapper<DeliveryState>
) :
    ViewModel() {
    private val viewModelScope = CoroutineScope(SupervisorJob() + Dispatchers.Main.immediate)

    private val _orderState = MutableLiveData<OrdersState>()
    val orderState: LiveData<OrdersState>
        get() = _orderState

    private val _updatedCartItem = MutableLiveData<CartItem>()
    val updatedCartItem: LiveData<CartItem>
        get() = _updatedCartItem

    private val _totalOrderCost = MutableLiveData<Int>()
    val totalOrderCost: LiveData<Int>
        get() = _totalOrderCost

    private val _deliveryResult = MutableLiveData<DeliveryState>()
    val deliveryResult: LiveData<DeliveryState>
        get() = _deliveryResult

    private val _addressInputError = MutableLiveData<Boolean>()
    val addressInputError: LiveData<Boolean>
        get() = _addressInputError

    fun getFullCartItemInfo() {
        viewModelScope.launch {
            _addressInputError.value = false
            val orderList = getFullCartItemInfoUseCase()
            if (orderList.isEmpty()) {
                _orderState.value = OrdersState.Empty
            } else {
                _orderState.value = OrdersState.Loaded(orderList)
                _totalOrderCost.value = getTotalOrderCostUseCase().toInt()
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
            _totalOrderCost.value = getTotalOrderCostUseCase().toInt()
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
            _totalOrderCost.value = getTotalOrderCostUseCase().toInt()
        }
    }

    fun deleteCartItem(cartItem: CartItem) {
        viewModelScope.launch {
            deleteCartItemUseCase(cartItem)
            getFullCartItemInfo()
        }
    }

    fun clearCart() {
        viewModelScope.launch {
            clearCartUseCase()
            getFullCartItemInfo()
        }
    }

    fun createDelivery(address: String, order: List<CartItem>) {
        viewModelScope.launch {
            val regex = "^\\w+( \\w+)*\\s*,\\s*[1-9]?[0-9]$".toRegex()

            if (!address.matches(regex)
            ) {
                _addressInputError.value = true
            } else {
                val addressList = address.split(",").map { it.trim() }
                _deliveryResult.value = DeliveryState.Loading
                val creatingResult = createDeliveryUseCase(
                    Delivery(
                        street = addressList[0],
                        house = addressList[1].toInt(),
                        products = order
                    )
                )
                _deliveryResult.value = creatingResult.map(mapper)
            }
        }
    }
}