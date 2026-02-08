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
import com.example.fakestore.core.peresention.uistate.AddProductUiState
import com.example.fakestore.core.peresention.uistate.UiError
import com.example.fakestore.core.peresention.vm.AddProductViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddProductScreen(
    viewModel: AddProductViewModel = hiltViewModel(),
    onProductCreated: () -> Unit = {}
) {
    val uiState by viewModel.uiState.collectAsState()
    val context = LocalContext.current

    var title by remember { mutableStateOf("") }
    var price by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var imageUrl by remember { mutableStateOf("") }
    var categoryId by remember { mutableStateOf("1") }

    var titleError by remember { mutableStateOf(false) }
    var priceError by remember { mutableStateOf(false) }
    var descriptionError by remember { mutableStateOf(false) }


    LaunchedEffect(uiState) {
        when (val state = uiState) {
            is AddProductUiState.Success -> {
                Toast.makeText(
                    context,
                    "Product created successfully!",
                    Toast.LENGTH_SHORT
                ).show()
                viewModel.resetState()
                onProductCreated()
            }
            is AddProductUiState.Error -> {
                val message = when (state.error) {
                    UiError.NoInternet -> "No internet connection"
                    is UiError.Http -> "Server error: ${state.error.code}"
                    UiError.Unknown -> "An error occurred"
                    else -> ""
                }
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
                viewModel.resetState()
            }
            else -> {}
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Add New Product") },
                navigationIcon = {
                    IconButton(onClick = onProductCreated) {
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
                // Title Field
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

                // Price Field
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

                // Description Field
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

                // Image URL Field
                OutlinedTextField(
                    value = imageUrl,
                    onValueChange = { imageUrl = it },
                    label = { Text("Image URL") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    placeholder = { Text("https://example.com/image.jpg") }
                )

                // Category ID Field
                OutlinedTextField(
                    value = categoryId,
                    onValueChange = { categoryId = it },
                    label = { Text("Category ID") },
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    singleLine = true
                )

                Spacer(modifier = Modifier.height(8.dp))

                // Submit Button
                Button(
                    onClick = {
                        // Validate inputs
                        titleError = title.isBlank()
                        priceError = price.isBlank() || price.toLongOrNull() == null
                        descriptionError = description.isBlank()

                        if (!titleError && !priceError && !descriptionError) {
                            val images = if (imageUrl.isNotBlank()) {
                                listOf(imageUrl)
                            } else {
                                listOf("https://placeimg.com/640/480/any")
                            }

                            viewModel.createProduct(
                                title = title,
                                price = price.toLong(),
                                description = description,
                                categoryId = categoryId.toIntOrNull() ?: 1,
                                images = images
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

                // Helper Text
                Text(
                    text = "* Required fields",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}
