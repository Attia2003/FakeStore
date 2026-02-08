package com.example.fakestore.core.di.usecasemodule

import com.example.fakestore.core.domain.contract.AddProductRepository
import com.example.fakestore.core.domain.usecases.AddProductUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)

object AddProductUseCaseModule {
    @Provides
    fun provideAddProductUseCase(repo: AddProductRepository): AddProductUseCase =
        AddProductUseCase(repo)
}
