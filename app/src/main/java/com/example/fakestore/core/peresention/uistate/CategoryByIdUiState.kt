package com.example.fakestore.core.peresention.uistate

import com.example.fakestore.core.data.dto.getProducts

sealed interface CategoryByIdUiState {
    data object Idle : CategoryByIdUiState
    data object Loading : CategoryByIdUiState
    data class Success(val products: List<getProducts>) : CategoryByIdUiState
    data class Error(val error: UiError) : CategoryByIdUiState
}
