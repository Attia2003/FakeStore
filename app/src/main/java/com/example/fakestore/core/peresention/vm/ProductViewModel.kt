package com.example.fakestore.core.peresention.vm

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fakestore.core.data.dto.getProducts
import com.example.fakestore.core.domain.usecases.ProductUseCaase
import com.example.fakestore.core.peresention.uistate.ProductUiState
import com.example.fakestore.core.peresention.util.toUiError
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductViewModel @Inject constructor(private val getproduct : ProductUseCaase) : ViewModel() {

    private val _productstate = MutableStateFlow<ProductUiState>(ProductUiState.Idle)
    val productstate : StateFlow<ProductUiState> = _productstate

    private var currentOffset = 0
    private val limit = 10
    private var isPaginationExhausted = false
    private var isLoadingMore = false
    private val productsList = mutableListOf<getProducts>()

    fun getFirstProduct(){
        currentOffset = 0
        isPaginationExhausted = false
        productsList.clear()
        
        viewModelScope.launch {
            _productstate.value = ProductUiState.Loading
            try {
                val products = getproduct.call(currentOffset, limit)
                productsList.addAll(products)
                if (products.size < limit) {
                    isPaginationExhausted = true
                }
                _productstate.value = ProductUiState.Success(productsList.toList())
                currentOffset += limit
            } catch (e: Exception) {
                Log.d("ProductError", e.message.toString())
                _productstate.value = ProductUiState.Error(e.toUiError())
            }
        }
    }

    fun loadNextPage() {
        if (isPaginationExhausted || isLoadingMore || _productstate.value is
                    ProductUiState.Loading || _productstate.value is ProductUiState.Error) {
            return
        }

        isLoadingMore = true
        viewModelScope.launch {
            try {
                val newProducts = getproduct.call(currentOffset, limit)
                if (newProducts.size < limit) {
                    isPaginationExhausted = true
                }
                productsList.addAll(newProducts)
                _productstate.value = ProductUiState.Success(productsList.toList())
                currentOffset += limit
            } catch (e: Exception) {
                Log.d("ProductError Pagination", e.message.toString())

            } finally {
                isLoadingMore = false
            }
        }
    }
}