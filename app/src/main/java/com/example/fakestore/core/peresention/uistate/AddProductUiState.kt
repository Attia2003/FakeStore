package com.example.fakestore.core.peresention.uistate

import com.example.fakestore.core.data.dto.CreateProductResponse

sealed interface AddProductUiState {
    data object Idle : AddProductUiState
    data object Loading : AddProductUiState
    data class Success(val product: CreateProductResponse) : AddProductUiState
    data class Error(val error: UiError) : AddProductUiState
}
