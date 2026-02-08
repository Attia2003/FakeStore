package com.example.fakestore.core.peresention.vm

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fakestore.core.data.dto.SignUpRequest
import com.example.fakestore.core.domain.usecases.SignUpUseCase
import com.example.fakestore.core.peresention.uistate.SignUpUiState
import com.example.fakestore.core.peresention.uistate.UiError
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val signUpUseCase: SignUpUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<SignUpUiState>(SignUpUiState.Idle)
    val uiState: StateFlow<SignUpUiState> = _uiState

    fun signUp(
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
            } catch (ioe: IOException) {
                Log.d("SignUpError", "IO Error: ${ioe.message}")
                _uiState.value = SignUpUiState.Error(UiError.NoInternet)
            } catch (e: HttpException) {
                Log.d("SignUpError", "HTTP Error: ${e.code()}")
                _uiState.value = SignUpUiState.Error(UiError.Http(e.code()))
            } catch (e: Exception) {
                Log.d("SignUpError", "Unknown Error: ${e.message}")
                _uiState.value = SignUpUiState.Error(UiError.Unknown)
            }
        }
    }

    fun resetState() {
        _uiState.value = SignUpUiState.Idle
    }
}
