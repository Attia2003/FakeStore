package com.example.fakestore.core.peresention.uistate

sealed interface UiError {
    data object NoInternet : UiError
    data class Http(val code: Int) : UiError
    data object InvalidCredentials : UiError
    data object UserNotFound : UiError
    data object BadRequest : UiError
    data object ServerError : UiError
    data object Unknown : UiError
}