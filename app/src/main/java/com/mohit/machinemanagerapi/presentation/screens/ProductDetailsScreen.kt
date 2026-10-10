package com.mohit.machinemanagerapi.presentation.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.mohit.machinemanagerapi.presentation.viewmodel.ProductViewModel

@Composable
fun ProductDetailsScreen(
    productId: Int,
    viewModel: ProductViewModel = hiltViewModel(),
    onBack: () -> Unit
) {
    val product by viewModel.selectedProduct.collectAsState()
    val isLoading by viewModel.isDetailsLoading.collectAsState()
    val error by viewModel.isError.collectAsState()

    LaunchedEffect(productId) {
        viewModel.getProductById(productId)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Button(onClick = onBack) {
            Text("Back")
        }

        Spacer(modifier = Modifier.height(16.dp))

        when {
            isLoading -> {
                CircularProgressIndicator()
            }

            error.isNotEmpty() -> {
                Text(text = error)
            }

            product != null -> {
                Text(text = product!!.title)
                Text(text = product!!.description)
                Text(text = "Price: $${product!!.price}")
                Text(text = "Category: ${product!!.category}")
            }
        }
    }
}