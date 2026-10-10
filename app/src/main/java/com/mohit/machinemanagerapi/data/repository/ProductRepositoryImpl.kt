package com.mohit.machinemanagerapi.data.repository

import com.mohit.machinemanagerapi.data.remote.api.ProductApi
import com.mohit.machinemanagerapi.data.remote.mapper.toProduct
import com.mohit.machinemanagerapi.domain.model.Product
import com.mohit.machinemanagerapi.domain.model.ProductPage
import com.mohit.machinemanagerapi.domain.repository.ProductRepository
import javax.inject.Inject

class ProductRepositoryImpl @Inject constructor(
    private val api: ProductApi
) : ProductRepository {

    override suspend fun getProducts(
        limit: Int,
        skip: Int,
        sortBy: String?,
        order: String?
    ): ProductPage {
        val response = api.getProducts(
            limit = limit,
            skip = skip,
            sortBy = sortBy,
            order = order
        )

        return ProductPage(
            products = response.products.map { it.toProduct() },
            total = response.total
        )
    }

    override suspend fun searchProducts(
        query: String,
        limit: Int,
        skip: Int,
        sortBy: String?,
        order: String?
    ): ProductPage {
        val response = api.searchProducts(
            query = query,
            limit = limit,
            skip = skip,
            sortBy = sortBy,
            order = order
        )

        return ProductPage(
            products = response.products.map { it.toProduct() },
            total = response.total
        )
    }

    override suspend fun getProductsByCategory(
        category: String,
        limit: Int,
        skip: Int,
        sortBy: String?,
        order: String?
    ): ProductPage {
        val response = api.getProductsByCategory(
            category = category,
            limit = limit,
            skip = skip,
            sortBy = sortBy,
            order = order
        )

        return ProductPage(
            products = response.products.map { it.toProduct() },
            total = response.total
        )
    }

    override suspend fun getProductById(id: Int): Product {
        return api.getProductById(id).toProduct()
    }
}