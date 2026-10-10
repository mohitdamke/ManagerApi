package com.mohit.machinemanagerapi.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mohit.machinemanagerapi.domain.model.Product
import com.mohit.machinemanagerapi.domain.model.ProductPage
import com.mohit.machinemanagerapi.domain.repository.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductViewModel @Inject constructor(
    private val repository: ProductRepository
) : ViewModel() {

    private val _products = MutableStateFlow<List<Product>>(emptyList())
    val products = _products.asStateFlow()

    private val _selectedProduct = MutableStateFlow<Product?>(null)
    val selectedProduct = _selectedProduct.asStateFlow()

    private val _searchQuery = MutableStateFlow("")
    val searchQuery = _searchQuery.asStateFlow()

    private val _selectedCategory = MutableStateFlow("All")
    val selectedCategory = _selectedCategory.asStateFlow()

    // null = default, "asc" = low to high, "desc" = high to low
    private val _sortOrder = MutableStateFlow<String?>(null)
    val sortOrder = _sortOrder.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    private val _isLoadingMore = MutableStateFlow(false)
    val isLoadingMore = _isLoadingMore.asStateFlow()

    private val _isDetailsLoading = MutableStateFlow(false)
    val isDetailsLoading = _isDetailsLoading.asStateFlow()

    private val _isError = MutableStateFlow("")
    val isError = _isError.asStateFlow()

    private val limit = 10
    private var skip = 0
    private var total = 0
    private var hasMore = true
    private var searchJob: Job? = null

    init {
        loadProducts()
    }

    // Decide which API to call
    private suspend fun fetchProducts(offset: Int): ProductPage {
        val query = _searchQuery.value.trim()
        val category = _selectedCategory.value
        val order = _sortOrder.value
        val sortBy = if (order != null) "price" else null

        return when {
            query.isNotEmpty() -> repository.searchProducts(
                query = query,
                limit = limit,
                skip = offset,
                sortBy = sortBy,
                order = order
            )

            category != "All" -> repository.getProductsByCategory(
                category = category,
                limit = limit,
                skip = offset,
                sortBy = sortBy,
                order = order
            )

            else -> repository.getProducts(
                limit = limit,
                skip = offset,
                sortBy = sortBy,
                order = order
            )
        }
    }

    // Load products using current search, category and sort options
    fun loadProducts() {
        searchJob?.cancel()

        searchJob = viewModelScope.launch {
            _isLoading.value = true
            _isError.value = ""

            skip = 0
            total = 0
            hasMore = true
            _products.value = emptyList()

            try {
                val result = fetchProducts(0)

                _products.value = result.products
                total = result.total
                skip = result.products.size
                hasMore = skip < total

            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                _isError.value = e.message ?: "Something went wrong"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun searchProducts(query: String) {
        _searchQuery.value = query
        loadProducts()
    }

    fun filterByCategory(category: String) {
        _selectedCategory.value = category
        loadProducts()
    }

    fun sortProducts(order: String?) {
        _sortOrder.value = order
        loadProducts()
    }

    // Load details of one product
    fun getProductById(id: Int) {
        viewModelScope.launch {
            _isDetailsLoading.value = true
            _isError.value = ""
            _selectedProduct.value = null

            try {
                _selectedProduct.value = repository.getProductById(id)
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                _isError.value = e.message ?: "Something went wrong"
            } finally {
                _isDetailsLoading.value = false
            }
        }
    }

    // Load the next page
    fun loadMoreProducts() {
        if (_isLoading.value || _isLoadingMore.value || !hasMore) return

        viewModelScope.launch {
            _isLoadingMore.value = true
            _isError.value = ""

            try {
                val result = fetchProducts(skip)

                _products.value += result.products
                skip += result.products.size
                total = result.total
                hasMore = skip < total

            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                _isError.value = e.message ?: "Something went wrong"
            } finally {
                _isLoadingMore.value = false
            }
        }
    }


}