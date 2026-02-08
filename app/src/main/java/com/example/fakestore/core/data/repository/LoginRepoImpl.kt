package com.example.fakestore.core.data.repository

import com.example.fakestore.core.data.dto.LoginResponse
import com.example.fakestore.core.data.dto.loginRequest
import com.example.fakestore.core.data.remote.ApiService
import com.example.fakestore.core.domain.contract.loginRepository
import javax.inject.Inject

class LoginRepoImpl @Inject constructor(
    val api: ApiService
): loginRepository {
    override suspend fun login(requsest: loginRequest): LoginResponse {
        return api.login(requsest)
    }
}