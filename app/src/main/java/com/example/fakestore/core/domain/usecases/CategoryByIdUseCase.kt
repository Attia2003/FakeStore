package com.example.fakestore.core.domain.usecases

import com.example.fakestore.core.domain.contract.CategoryByIdRepository
import javax.inject.Inject

class CategoryByIdUseCase @Inject constructor(
    private val repo: CategoryByIdRepository
) {
    suspend fun call(id: Int) = repo.getProductsByCategory(id)
}
