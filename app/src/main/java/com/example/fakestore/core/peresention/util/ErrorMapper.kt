package com.example.fakestore.core.peresention.util

import com.example.fakestore.core.peresention.uistate.UiError
import retrofit2.HttpException
import java.io.IOException


object ErrorMapper {


    fun mapToUiError(exception: Exception): UiError {
        return when (exception) {
            is IOException -> UiError.NoInternet
            is HttpException -> mapHttpException(exception)
            else -> UiError.Unknown
        }
    }


    private fun mapHttpException(exception: HttpException): UiError {
        return when (exception.code()) {
            400 -> UiError.BadRequest
            401 -> UiError.InvalidCredentials
            404 -> UiError.UserNotFound
            in 500..599 -> UiError.ServerError
            else -> UiError.Http(exception.code())
        }
    }
}


fun Exception.toUiError(): UiError = ErrorMapper.mapToUiError(this)
