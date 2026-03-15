package com.example.fakestore.core.domain.contract

import com.example.fakestore.core.data.dto.getProducts

interface CategoryByIdRepository {
    suspend fun getProductsByCategory(id: Int): List<getProducts>
}
