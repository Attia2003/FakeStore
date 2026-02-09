package com.example.fakestore.core.peresention.vm

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fakestore.core.domain.usecases.ProductByIdUseCaase
import com.example.fakestore.core.peresention.uistate.ProductByIdUiState
import com.example.fakestore.core.peresention.util.toUiError
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject



@HiltViewModel
class ProductByIdViewModel @Inject constructor(
    private val getProductByIdUseCase: ProductByIdUseCaase
) : ViewModel() {

    private val _productByIdState = MutableStateFlow<ProductByIdUiState>(ProductByIdUiState.Idle)
    val productByIdState: StateFlow<ProductByIdUiState> = _productByIdState



    fun getProductById(id: Int) {
        viewModelScope.launch {
            _productByIdState.value = ProductByIdUiState.Loading
            try {
                Log.d("ProductByIdViewModel", "Fetching product with ID: $id")
                val product = getProductByIdUseCase.call(id)
                Log.d("ProductByIdViewModel", "Fetched product: $product")
                _productByIdState.value = ProductByIdUiState.Success(product)
            } catch (e: Exception) {
                Log.e("ProductByIdViewModel", "Exception caught: ${e.message}", e)
                _productByIdState.value = ProductByIdUiState.Error(e.toUiError())
            }
        }
    }
}






