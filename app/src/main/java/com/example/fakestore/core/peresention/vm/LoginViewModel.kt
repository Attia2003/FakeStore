package com.example.fakestore.core.peresention.vm

import android.util.Patterns
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


    private val _email = MutableStateFlow("")
    val email: StateFlow<String> = _email.asStateFlow()

    private val _password = MutableStateFlow("")
    val password: StateFlow<String> = _password.asStateFlow()


    private val _emailError = MutableStateFlow<String?>(null)
    val emailError: StateFlow<String?> = _emailError.asStateFlow()

    private val _passwordError = MutableStateFlow<String?>(null)
    val passwordError: StateFlow<String?> = _passwordError.asStateFlow()


    private val _passwordVisible = MutableStateFlow(false)
    val passwordVisible: StateFlow<Boolean> = _passwordVisible.asStateFlow()


    fun onEmailChange(newEmail: String) {
        _email.value = newEmail

        if (_emailError.value != null) {
            _emailError.value = null
        }
    }


    fun onPasswordChange(newPassword: String) {
        _password.value = newPassword

        if (_passwordError.value != null) {
            _passwordError.value = null
        }
    }


    fun onPasswordVisibilityToggle() {
        _passwordVisible.value = !_passwordVisible.value
    }


    private fun validateEmail(email: String): String? {
        return when {
            email.isBlank() -> "Email is required"
            !Patterns.EMAIL_ADDRESS.matcher(email).matches() -> "Invalid email format"
            else -> null
        }
    }

    private fun validatePassword(password: String): String? {
        return when {
            password.isBlank() -> "Password is required"
            password.length < 6 -> "Password must be at least 6 characters"
            else -> null
        }
    }


    private fun validateInputs(): Boolean {
        val emailValidation = validateEmail(_email.value)
        val passwordValidation = validatePassword(_password.value)

        _emailError.value = emailValidation
        _passwordError.value = passwordValidation

        return emailValidation == null && passwordValidation == null
    }


    fun onLoginClick() {
        if (validateInputs()) {
            login(_email.value, _password.value)
        }
    }


    private fun login(email: String, password: String) {
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