package com.example.fakestore.core.di.repositorymodule

import com.example.fakestore.core.data.repository.AddProductRepoImpl
import com.example.fakestore.core.domain.contract.AddProductRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)

abstract class AddProductRepoModule {

    @Binds
    abstract fun bindAddProductRepository(
        impl: AddProductRepoImpl
    ): AddProductRepository

}
