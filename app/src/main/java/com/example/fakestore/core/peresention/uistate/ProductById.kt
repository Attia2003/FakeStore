package com.example.fakestore.core.peresention.uistate

import com.example.fakestore.core.data.dto.getproductbyid

sealed interface productByIdUiState {
    data object Idle : productByIdUiState

    data object Loading : productByIdUiState

    data class Success(
        val product: getproductbyid,
    ) : productByIdUiState

    data class Error(
        val error: UiError,
    ) : productByIdUiState
}
