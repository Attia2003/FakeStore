package com.example.fakestore.core.data.dto

data class getproductbyid(
    val id: Int,
    val description : String?=null,
    val title :String?=null,
    val price : Int,
    val category: CategoryBYid,
    val images: List<String>?=null,
    val slug: String?=null,

    )

data class CategoryBYid(
    val  id: Int,
    val name :String,
    val slug: String,
    val image: String?=null,

)
