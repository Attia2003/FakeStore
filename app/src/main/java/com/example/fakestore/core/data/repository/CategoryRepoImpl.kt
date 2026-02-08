package com.example.fakestore.core.data.repository

import com.example.fakestore.core.data.dto.CategoryDto
import com.example.fakestore.core.data.remote.ApiService
import com.example.fakestore.core.domain.contract.CategoryRepository
import javax.inject.Inject

class CategoryRepoImpl @Inject constructor(
    private val api: ApiService
) : CategoryRepository {
    override suspend fun getAllCategories(): List<CategoryDto> {
        return api.getAllCategories()
    }
}
