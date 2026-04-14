package com.example.fakestore.core.peresention.vm

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fakestore.core.domain.usecases.CategoryUseCase
import com.example.fakestore.core.peresention.uistate.CategoryUiState
import com.example.fakestore.core.peresention.util.toUiError
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CategoryViewModel @Inject constructor(
    private val categoryUseCase: CategoryUseCase
) : ViewModel() {

    private val _categoryState = MutableStateFlow<CategoryUiState>(CategoryUiState.Idle)
    val categoryState: StateFlow<CategoryUiState> = _categoryState

    init {
        getAllCategories()
    }

    fun getAllCategories() {
        if (_categoryState.value is CategoryUiState.Loading) return

        viewModelScope.launch {
            _categoryState.value = CategoryUiState.Loading
            try {
                val categories = categoryUseCase.call()
                Log.d("CategoryViewModel", "Categories loaded: ${categories.size}")
                _categoryState.value = CategoryUiState.Success(categories)
            } catch (e: Exception) {
                Log.e("CategoryViewModel", "Error: ${e.message}", e)
                _categoryState.value = CategoryUiState.Error(e.toUiError())
            }
        }
    }
}
