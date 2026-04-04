package com.example.fakestore.core.domain.usecases

import com.example.fakestore.core.data.local.db.CartItemEntity
import com.example.fakestore.core.domain.contract.CartRepository
import kotlinx.coroutines.flow.Flow

class GetCartItemsUseCase(private val repo: CartRepository) {

    fun call(): Flow<List<CartItemEntity>> = repo.getCartItems()
}

class AddToCartUseCase(private val repo: CartRepository) {

    suspend fun call(item: CartItemEntity): Unit = repo.addToCart(item)
}

class UpdateCartQuantityUseCase(private val repo: CartRepository) {

    suspend fun call(productId: Int, quantity: Int): Unit = repo.updateQuantity(productId, quantity)
}

class RemoveFromCartUseCase(private val repo: CartRepository) {

    suspend fun call(id: Int): Unit = repo.removeFromCart(id)
}

class ClearExpiredCartUseCase(private val repo: CartRepository) {

    suspend fun call(): Unit = repo.clearExpired()
}