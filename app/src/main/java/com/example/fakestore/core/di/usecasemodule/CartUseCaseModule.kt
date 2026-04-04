package com.example.fakestore.core.di.usecasemodule

import com.example.fakestore.core.domain.contract.CartRepository
import com.example.fakestore.core.domain.usecases.AddToCartUseCase
import com.example.fakestore.core.domain.usecases.ClearExpiredCartUseCase
import com.example.fakestore.core.domain.usecases.GetCartItemsUseCase
import com.example.fakestore.core.domain.usecases.RemoveFromCartUseCase
import com.example.fakestore.core.domain.usecases.UpdateCartQuantityUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object CartUseCaseModule {

    @Provides
    fun provideGetCartItemsUseCase(repo: CartRepository): GetCartItemsUseCase =
        GetCartItemsUseCase(repo)

    @Provides
    fun provideAddToCartUseCase(repo: CartRepository): AddToCartUseCase =
        AddToCartUseCase(repo)

    @Provides
    fun provideUpdateCartQuantityUseCase(repo: CartRepository): UpdateCartQuantityUseCase =
        UpdateCartQuantityUseCase(repo)

    @Provides
    fun provideRemoveFromCartUseCase(repo: CartRepository): RemoveFromCartUseCase =
        RemoveFromCartUseCase(repo)

    @Provides
    fun provideClearExpiredCartUseCase(repo: CartRepository): ClearExpiredCartUseCase =
        ClearExpiredCartUseCase(repo)
}
