package com.example.fakestore.core.data.remote

import com.example.fakestore.core.data.dto.CategoryDto
import com.example.fakestore.core.data.dto.CreateProductRequest
import com.example.fakestore.core.data.dto.CreateProductResponse
import com.example.fakestore.core.data.dto.LoginResponse
import com.example.fakestore.core.data.dto.SignUpRequest
import com.example.fakestore.core.data.dto.SignUpResponse

import com.example.fakestore.core.data.dto.getProducts
import com.example.fakestore.core.data.dto.getproductbyid
import com.example.fakestore.core.data.dto.loginRequest
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("products")
    suspend fun getAllProducts(
        @Query("offset") offset: Int,
        @Query("limit") limit: Int
    ): List<getProducts>

    @GET("products/{id}")
    suspend fun getproductByID(@Path("id") id: Int): getproductbyid

    @POST("products")
    suspend fun createProduct(@Body request: CreateProductRequest): CreateProductResponse

    @GET("categories")
    suspend fun getAllCategories(): List<CategoryDto>

    @GET("categories/{id}/products")
    suspend fun getProductsByCategory(@Path("id") id: Int): List<getProducts>

    @POST("users/")
    suspend fun signUp(@Body request: SignUpRequest): SignUpResponse

    @POST("auth/login")
    suspend fun login(@Body request: loginRequest): LoginResponse




}


