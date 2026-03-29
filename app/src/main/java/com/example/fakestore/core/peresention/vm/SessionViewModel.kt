package com.example.fakestore.core.peresention.vm

import androidx.lifecycle.ViewModel
import com.example.fakestore.core.data.local.TokenManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject


@HiltViewModel
class SessionViewModel @Inject constructor(
    tokenManager: TokenManager
) : ViewModel() {

    private val _isLoggedIn = MutableStateFlow(tokenManager.isLoggedIn())
    val isLoggedIn: StateFlow<Boolean> = _isLoggedIn.asStateFlow()
}
