package com.example.fakestore.core.di.repositorymodule

import com.example.fakestore.core.data.repository.LoginRepoImpl
import com.example.fakestore.core.domain.contract.loginRepository
import com.example.fakestore.core.domain.usecases.LoginUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LoginRepoModule {

    @Provides
    @Singleton
    fun provideLoginRepository(impl: LoginRepoImpl): loginRepository = impl

    @Provides
    @Singleton
    fun provideLoginUseCase(repo: loginRepository): LoginUseCase {
        return LoginUseCase(repo)
    }



}