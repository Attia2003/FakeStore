package com.example.fakestore.core.peresention.vm

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fakestore.core.domain.usecases.ProductByIdUseCaase
import com.example.fakestore.core.peresention.uistate.productByIdUiState
import com.example.fakestore.core.peresention.util.toUiError
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductByIdViewModel
    @Inject
    constructor(
        private val getProductByIdUseCase: ProductByIdUseCaase,
    ) : ViewModel() {
        private val _productByIdState = MutableStateFlow<productByIdUiState>(productByIdUiState.Idle)
        val productByIdState: StateFlow<productByIdUiState> = _productByIdState

        fun getProductById(id: Int) {
            viewModelScope.launch {
                _productByIdState.value = productByIdUiState.Loading
                try {
                    Log.d("ProductByIdViewModel", "Fetching product with ID: $id")
                    val product = getProductByIdUseCase.call(id)
                    Log.d("ProductByIdViewModel", "Fetched product: $product")
                    _productByIdState.value = productByIdUiState.Success(product)
                } catch (e: Exception) {
                    Log.e("ProductByIdViewModel", "Exception caught: ${e.message}", e)
                    _productByIdState.value = productByIdUiState.Error(e.toUiError())
                }
            }
        }
    }
