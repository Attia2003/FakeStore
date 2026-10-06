package com.example.fakestore.core.data.repository

import com.example.fakestore.core.data.dto.getProducts
import com.example.fakestore.core.data.remote.ApiService
import com.example.fakestore.core.domain.contract.productRepository
import javax.inject.Inject

class productrRepoImpl
    @Inject
    constructor(
        val api: ApiService,
    ) : productRepository {
        override suspend fun getAllProducts(
            offset: Int,
            limit: Int,
        ): List<getProducts> = api.getAllProducts(offset, limit)
    }
