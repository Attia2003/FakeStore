package com.example.fakestore.core.domain.contract

import com.example.fakestore.core.data.dto.getProducts

interface productRepository{
    suspend fun getAllProducts():List<getProducts>
}


