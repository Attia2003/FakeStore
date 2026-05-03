package com.example.fakestore.core.domain.contract


import com.example.fakestore.core.data.dto.getproductbyid

interface CategoryByIdRepository {
    suspend fun getProductsByCategory(id: Int): List<getproductbyid>
}
