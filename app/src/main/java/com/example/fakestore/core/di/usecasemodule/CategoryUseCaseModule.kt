package com.example.fakestore.core.di.usecasemodule

import com.example.fakestore.core.domain.contract.CategoryRepository
import com.example.fakestore.core.domain.usecases.CategoryUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object CategoryUseCaseModule {
    
    @Provides
    fun provideCategoryUseCase(repo: CategoryRepository): CategoryUseCase =
        CategoryUseCase(repo)
}
