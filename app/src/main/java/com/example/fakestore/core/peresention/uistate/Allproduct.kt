package com.example.fakestore.core.peresention.uistate

import androidx.compose.runtime.Immutable
import com.example.fakestore.core.data.dto.getProducts

sealed interface ProductUiState{

    data object Idle : ProductUiState
    data object Loading : ProductUiState
    @Immutable
    data class Success(val products: List<getProducts>) : ProductUiState
    data class Error(val eror : UiError) : ProductUiState

}