package com.example.fakestore.core.data.repository

import com.example.fakestore.core.data.dto.CreateProductRequest
import com.example.fakestore.core.data.dto.CreateProductResponse
import com.example.fakestore.core.data.remote.ApiService
import com.example.fakestore.core.domain.contract.AddProductRepository
import javax.inject.Inject

class AddProductRepoImpl @Inject constructor(
    private val api: ApiService
) : AddProductRepository {
    override suspend fun createProduct(request: CreateProductRequest): CreateProductResponse {
        return api.createProduct(request)
    }
}
