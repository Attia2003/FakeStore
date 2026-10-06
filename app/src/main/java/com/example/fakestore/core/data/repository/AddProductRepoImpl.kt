package com.example.fakestore.core.data.repository

import com.example.fakestore.core.data.dto.createProductRequest
import com.example.fakestore.core.data.dto.createProductResponse
import com.example.fakestore.core.data.remote.ApiService
import com.example.fakestore.core.domain.contract.AddProductRepository
import javax.inject.Inject

class AddProductRepoImpl
    @Inject
    constructor(
        private val api: ApiService,
    ) : AddProductRepository {
        override suspend fun createProduct(request: createProductRequest): createProductResponse = api.createProduct(request)
    }
