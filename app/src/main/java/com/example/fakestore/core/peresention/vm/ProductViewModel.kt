package com.example.fakestore.core.peresention.vm

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fakestore.core.domain.usecases.ProductUseCaase
import com.example.fakestore.core.peresention.uistate.ProductUiState
import com.example.fakestore.core.peresention.uistate.UiError
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import retrofit2.HttpException
import java.io.IOException

@HiltViewModel
class ProductViewModel @Inject constructor(private val getproduct : ProductUseCaase) : ViewModel() {

    private val _productstate = MutableStateFlow<ProductUiState>(ProductUiState.Idle)
    val  productstate : StateFlow<ProductUiState> = _productstate

    fun getFirstProduct(){
        viewModelScope.launch {
            _productstate.value = ProductUiState.Loading
            try {
                val products = getproduct.call()
                _productstate.value = ProductUiState.Success(products)
                Log.d("product",products.toString())
            }catch (ioe: IOException){
                Log.d("ioEror",ioe.message.toString())
                _productstate.value = ProductUiState.Error(UiError.NoInternet)
            }
            catch (e: HttpException){
                _productstate.value = ProductUiState.Error(UiError.Http(e.code()))
            }
            catch (e: Exception){
                Log.d("unkonwEror",e.message.toString())
                _productstate.value = ProductUiState.Error(UiError.Unknown)
            }


        }
    }


}