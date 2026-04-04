package com.example.fakestore.core.domain.contract

import com.example.fakestore.core.data.local.db.CartItemEntity
import kotlinx.coroutines.flow.Flow

interface CartRepository {
    fun getCartItems(): Flow<List<CartItemEntity>>
    suspend fun addToCart(item: CartItemEntity): Unit
    suspend fun updateQuantity(productId: Int, quantity: Int): Unit
    suspend fun removeFromCart(id: Int): Unit
    suspend fun clearExpired(): Unit
}