package com.example.fakestore.core.peresention.uistate

import com.example.fakestore.core.data.dto.createProductResponse

sealed interface AddProductUiState {
    data object Idle : AddProductUiState

    data object Loading : AddProductUiState

    data class Success(
        val product: createProductResponse,
    ) : AddProductUiState

    data class Error(
        val error: UiError,
    ) : AddProductUiState
}
