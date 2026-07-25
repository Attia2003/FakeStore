package com.example.fakestore.core.peresention.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fakestore.core.data.local.db.CartItemEntity
import com.example.fakestore.core.domain.usecases.AddToCartUseCase
import com.example.fakestore.core.domain.usecases.ClearExpiredCartUseCase
import com.example.fakestore.core.domain.usecases.GetCartItemsUseCase
import com.example.fakestore.core.domain.usecases.RemoveFromCartUseCase
import com.example.fakestore.core.domain.usecases.UpdateCartQuantityUseCase
import com.example.fakestore.core.peresention.uistate.CartUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(
    private val getCartItems: GetCartItemsUseCase,
    private val addToCart: AddToCartUseCase,
    private val updateQuantity: UpdateCartQuantityUseCase,
    private val removeFromCart: RemoveFromCartUseCase,
    private val clearExpired: ClearExpiredCartUseCase
) : ViewModel() {

    private val _cartState = MutableStateFlow<CartUiState>(CartUiState.Loading)
    val cartState: StateFlow<CartUiState> = _cartState

    init {
        viewModelScope.launch {
            clearExpired.call()
            getCartItems.call()
                .collect { items ->
                    _cartState.value = CartUiState.Success(items.toImmutableList())
            }
        }
    }

    fun addToCart(
        productId: Int,
        title: String,
        price: Double,
        imageUrl: String
    ) {
        viewModelScope.launch {
            addToCart.call(
                CartItemEntity(
                    productId = productId,
                    title = title,
                    price = price,
                    imageUrl = imageUrl,
                    quantity = 1
                )
            )
        }
    }

    fun increaseQuantity(productId: Int, currentQty: Int) {
        viewModelScope.launch {
            updateQuantity.call(productId, currentQty + 1)
        }
    }

    fun decreaseQuantity(productId: Int, currentQty: Int, itemId: Int) {
        viewModelScope.launch {
            if (currentQty <= 1) {
                removeFromCart.call(itemId)
            } else {
                updateQuantity.call(productId, currentQty - 1)
            }
        }
    }

    fun removeItem(id: Int) {
        viewModelScope.launch {
            removeFromCart.call(id)
        }
    }
}
