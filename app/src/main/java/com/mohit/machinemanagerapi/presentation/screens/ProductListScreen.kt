package com.mohit.machinemanagerapi.presentation.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.mohit.machinemanagerapi.presentation.viewmodel.ProductViewModel

@Composable
fun ProductListScreen(
    viewModel: ProductViewModel = hiltViewModel(),
    onProductClick: (Int) -> Unit
) {
    val products by viewModel.products.collectAsState()
    val searchQuery by viewModel.searchQuery.collectAsState()
    val selectedCategory by viewModel.selectedCategory.collectAsState()
    val sortOrder by viewModel.sortOrder.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val isLoadingMore by viewModel.isLoadingMore.collectAsState()
    val isError by viewModel.isError.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = searchQuery,
            onValueChange = viewModel::searchProducts,
            label = { Text("Search products") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            listOf("All", "beauty", "furniture", "groceries")
                .forEach { category ->
                FilterChip(
                    selected = selectedCategory == category,
                    onClick = { viewModel.filterByCategory(category) },
                    label = { Text(category) }
                )
            }
        }

        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Button(onClick = { viewModel.sortProducts("asc") }) {
                Text("Price Asc")
            }

            Button(onClick = { viewModel.sortProducts("desc") }) {
                Text("Price Desc")
            }

            TextButton(onClick = { viewModel.sortProducts(null) }) {
                Text("Reset sort")
            }
        }

        if (isLoading) {
            CircularProgressIndicator()
        } else if (isError.isNotEmpty()) {
            Text(isError)
            Button(onClick = { viewModel.loadProducts() }) {
                Text("Retry")
            }
        } else if (products.isEmpty()) {
            Text("No products found")
        } else {
            LazyColumn {
                items(products) { product ->
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { onProductClick(product.id) }
                            .padding(vertical = 12.dp)
                    ) {
                        Text(product.title)
                        Text("Price: $${product.price}")
                        Text("Category: ${product.category}")
                    }

                    HorizontalDivider()
                }

                item {
                    if (isLoadingMore) {
                        CircularProgressIndicator()
                    } else {
                        Button(
                            onClick = viewModel::loadMoreProducts,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text("Load More")
                        }
                    }
                }
            }
        }
    }
}