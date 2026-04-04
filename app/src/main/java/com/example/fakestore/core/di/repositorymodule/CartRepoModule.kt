package com.example.fakestore.core.di.repositorymodule

import com.example.fakestore.core.data.repository.CartRepoImpl
import com.example.fakestore.core.domain.contract.CartRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class CartRepoModule {

    @Binds
    abstract fun bindCartRepository(impl: CartRepoImpl): CartRepository
}
