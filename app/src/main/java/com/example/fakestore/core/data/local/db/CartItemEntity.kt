package com.example.fakestore.core.data.local.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.compose.runtime.Immutable

@Entity(tableName = "cart_items")
@Immutable
data class CartItemEntity(

    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val productId: Int,
    val title: String,
    val price: Double,
    val imageUrl: String,
    val quantity: Int,
    val addedAt: Long = System.currentTimeMillis()
)
