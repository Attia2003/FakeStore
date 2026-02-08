package com.example.fakestore.core.peresention.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fakestore.core.data.dto.loginRequest
import com.example.fakestore.core.peresention.uistate.LoginUiState
import com.example.fakestore.core.peresention.uistate.UiError
import com.example.fakestore.core.domain.usecases.LoginUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<LoginUiState>(LoginUiState.Idle)
    val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()

    fun login(email: String, password: String) {
        viewModelScope.launch {
            _uiState.value = LoginUiState.Loading
            try {
                val request = loginRequest(
                    email = email,
                    password = password
                )
                val response = loginUseCase.call(request)
                _uiState.value = LoginUiState.Success(response)
            } catch (e: IOException) {
                _uiState.value = LoginUiState.Error(UiError.NoInternet)
            } catch (e: HttpException) {

                val error = when (e.code()) {
                    400 -> UiError.BadRequest
                    401 -> UiError.InvalidCredentials
                    404 -> UiError.UserNotFound
                    in 500..599 -> UiError.ServerError
                    else -> UiError.Http(e.code())
                }
                _uiState.value = LoginUiState.Error(error)
            } catch (e: Exception) {
                _uiState.value = LoginUiState.Error(UiError.Unknown)
            }
        }
    }

    fun resetState() {
        _uiState.value = LoginUiState.Idle
    }
}