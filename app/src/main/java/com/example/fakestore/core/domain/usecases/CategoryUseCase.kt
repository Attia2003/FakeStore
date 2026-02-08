package com.example.fakestore.core.domain.usecases

import com.example.fakestore.core.domain.contract.CategoryRepository
import javax.inject.Inject

class CategoryUseCase @Inject constructor(
    private val repo: CategoryRepository
) {
    suspend fun call() = repo.getAllCategories()
}
