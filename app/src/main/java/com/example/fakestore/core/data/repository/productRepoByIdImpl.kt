package com.example.fakestore.core.data.repository

import com.example.fakestore.core.data.dto.CategoryBYid
import com.example.fakestore.core.data.dto.getproductbyid
import com.example.fakestore.core.data.remote.ApiService
import com.example.fakestore.core.domain.contract.productByIdRepository
import javax.inject.Inject

class productRepoByIdImpl @Inject constructor(
    private val api: ApiService
) : productByIdRepository {

    override suspend fun getProductByID(id: Int): getproductbyid {
       return api.getproductByID(id)

    }

}
