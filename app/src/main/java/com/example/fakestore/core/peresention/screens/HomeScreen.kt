package com.example.fakestore.core.peresention.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.fakestore.core.data.dto.CategoryDto
import com.example.fakestore.core.data.dto.getProducts
import com.example.fakestore.core.peresention.screens.component.HorizontalCardProduct
import com.example.fakestore.core.peresention.screens.component.HorizontalCategoryList
import com.example.fakestore.core.peresention.uistate.ProductUiState
import com.example.fakestore.core.peresention.uistate.UiError
import com.example.fakestore.core.peresention.vm.CategoryViewModel
import com.example.fakestore.core.peresention.vm.ProductViewModel

@Composable
fun HomeScreen(
    vm: ProductViewModel = hiltViewModel(),
    categoryVm: CategoryViewModel = hiltViewModel(),
    onProductClick: (getProducts) -> Unit = {},
    onAddProductClick: () -> Unit = {},
    onCategoryClick: (CategoryDto) -> Unit = {}
) {
    val state by vm.productstate.collectAsState()
    val categoryState by categoryVm.categoryState.collectAsState()
    val listState = rememberLazyListState()
    

    var isFabVisible by remember { mutableStateOf(true) }
    val previousScrollOffset = remember { mutableStateOf(0) }
    

    LaunchedEffect(listState.firstVisibleItemIndex, listState.firstVisibleItemScrollOffset) {
        val currentScrollOffset = listState.firstVisibleItemIndex * 1000 + listState.firstVisibleItemScrollOffset
        
        if (currentScrollOffset > previousScrollOffset.value && currentScrollOffset > 50) {

            isFabVisible = false
        } else if (currentScrollOffset < previousScrollOffset.value) {

            isFabVisible = true
        }
        
        previousScrollOffset.value = currentScrollOffset
    }

    LaunchedEffect(listState) {
        snapshotFlow { listState.layoutInfo.visibleItemsInfo.lastOrNull()?.index }
            .collect { lastIndex ->
                if (lastIndex != null && lastIndex >= listState.layoutInfo.totalItemsCount - 1) {
                    val currentState = state
                    if (currentState is ProductUiState.Success && currentState.products.isNotEmpty()) {
                        vm.loadNextPage()
                    }
                }
            }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            state = listState,
            contentPadding = PaddingValues(vertical = 16.dp)
        ) {

            item {
                HorizontalCategoryList(
                    categoryState = categoryState,
                    onCategoryClick = onCategoryClick,
                    onRetry = { categoryVm.getAllCategories() }
                )
                Spacer(Modifier.height(24.dp))
            }
            
            item {
                Text(
                    text = "Recommended Product",
                    style = MaterialTheme.typography.headlineSmall,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
                Spacer(Modifier.height(16.dp))
            }

                when (val isstate = state) {
                    ProductUiState.Idle -> {
                        item {
                        }
                    }

                    ProductUiState.Loading -> {
                        item {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(130.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                CircularProgressIndicator()
                            }
                        }
                    }

                    is ProductUiState.Error -> {
                        item {
                            val message = when (val eror = isstate.eror) {
                                UiError.NoInternet -> "Check ur Internet"
                                is UiError.Http -> "Error ${eror.code}"
                                UiError.Unknown -> "Unknown Error"
                                else -> ""
                            }
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 12.dp),
                                verticalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                Text(text = message)
                                Button(onClick = { vm.getFirstProduct() }) {
                                    Text("Retry")
                                }
                            }
                        }
                    }

                    is ProductUiState.Success -> {
                        val products = isstate.products
                        val rows = products.chunked(2)
                        rows.forEachIndexed { index, rowProducts ->
                            item(key = "row_$index") {
                                androidx.compose.foundation.layout.Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(horizontal = 16.dp, vertical = 8.dp),
                                    horizontalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterHorizontally)
                                ) {
                                    for (product in rowProducts) {
                                        Box(modifier = Modifier.weight(1f), contentAlignment = Alignment.Center) {
                                            HorizontalCardProduct(
                                                product = product,
                                                onClick = { onProductClick(product) }
                                            )
                                        }
                                    }
                                    if (rowProducts.size < 2) {
                                        Spacer(modifier = Modifier.weight(1f))
                                    }
                                }
                            }
                        }
                    }
                }

            item {
                Spacer(modifier = Modifier.height(16.dp))
            }
        }

        AnimatedVisibility(
            visible = isFabVisible,
            enter = slideInVertically(initialOffsetY = { it }),
            exit = slideOutVertically(targetOffsetY = { it }),
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp)
        ) {
            FloatingActionButton(
                onClick = onAddProductClick,
                shape = CircleShape,
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add Product"
                )
            }
        }
    }
}
