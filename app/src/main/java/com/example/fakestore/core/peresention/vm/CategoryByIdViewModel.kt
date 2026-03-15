package com.example.fakestore.core.peresention.vm

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fakestore.core.domain.usecases.CategoryByIdUseCase
import com.example.fakestore.core.peresention.uistate.CategoryByIdUiState
import com.example.fakestore.core.peresention.util.toUiError
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CategoryByIdViewModel @Inject constructor(
    private val categoryByIdUseCase: CategoryByIdUseCase
) : ViewModel() {

    private val _categoryByIdState = MutableStateFlow<CategoryByIdUiState>(CategoryByIdUiState.Idle)
    val categoryByIdState: StateFlow<CategoryByIdUiState> = _categoryByIdState

    fun getCategoryById(id: Int) {
        viewModelScope.launch {
            _categoryByIdState.value = CategoryByIdUiState.Loading
            try {
                Log.d("CategoryByIdViewModel", "Fetching products for category ID: $id")
                val products = categoryByIdUseCase.call(id)
                Log.d("CategoryByIdViewModel", "Fetched products: ${products.size}")
                _categoryByIdState.value = CategoryByIdUiState.Success(products)
            } catch (e: Exception) {
                Log.e("CategoryByIdViewModel", "Error: ${e.message}", e)
                _categoryByIdState.value = CategoryByIdUiState.Error(e.toUiError())
            }
        }
    }
}
