package com.example.fakestore.core.peresention.vm

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fakestore.core.data.dto.CreateProductRequest
import com.example.fakestore.core.domain.usecases.AddProductUseCase
import com.example.fakestore.core.peresention.uistate.AddProductUiState
import com.example.fakestore.core.peresention.util.toUiError
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddProductViewModel @Inject constructor(
    private val addProductUseCase: AddProductUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<AddProductUiState>(AddProductUiState.Idle)
    val uiState: StateFlow<AddProductUiState> = _uiState
    private val _events = Channel<AddProductUiState>(Channel.BUFFERED)
    val events = _events.receiveAsFlow()

    fun createProduct(
       form : CreateProductRequest
    ) {
        viewModelScope.launch {
            _uiState.value = AddProductUiState.Loading
            try {
                val request = CreateProductRequest(
                    title = form.title,
                    price = form.price,
                    description =form. description,
                    categoryId = form.categoryId,
                    images = form.images
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

}
