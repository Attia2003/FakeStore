package com.example.fakestore.core.di.repositorymodule

import com.example.fakestore.core.data.repository.productRepoByIdImpl
import com.example.fakestore.core.data.repository.productrRepoImpl
import com.example.fakestore.core.domain.contract.productByIdRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)

abstract class ProductRepoIdImplmodule {


    @Binds
    abstract fun bindProductRepository(
        impl: productRepoByIdImpl
    ): productByIdRepository

}

