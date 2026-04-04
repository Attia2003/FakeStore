package com.example.fakestore.core.data.local.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface CartDao {

    @Query("SELECT * FROM cart_items ORDER BY addedAt DESC")
    fun getAll(): Flow<List<CartItemEntity>>

    @Query("SELECT * FROM cart_items WHERE productId = :productId LIMIT 1")
    suspend fun getByProductId(productId: Int): CartItemEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: CartItemEntity): Unit

    @Update
    suspend fun update(item: CartItemEntity): Unit

    @Delete
    suspend fun delete(item: CartItemEntity): Unit

    @Query("DELETE FROM cart_items WHERE id = :id")
    suspend fun deleteById(id: Int): Unit

    @Query("DELETE FROM cart_items WHERE addedAt < :threshold")
    suspend fun deleteExpired(threshold: Long): Unit
}