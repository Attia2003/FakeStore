package com.example.fakestore.core.peresention.screens.component

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.fakestore.core.data.dto.CategoryDto
import com.example.fakestore.core.peresention.uistate.CategoryUiState
import com.example.fakestore.core.peresention.uistate.UiError

@Composable
fun HorizontalCategoryList(
    categoryState: CategoryUiState,
    onCategoryClick: (CategoryDto) -> Unit,
    onRetry: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {

        Text(
            text = "Categories",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = androidx.compose.ui.text.font.FontWeight.SemiBold,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp)
        )

        // Category List
        when (categoryState) {
            CategoryUiState.Idle -> {

            }

            CategoryUiState.Loading -> {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(100.dp),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }

            is CategoryUiState.Error -> {
                val message = when (val error = categoryState.error) {
                    UiError.NoInternet -> "Check your Internet"
                    is UiError.Http -> "Error ${error.code}"
                    UiError.Unknown -> "Unknown Error"
                    else -> ""
                }
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(text = message)
                    Button(onClick = onRetry) {
                        Text("Retry")
                    }
                }
            }

            is CategoryUiState.Success -> {
                LazyRow(
                    modifier = Modifier.fillMaxWidth(),
                    contentPadding = PaddingValues(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(
                        items = categoryState.categories,
                        key = { it.id }
                    ) { category ->
                        CategoryCard(
                            category = category,
                            onClick = { onCategoryClick(category) }
                        )
                    }
                }
            }
        }
    }
}
