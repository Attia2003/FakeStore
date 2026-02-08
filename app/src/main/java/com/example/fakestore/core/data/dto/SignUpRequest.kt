package com.example.fakestore.core.data.dto

data class SignUpRequest(
    val name: String,
    val email: String,
    val password: String,
    val avatar: String
)
