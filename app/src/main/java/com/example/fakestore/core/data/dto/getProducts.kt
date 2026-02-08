package com.example.fakestore.core.data.dto

data class getProducts(
    val id: Int,
    val description : String?=null,
    val title :String?=null,
    val price : Long,
    val category: Category,
    val images: List<String>?=null
)

data class Category (
       val  id: Int,
       val name :String



    )