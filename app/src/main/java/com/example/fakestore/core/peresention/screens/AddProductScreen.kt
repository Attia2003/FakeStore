package com.example.fakestore.core.peresention.screens

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.fakestore.core.data.dto.CreateProductRequest
import com.example.fakestore.core.peresention.uistate.AddProductUiState
import com.example.fakestore.core.peresention.uistate.UiError
import com.example.fakestore.core.peresention.vm.AddProductViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddProductScreen(
    uiState: AddProductUiState,
    onCloseClick: () -> Unit,
    onCreateProductClick: (CreateProductRequest) -> Unit
) {

    var title by remember { mutableStateOf("") }
    var price by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var imageUrl by remember { mutableStateOf("") }
    var categoryId by remember { mutableStateOf("1") }

    var titleError by remember { mutableStateOf(false) }
    var priceError by remember { mutableStateOf(false) }
    var descriptionError by remember { mutableStateOf(false) }




    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Add New Product") },
                navigationIcon = {
                    IconButton(onClick = onCloseClick) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Close"
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {

                OutlinedTextField(
                    value = title,
                    onValueChange = {
                        title = it
                        titleError = it.isBlank()
                    },
                    label = { Text("Product Title *") },
                    modifier = Modifier.fillMaxWidth(),
                    isError = titleError,
                    supportingText = {
                        if (titleError) {
                            Text("Title is required")
                        }
                    },
                    singleLine = true
                )


                OutlinedTextField(
                    value = price,
                    onValueChange = {
                        price = it
                        priceError = it.isBlank() || it.toLongOrNull() == null
                    },
                    label = { Text("Price *") },
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    isError = priceError,
                    supportingText = {
                        if (priceError) {
                            Text("Valid price is required")
                        }
                    },
                    singleLine = true,
                    prefix = { Text("$") }
                )


                OutlinedTextField(
                    value = description,
                    onValueChange = {
                        description = it
                        descriptionError = it.isBlank()
                    },
                    label = { Text("Description *") },
                    modifier = Modifier.fillMaxWidth(),
                    isError = descriptionError,
                    supportingText = {
                        if (descriptionError) {
                            Text("Description is required")
                        }
                    },
                    minLines = 1,
                    maxLines = 6
                )


                OutlinedTextField(
                    value = imageUrl,
                    onValueChange = { imageUrl = it },
                    label = { Text("Image URL") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    placeholder = { Text("https://example.com/image.jpg") }
                )


                OutlinedTextField(
                    value = categoryId,
                    onValueChange = { categoryId = it },
                    label = { Text("Category ID") },
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    singleLine = true
                )

                Spacer(modifier = Modifier.height(8.dp))


                Button(
                    onClick = {

                        titleError = title.isBlank()
                        priceError = price.isBlank() || price.toLongOrNull() == null
                        descriptionError = description.isBlank()

                        if (!titleError && !priceError && !descriptionError) {
                            val images = if (imageUrl.isNotBlank()) {
                                listOf(imageUrl)
                            } else {
                                listOf("https://placeimg.com/640/480/any")
                            }
                            onCreateProductClick(
                            CreateProductRequest(
                                title = title,
                                price = price.toDoubleOrNull() ?: 0.0,
                                description = description,
                                categoryId = categoryId.toIntOrNull() ?: 1,
                                images = images
                                 )
                            )
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    enabled = uiState !is AddProductUiState.Loading
                ) {
                    if (uiState is AddProductUiState.Loading) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(24.dp),
                            color = MaterialTheme.colorScheme.onPrimary
                        )
                    } else {
                        Text("Create Product", style = MaterialTheme.typography.bodyLarge)
                    }
                }


                Text(
                    text = "* Required fields",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}

@Composable
fun AddProductScreenRoute(
    viewModel: AddProductViewModel = hiltViewModel(),
    onNavigateBack: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        viewModel.events.collect { event ->
            when (event) {
                is AddProductUiState.Success -> {
                    Toast.makeText(context, "Product created successfully!", Toast.LENGTH_SHORT).show()
                    onNavigateBack()
                }
                is AddProductUiState.Error -> {
                    val message = when (event.error) {
                        UiError.NoInternet -> "No internet connection"
                        is UiError.Http -> "Server error: ${event.error.code}"
                        UiError.Unknown -> "An error occurred"
                        else -> "Unknown Error"
                    }
                    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
                }

                else -> {}
            }
        }
    }

    AddProductScreen(
        uiState = uiState,
        onCloseClick = onNavigateBack,
        onCreateProductClick = { form ->
            viewModel.createProduct(form)
        }
    )

}