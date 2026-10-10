package com.mohit.machinemanagerapi.domain.repository

import com.mohit.machinemanagerapi.domain.model.Product
import com.mohit.machinemanagerapi.domain.model.ProductPage

interface ProductRepository {

    suspend fun getProducts(
       limit: Int,
        skip: Int,
        sortBy: String? = null,
         order: String? = null,
    ): ProductPage

    suspend fun searchProducts(
         query: String,
         limit: Int,
         skip: Int,
        sortBy: String? = null,
         order: String? = null,
    ): ProductPage

    suspend fun getProductsByCategory(
       category: String,
         limit: Int,
         skip: Int,
        sortBy: String? = null,
         order: String? = null,
    ): ProductPage

    suspend fun getProductById(
        id : Int
    ): Product
}