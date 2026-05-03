package com.example.fakestore

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.example.fakestore.core.data.dto.CategoryBYid
import com.example.fakestore.core.data.dto.getproductbyid
import com.example.fakestore.core.peresention.screens.component.ProductDetailsContent
import com.example.fakestore.core.peresention.uistate.ProductByIdUiState
import com.example.fakestore.core.peresention.uistate.UiError
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test

class DetailsProductScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private val fakeCategory = CategoryBYid(id = 1, name = "electronics")

    private val fakeProduct = getproductbyid(
        id = 5,
        price = 22220,
        description = "noise cancelling wireless headphones and rgp can work with all devices",
        title = "headphone",
        category = fakeCategory,
        images = listOf("https://fakeurl.com/image.png")
    )



    @Test
    fun successState_displaysProductTitle() {
        composeTestRule.setContent {
            ProductDetailsContent(
                uiState = ProductByIdUiState.Success(fakeProduct),
                onNavigateBack = {},
                onRetryClick = {},
                onAddToCartClick = {}
            )
        }

        composeTestRule.onNodeWithText("headphone").assertIsDisplayed()
    }

    @Test
    fun successState_displaysProductPrice() {
        composeTestRule.setContent {
            ProductDetailsContent(
                uiState = ProductByIdUiState.Success(fakeProduct),
                onNavigateBack = {},
                onRetryClick = {},
                onAddToCartClick = {}
            )
        }

        composeTestRule.onNodeWithText("EGP 22220").assertIsDisplayed()
    }

    @Test
    fun successState_displaysCategoryName() {
        composeTestRule.setContent {
            ProductDetailsContent(
                uiState = ProductByIdUiState.Success(fakeProduct),
                onNavigateBack = {},
                onRetryClick = {},
                onAddToCartClick = {}
            )
        }

        composeTestRule.onNodeWithText("electronics").assertIsDisplayed()
    }

    @Test
    fun successState_displaysDescription() {
        composeTestRule.setContent {
            ProductDetailsContent(
                uiState = ProductByIdUiState.Success(fakeProduct),
                onNavigateBack = {},
                onRetryClick = {},
                onAddToCartClick = {}
            )
        }

        composeTestRule
            .onNodeWithText("noise cancelling wireless headphones and rgp can work with all devices")
            .assertIsDisplayed()
    }

    @Test
    fun successState_displaysAddToCartButton() {
        composeTestRule.setContent {
            ProductDetailsContent(
                uiState = ProductByIdUiState.Success(fakeProduct),
                onNavigateBack = {},
                onRetryClick = {},
                onAddToCartClick = {}
            )
        }

        composeTestRule.onNodeWithTag("add_to_cart_button").assertIsDisplayed()
        composeTestRule.onNodeWithText("Add to Cart").assertIsDisplayed()
    }

    @Test
    fun successState_addToCartClick_invokesCallbackWithProduct() {
        var clickedProductId: Int? = null

        composeTestRule.setContent {
            ProductDetailsContent(
                uiState = ProductByIdUiState.Success(fakeProduct),
                onNavigateBack = {},
                onRetryClick = {},
                onAddToCartClick = { product -> clickedProductId = product.id }
            )
        }

        composeTestRule.onNodeWithTag("add_to_cart_button").performClick()

        assertTrue("Add to cart callback should be invoked with product id 5", clickedProductId == 5)
    }

    @Test
    fun successState_backButton_invokesNavigateBack() {
        var backClicked = false

        composeTestRule.setContent {
            ProductDetailsContent(
                uiState = ProductByIdUiState.Success(fakeProduct),
                onNavigateBack = { backClicked = true },
                onRetryClick = {},
                onAddToCartClick = {}
            )
        }

        composeTestRule.onNodeWithTag("back_button").performClick()

        assertTrue("Back navigation callback should be invoked", backClicked)
    }



    @Test
    fun loadingState_displaysLoadingIndicator() {
        composeTestRule.setContent {
            ProductDetailsContent(
                uiState = ProductByIdUiState.Loading,
                onNavigateBack = {},
                onRetryClick = {},
                onAddToCartClick = {}
            )
        }

        composeTestRule.onNodeWithTag("loading_indicator").assertIsDisplayed()
    }



    @Test
    fun errorState_noInternet_displaysMessage() {
        composeTestRule.setContent {
            ProductDetailsContent(
                uiState = ProductByIdUiState.Error(UiError.NoInternet),
                onNavigateBack = {},
                onRetryClick = {},
                onAddToCartClick = {}
            )
        }

        composeTestRule.onNodeWithText("Check your Internet").assertIsDisplayed()
    }

    @Test
    fun errorState_displaysRetryButton() {
        composeTestRule.setContent {
            ProductDetailsContent(
                uiState = ProductByIdUiState.Error(UiError.Unknown),
                onNavigateBack = {},
                onRetryClick = {},
                onAddToCartClick = {}
            )
        }

        composeTestRule.onNodeWithTag("retry_button").assertIsDisplayed()
        composeTestRule.onNodeWithText("Retry").assertIsDisplayed()
    }

    @Test
    fun errorState_retryClick_invokesCallback() {
        var retryClicked = false

        composeTestRule.setContent {
            ProductDetailsContent(
                uiState = ProductByIdUiState.Error(UiError.Unknown),
                onNavigateBack = {},
                onRetryClick = { retryClicked = true },
                onAddToCartClick = {}
            )
        }

        composeTestRule.onNodeWithTag("retry_button").performClick()

        assertTrue("Retry callback should be invoked", retryClicked)
    }
}