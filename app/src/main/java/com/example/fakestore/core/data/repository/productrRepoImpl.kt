package com.example.fakestore.core.data.repository

import com.example.fakestore.core.domain.contract.productRepository
import com.example.fakestore.core.data.dto.Category
import com.example.fakestore.core.data.dto.getProducts
import com.example.fakestore.core.data.remote.ApiService
import javax.inject.Inject

class productrRepoImpl @Inject constructor(val api: ApiService) : productRepository {
    override suspend fun getAllProducts(): List<getProducts> {
        return api.getAllProducts()



        }

    }
