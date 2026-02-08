package com.example.fakestore.core.domain.usecases

import com.example.fakestore.core.domain.contract.productRepository

class ProductUseCaase( private val repo : productRepository){
    suspend fun call() = repo.getAllProducts()
}