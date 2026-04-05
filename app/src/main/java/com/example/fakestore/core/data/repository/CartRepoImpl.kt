package com.example.fakestore.core.data.repository

import com.example.fakestore.core.data.local.db.CartDao
import com.example.fakestore.core.data.local.db.CartItemEntity
import com.example.fakestore.core.domain.contract.CartRepository
import kotlinx.coroutines.flow.Flow
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class CartRepoImpl @Inject constructor(private val dao: CartDao) : CartRepository {

    override fun getCartItems(): Flow<List<CartItemEntity>> = dao.getAll()

    override suspend fun addToCart(item: CartItemEntity) {
        val existing = dao.getByProductId(item.productId)
        if (existing != null) {
            dao.update(existing.copy(quantity = existing.quantity + 1))
        } else {
            dao.insert(item)
        }
    }

    override suspend fun updateQuantity(productId: Int, quantity: Int) {
        val existing = dao.getByProductId(productId) ?: return
        dao.update(existing.copy(quantity = quantity))
    }



    override suspend fun removeFromCart(id: Int) {
        dao.deleteById(id)
    }

    override suspend fun clearExpired() {
        val threshold = System.currentTimeMillis() - TimeUnit.HOURS.toMillis(4)
        dao.deleteExpired(threshold)
    }
}
