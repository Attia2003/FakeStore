package com.example.fakestore.core.peresention.vm

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fakestore.core.domain.usecases.CategoryUseCase
import com.example.fakestore.core.peresention.uistate.CategoryUiState
import com.example.fakestore.core.peresention.uistate.UiError
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class CategoryViewModel @Inject constructor(
    private val categoryUseCase: CategoryUseCase
) : ViewModel() {

    private val _categoryState = MutableStateFlow<CategoryUiState>(CategoryUiState.Idle)
    val categoryState: StateFlow<CategoryUiState> = _categoryState

    fun getAllCategories() {
        viewModelScope.launch {
            _categoryState.value = CategoryUiState.Loading
            try {
                val categories = categoryUseCase.call()
                Log.d("CategoryViewModel", "Categories loaded: ${categories.size}")
                _categoryState.value = CategoryUiState.Success(categories)
            } catch (e: IOException) {
                Log.e("CategoryViewModel", "Network error", e)
                _categoryState.value = CategoryUiState.Error(UiError.NoInternet)
            } catch (e: HttpException) {
                Log.e("CategoryViewModel", "HTTP error: ${e.code()}", e)
                _categoryState.value = CategoryUiState.Error(UiError.Http(e.code()))
            } catch (e: Exception) {
                Log.e("CategoryViewModel", "Unknown error", e)
                _categoryState.value = CategoryUiState.Error(UiError.Unknown)
            }
        }
    }
}
