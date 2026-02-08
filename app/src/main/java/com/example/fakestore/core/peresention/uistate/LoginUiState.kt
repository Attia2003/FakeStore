package com.example.fakestore.core.peresention.uistate

import com.example.fakestore.core.data.dto.LoginResponse

sealed class LoginUiState {
     data object Idle : LoginUiState()
     data object Loading : LoginUiState()
     data class Success(val user: LoginResponse) : LoginUiState()
     data class Error(val error: UiError) : LoginUiState()


}