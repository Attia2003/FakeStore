package com.example.fakestore.core.data.dto

data class SignUpResponse(
    val id: Int,
    val email: String,
    val name: String,
    val avatar: String,
    val creationAt: String,
    val updatedAt: String
)
