package com.example.fakestore.core.domain.contract

import com.example.fakestore.core.data.dto.LoginResponse
import com.example.fakestore.core.data.dto.loginRequest

interface loginRepository {

    suspend fun login(requsest : loginRequest): LoginResponse
}