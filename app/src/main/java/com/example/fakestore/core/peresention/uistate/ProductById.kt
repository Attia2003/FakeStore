package com.example.fakestore.core.peresention.uistate

import com.example.fakestore.core.data.dto.getproductbyid
import java.util.Objects

sealed interface ProductByIdUiState{
    data object Idle : ProductByIdUiState

    data object Loading : ProductByIdUiState

    data class Success(val product: getproductbyid): ProductByIdUiState

    data class Error(val error: UiError) : ProductByIdUiState

}