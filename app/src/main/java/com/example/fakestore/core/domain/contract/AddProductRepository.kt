package com.example.fakestore.core.domain.contract

import com.example.fakestore.core.data.dto.CreateProductRequest
import com.example.fakestore.core.data.dto.CreateProductResponse

interface AddProductRepository {
    suspend fun createProduct(request: CreateProductRequest): CreateProductResponse
}
