package com.example.fakestore.core.peresention.uistate

import com.example.fakestore.core.data.dto.getproductbyid


sealed interface CategoryByIdUiState {
    data object Idle : CategoryByIdUiState
    data object Loading : CategoryByIdUiState
    data class Success(val products: List<getproductbyid>) : CategoryByIdUiState
    data class Error(val error: UiError) : CategoryByIdUiState
}
