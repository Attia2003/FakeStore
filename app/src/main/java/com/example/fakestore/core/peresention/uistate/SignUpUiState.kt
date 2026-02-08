package com.example.fakestore.core.peresention.uistate

import com.example.fakestore.core.data.dto.SignUpResponse

sealed class SignUpUiState {
    data object Idle : SignUpUiState()
    data object Loading : SignUpUiState()
    data class Success(val user: SignUpResponse) : SignUpUiState()
    data class Error(val error: UiError) : SignUpUiState()
}
