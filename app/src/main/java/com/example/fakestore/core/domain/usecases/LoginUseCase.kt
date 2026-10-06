package com.example.fakestore.core.domain.usecases

import com.example.fakestore.core.data.dto.LoginResponse
import com.example.fakestore.core.data.dto.loginRequest
import com.example.fakestore.core.domain.contract.loginRepository

class LoginUseCase(
    private val repo: loginRepository,
) {
    suspend fun call(request: loginRequest): LoginResponse = repo.login(request)
}
