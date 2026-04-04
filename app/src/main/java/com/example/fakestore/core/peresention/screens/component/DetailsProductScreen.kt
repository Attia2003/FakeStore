package com.example.fakestore.core.peresention.screens.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults

import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil3.compose.AsyncImage
import com.example.fakestore.core.peresention.uistate.ProductByIdUiState
import com.example.fakestore.core.peresention.uistate.UiError
import com.example.fakestore.core.peresention.vm.CartViewModel
import com.example.fakestore.core.peresention.vm.ProductByIdViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun getProductById(
    id:Int,
    onNavigateBack: () -> Unit = {},
    vm: ProductByIdViewModel = hiltViewModel(),
    cartVm: CartViewModel = hiltViewModel()
) {
    val state by vm.productByIdState.collectAsState()

    LaunchedEffect(id) { vm.getProductById(id) }

    Scaffold(
        topBar = {
            TopAppBar(
                modifier = Modifier.padding(bottom = 16.dp),
                title = { Text("Product Details") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(top = 6.dp)
                .verticalScroll(rememberScrollState())
        ) {
            when (val istate = state) {
            ProductByIdUiState.Idle -> {}
            ProductByIdUiState.Loading -> {
            androidx.compose.foundation.layout.Box(
                modifier = Modifier.fillMaxWidth().height(200.dp),
                contentAlignment = Alignment.Center
            ) { CircularProgressIndicator() }
        }

        is ProductByIdUiState.Error -> {
            val message = when (val error = istate.error) {
                UiError.NoInternet -> "Check your Internet"
                is UiError.Http -> "Error ${error.code}"
                UiError.Unknown -> "Unknown Error"
                else -> ""
            }
            
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = message, style = MaterialTheme.typography.bodyLarge)
                Button(onClick = { vm.getProductById(id) }) {
                    Text("Retry")
                }
            }
        }

        is ProductByIdUiState.Success -> {
            val product = istate.product
            val imageUrl = product.images?.firstOrNull()

            Column (
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .wrapContentHeight(),
                verticalArrangement = Arrangement.spacedBy(16.dp),


            ) {

                if (imageUrl != null) {
                    AsyncImage(
                        model = imageUrl,
                        contentDescription = product.title,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(300.dp),
                        contentScale = ContentScale.Crop
                    )
                } else {
                    Spacer(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(300.dp)
                    )
                }




                Column(
                    Modifier.padding(16.dp)
                ) {


                    Text(product.title.orEmpty(), style = MaterialTheme.typography.headlineSmall)

                    Spacer(Modifier.height(8.dp))

                    Text("EGP ${product.price}")

                    Spacer(Modifier.height(8.dp))

                    Text(product.category.name)

                    Spacer(Modifier.height(8.dp))

                    Text(product.description.orEmpty())

                    Spacer(Modifier.height(16.dp))

                    Button(
                        onClick = {
                            cartVm.addToCart(
                                productId = product.id,
                                title = product.title.orEmpty(),
                                price = product.price.toDouble(),
                                imageUrl = imageUrl ?: ""
                            )
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(52.dp),
                        shape = androidx.compose.foundation.shape.RoundedCornerShape(14.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.primary,
                            contentColor = MaterialTheme.colorScheme.onPrimary
                        )
                    ) {
                        Text(
                            text = "Add to Cart",
                            style = MaterialTheme.typography.titleSmall,
                            fontWeight = androidx.compose.ui.text.font.FontWeight.Bold
                        )
                    }
                }
            }
        }
            }
        }
    }
}


