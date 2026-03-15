package com.example.fakestore.core.di.repositorymodule

import com.example.fakestore.core.data.repository.CategoryByIdRepoImpl
import com.example.fakestore.core.domain.contract.CategoryByIdRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class CategoryByIdRepoModule {

    @Binds
    abstract fun bindCategoryByIdRepository(
        impl: CategoryByIdRepoImpl
    ): CategoryByIdRepository
}
