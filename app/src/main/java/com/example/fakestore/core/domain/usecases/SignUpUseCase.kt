package com.example.fakestore.core.domain.usecases

import com.example.fakestore.core.data.dto.SignUpRequest
import com.example.fakestore.core.data.dto.SignUpResponse
import com.example.fakestore.core.domain.contract.SignUpRepository

class SignUpUseCase(
    private val repo: SignUpRepository
) {
    suspend fun call(request: SignUpRequest): SignUpResponse {
        return repo.signUp(request)
    }
}
