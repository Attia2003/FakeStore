package com.example.fakestore.core.domain.usecases

import com.example.fakestore.core.data.dto.CreateProductRequest
import com.example.fakestore.core.data.dto.CreateProductResponse
import com.example.fakestore.core.domain.contract.AddProductRepository

class AddProductUseCase(
    private val repo: AddProductRepository
) {
    suspend fun call(request: CreateProductRequest): CreateProductResponse {
        return repo.createProduct(request)
    }
}
