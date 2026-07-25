package com.example.fakestore.core.peresention.uistate

import androidx.compose.runtime.Immutable
import com.example.fakestore.core.data.local.db.CartItemEntity
import kotlinx.collections.immutable.ImmutableList

sealed interface CartUiState {
    data object Idle : CartUiState
    data object Loading : CartUiState
    @Immutable
    data class Success(val items: ImmutableList<CartItemEntity>) : CartUiState
    data class Error(val error: UiError) : CartUiState
}

sealed interface CartEvent {
    data class IncreaseQuantity(val item: CartItemEntity) : CartEvent
    data class DecreaseQuantity(val item: CartItemEntity) : CartEvent
    data class RemoveItem(val item: CartItemEntity) : CartEvent
    object Checkout : CartEvent
    object GoShopping : CartEvent
}
