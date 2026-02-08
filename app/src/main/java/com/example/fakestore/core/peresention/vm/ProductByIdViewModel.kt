package com.example.fakestore.core.peresention.vm

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fakestore.core.domain.usecases.ProductByIdUseCaase
import com.example.fakestore.core.peresention.uistate.ProductByIdUiState
import com.example.fakestore.core.peresention.uistate.UiError
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.io.IOException
import javax.inject.Inject
import retrofit2.HttpException



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
            } catch (io: IOException) {
                Log.e("ProductByIdViewModel", "IOException caught: ${io.message}", io)
                _productByIdState.value = ProductByIdUiState.Error(UiError.NoInternet)
            } catch (http: HttpException) {
                Log.e("ProductByIdViewModel", "HttpException caught: ${http.code()} - ${http.message()}", http)
                _productByIdState.value = ProductByIdUiState.Error(UiError.Http(http.code()))
            } catch (e: Exception) {
                Log.e("ProductByIdViewModel", "Unknown Exception caught: ${e.message}", e)
                Log.e("ProductByIdViewModel", "Exception type: ${e.javaClass.name}")
                e.printStackTrace()
                _productByIdState.value = ProductByIdUiState.Error(UiError.Unknown)
            }
        }
    }
}






