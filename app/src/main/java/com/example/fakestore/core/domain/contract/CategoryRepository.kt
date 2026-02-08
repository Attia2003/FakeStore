package com.example.fakestore.core.domain.contract

import com.example.fakestore.core.data.dto.CategoryDto

interface CategoryRepository {
    suspend fun getAllCategories(): List<CategoryDto>
}
