package com.example.fakestore.core.domain.contract

import com.example.fakestore.core.data.dto.getproductbyid

interface productByIdRepository {
    suspend fun getProductByID(id: Int): getproductbyid
}


