package com.example.fakestore.core.domain.contract

import com.example.fakestore.core.data.dto.createProductRequest
import com.example.fakestore.core.data.dto.createProductResponse

interface AddProductRepository {
    suspend fun createProduct(request: createProductRequest): createProductResponse
}
