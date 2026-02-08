package com.example.fakestore.core.data.dto

data class CreateProductRequest(
    val title: String,
    val price: Long,
    val description: String,
    val categoryId: Int,
    val images: List<String>
)

data class CreateProductResponse(
    val id: Int,
    val title: String,
    val price: Long,
    val description: String,
    val category: Category,
    val images: List<String>
)
