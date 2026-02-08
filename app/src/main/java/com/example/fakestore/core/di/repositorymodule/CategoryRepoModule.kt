package com.example.fakestore.core.di.repositorymodule

import com.example.fakestore.core.data.repository.CategoryRepoImpl
import com.example.fakestore.core.domain.contract.CategoryRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class CategoryRepoModule {

    @Binds
    abstract fun bindCategoryRepository(
        impl: CategoryRepoImpl
    ): CategoryRepository
}
