package com.example.fakestore.core.peresention.uistate

import com.example.fakestore.core.data.local.db.CartItemEntity

sealed interface CartUiState {
    data object Idle : CartUiState
    data object Loading : CartUiState
    data class Success(val items: List<CartItemEntity>) : CartUiState
    data class Error(val error: UiError) : CartUiState
}
