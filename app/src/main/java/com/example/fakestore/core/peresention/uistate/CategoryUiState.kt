package com.example.fakestore.core.peresention.uistate

import com.example.fakestore.core.data.dto.CategoryDto

sealed class CategoryUiState {
    object Idle : CategoryUiState()

    object Loading : CategoryUiState()

    data class Success(val categories: List<CategoryDto>) : CategoryUiState()

    data class Error(val error: UiError) : CategoryUiState()
}
