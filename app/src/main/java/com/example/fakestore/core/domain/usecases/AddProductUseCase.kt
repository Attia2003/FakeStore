package com.example.fakestore.core.domain.usecases

import com.example.fakestore.core.data.dto.createProductRequest
import com.example.fakestore.core.data.dto.createProductResponse
import com.example.fakestore.core.domain.contract.AddProductRepository

class AddProductUseCase(
    private val repo: AddProductRepository,
) {
    suspend fun call(request: createProductRequest): createProductResponse = repo.createProduct(request)
}
