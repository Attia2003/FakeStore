package com.example.fakestore.core.di.repositorymodule

import com.example.fakestore.core.data.repository.productrRepoImpl
import com.example.fakestore.core.domain.contract.productRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)

abstract class ProductRepoImplmodule {


    @Binds
    abstract fun bindProductRepository(
        impl: productrRepoImpl
    ): productRepository

}