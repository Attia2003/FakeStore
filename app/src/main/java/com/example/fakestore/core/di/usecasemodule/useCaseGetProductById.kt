package com.example.fakestore.core.di.usecasemodule

import com.example.fakestore.core.domain.contract.productByIdRepository
import com.example.fakestore.core.domain.usecases.ProductByIdUseCaase
import com.example.fakestore.core.domain.usecases.ProductUseCaase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)


object useCaseGetProductById {
    @Provides
    fun  provideusecaseproductbyid(repo: productByIdRepository) : ProductByIdUseCaase
    = ProductByIdUseCaase(repo)
}