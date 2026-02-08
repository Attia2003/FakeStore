package com.example.fakestore.core.domain.contract

import com.example.fakestore.core.data.dto.SignUpRequest
import com.example.fakestore.core.data.dto.SignUpResponse

interface SignUpRepository {
    suspend fun signUp(request: SignUpRequest): SignUpResponse
}
