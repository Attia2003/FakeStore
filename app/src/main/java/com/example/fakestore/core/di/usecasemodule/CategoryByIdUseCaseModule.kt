package com.example.fakestore.core.di.usecasemodule

import com.example.fakestore.core.domain.contract.CategoryByIdRepository
import com.example.fakestore.core.domain.usecases.CategoryByIdUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object CategoryByIdUseCaseModule {

    @Provides
    fun provideCategoryByIdUseCase(repo: CategoryByIdRepository): CategoryByIdUseCase =
        CategoryByIdUseCase(repo)
}
