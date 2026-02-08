package com.example.fakestore.core.di.repositorymodule

import com.example.fakestore.core.data.repository.SignUpRepoImpl
import com.example.fakestore.core.domain.contract.SignUpRepository
import com.example.fakestore.core.domain.usecases.SignUpUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object SignUpRepoModule {

    @Provides
    @Singleton
    fun provideSignUpRepository(impl: SignUpRepoImpl): SignUpRepository = impl

    @Provides
    @Singleton
    fun provideSignUpUseCase(repo: SignUpRepository): SignUpUseCase {
        return SignUpUseCase(repo)
    }
}
