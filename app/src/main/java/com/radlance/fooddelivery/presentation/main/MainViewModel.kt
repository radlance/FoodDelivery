package com.radlance.fooddelivery.presentation.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {
    private val _navigationState = MutableLiveData<NavigationState>()
    val navigationState: LiveData<NavigationState>
        get() = _navigationState

    fun updateNavigationState(state: NavigationState) {
        _navigationState.value = state
    }
}