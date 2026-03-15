package com.example.fakestore.core.data.repository

import com.example.fakestore.core.data.dto.getProducts
import com.example.fakestore.core.data.remote.ApiService
import com.example.fakestore.core.domain.contract.CategoryByIdRepository
import javax.inject.Inject

class CategoryByIdRepoImpl @Inject constructor(
    private val api: ApiService
) : CategoryByIdRepository {
    override suspend fun getProductsByCategory(id: Int): List<getProducts> {
        return api.getProductsByCategory(id)
    }
}
