package com.example.fakestore.core.peresention.vm

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fakestore.core.data.dto.CreateProductRequest
import com.example.fakestore.core.domain.usecases.AddProductUseCase
import com.example.fakestore.core.peresention.uistate.AddProductUiState
import com.example.fakestore.core.peresention.util.toUiError
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddProductViewModel @Inject constructor(
    private val addProductUseCase: AddProductUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<AddProductUiState>(AddProductUiState.Idle)
    val uiState: StateFlow<AddProductUiState> = _uiState

    fun createProduct(
        title: String,
        price: Long,
        description: String,
        categoryId: Int,
        images: List<String>
    ) {
        viewModelScope.launch {
            _uiState.value = AddProductUiState.Loading
            try {
                val request = CreateProductRequest(
                    title = title,
                    price = price,
                    description = description,
                    categoryId = categoryId,
                    images = images
                )
                val response = addProductUseCase.call(request)
                _uiState.value = AddProductUiState.Success(response)
                Log.d("AddProduct", "Product created: $response")
            } catch (e: Exception) {
                Log.d("AddProductError", "Error: ${e.message}")
                _uiState.value = AddProductUiState.Error(e.toUiError())
            }
        }
    }

    fun resetState() {
        _uiState.value = AddProductUiState.Idle
    }
}
