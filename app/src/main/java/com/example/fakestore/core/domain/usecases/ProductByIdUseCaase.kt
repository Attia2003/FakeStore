package com.example.fakestore.core.domain.usecases

import com.example.fakestore.core.domain.contract.productByIdRepository

class ProductByIdUseCaase (private  val repo : productByIdRepository){
    suspend fun call(id: Int) = repo.getProductByID(id)
}