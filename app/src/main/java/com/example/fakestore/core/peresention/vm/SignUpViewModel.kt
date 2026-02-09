package com.example.fakestore.core.peresention.vm

import android.util.Log
import android.util.Patterns
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fakestore.core.data.dto.SignUpRequest
import com.example.fakestore.core.domain.usecases.SignUpUseCase
import com.example.fakestore.core.peresention.uistate.SignUpUiState
import com.example.fakestore.core.peresention.util.toUiError
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val signUpUseCase: SignUpUseCase
) : ViewModel() {


    private val _uiState = MutableStateFlow<SignUpUiState>(SignUpUiState.Idle)
    val uiState: StateFlow<SignUpUiState> = _uiState.asStateFlow()


    private val _name = MutableStateFlow("")
    val name: StateFlow<String> = _name.asStateFlow()

    private val _email = MutableStateFlow("")
    val email: StateFlow<String> = _email.asStateFlow()

    private val _password = MutableStateFlow("")
    val password: StateFlow<String> = _password.asStateFlow()

    private val _confirmPassword = MutableStateFlow("")
    val confirmPassword: StateFlow<String> = _confirmPassword.asStateFlow()


    private val _nameError = MutableStateFlow<String?>(null)
    val nameError: StateFlow<String?> = _nameError.asStateFlow()

    private val _emailError = MutableStateFlow<String?>(null)
    val emailError: StateFlow<String?> = _emailError.asStateFlow()

    private val _passwordError = MutableStateFlow<String?>(null)
    val passwordError: StateFlow<String?> = _passwordError.asStateFlow()

    private val _confirmPasswordError = MutableStateFlow<String?>(null)
    val confirmPasswordError: StateFlow<String?> = _confirmPasswordError.asStateFlow()


    private val _passwordVisible = MutableStateFlow(false)
    val passwordVisible: StateFlow<Boolean> = _passwordVisible.asStateFlow()

    private val _confirmPasswordVisible = MutableStateFlow(false)
    val confirmPasswordVisible: StateFlow<Boolean> = _confirmPasswordVisible.asStateFlow()


    fun onNameChange(newName: String) {
        _name.value = newName

        if (_nameError.value != null) {
            _nameError.value = null
        }
    }


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


    fun onConfirmPasswordChange(newConfirmPassword: String) {
        _confirmPassword.value = newConfirmPassword

        if (_confirmPasswordError.value != null) {
            _confirmPasswordError.value = null
        }
    }


    fun onPasswordVisibilityToggle() {
        _passwordVisible.value = !_passwordVisible.value
    }


    fun onConfirmPasswordVisibilityToggle() {
        _confirmPasswordVisible.value = !_confirmPasswordVisible.value
    }


    private fun validateName(name: String): String? {
        return when {
            name.isBlank() -> "Name is required"
            else -> null
        }
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


    private fun validateConfirmPassword(password: String, confirmPassword: String): String? {
        return when {
            confirmPassword.isBlank() -> "Please confirm your password"
            password != confirmPassword -> "Passwords do not match"
            else -> null
        }
    }


    private fun validateInputs(): Boolean {
        val nameValidation = validateName(_name.value)
        val emailValidation = validateEmail(_email.value)
        val passwordValidation = validatePassword(_password.value)
        val confirmPasswordValidation = validateConfirmPassword(_password.value, _confirmPassword.value)

        _nameError.value = nameValidation
        _emailError.value = emailValidation
        _passwordError.value = passwordValidation
        _confirmPasswordError.value = confirmPasswordValidation

        return nameValidation == null && 
               emailValidation == null && 
               passwordValidation == null && 
               confirmPasswordValidation == null
    }


    fun onSignUpClick() {
        if (validateInputs()) {
            signUp(_name.value, _email.value, _password.value)
        }
    }


    private fun signUp(
        name: String,
        email: String,
        password: String,
        avatar: String = "https://api.lorem.space/image/face?w=640&h=480"
    ) {
        viewModelScope.launch {
            _uiState.value = SignUpUiState.Loading
            try {
                val request = SignUpRequest(
                    name = name,
                    email = email,
                    password = password,
                    avatar = avatar
                )
                val response = signUpUseCase.call(request)
                _uiState.value = SignUpUiState.Success(response)
                Log.d("SignUp", "User created: $response")
            } catch (e: Exception) {
                Log.d("SignUpError", "Error: ${e.message}")
                _uiState.value = SignUpUiState.Error(e.toUiError())
            }
        }
    }


    fun resetState() {
        _uiState.value = SignUpUiState.Idle
    }
}
