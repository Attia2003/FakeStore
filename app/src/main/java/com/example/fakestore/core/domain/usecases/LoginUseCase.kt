package com.example.fakestore.core.domain.usecases

import com.example.fakestore.core.data.dto.LoginResponse
import com.example.fakestore.core.domain.contract.loginRepository
import com.example.fakestore.core.data.dto.loginRequest

class LoginUseCase (private val repo: loginRepository
){
    suspend fun call(request: loginRequest) : LoginResponse {
        return repo.login(request)




    }
}