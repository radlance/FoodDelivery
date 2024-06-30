package com.radlance.fooddelivery.presentation.catalog.core

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewmodel.CreationExtras
import com.radlance.fooddelivery.domain.usecase.catalog.SearchProductsLikeNameUseCase
import com.radlance.fooddelivery.presentation.core.ProvideRepository

class CatalogViewModelFactory : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
        val application = extras[APPLICATION_KEY]
        val catalogRepository = (application as ProvideRepository).catalogRepository()
        val searchProductsLikeNameUseCase = SearchProductsLikeNameUseCase(catalogRepository)
        return CatalogViewModel(searchProductsLikeNameUseCase) as T
    }
}