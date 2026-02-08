package com.example.fakestore.core.data.repository

import com.example.fakestore.core.data.dto.SignUpRequest
import com.example.fakestore.core.data.dto.SignUpResponse
import com.example.fakestore.core.data.remote.ApiService
import com.example.fakestore.core.domain.contract.SignUpRepository
import javax.inject.Inject

class SignUpRepoImpl @Inject constructor(
    private val api: ApiService
) : SignUpRepository {
    override suspend fun signUp(request: SignUpRequest): SignUpResponse {
        return api.signUp(request)
    }
}
