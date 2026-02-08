package com.example.fakestore.core.di.usecasemodule

import com.example.fakestore.core.domain.contract.productRepository
import com.example.fakestore.core.domain.usecases.ProductUseCaase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)

object UseCaseModule{
    @Provides
    fun ProvideGetProductUseCase(repo : productRepository) : ProductUseCaase =
        ProductUseCaase(repo)


}

