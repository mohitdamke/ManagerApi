package com.mohit.machinemanagerapi.data.remote.api

import com.mohit.machinemanagerapi.data.remote.dto.ProductDto
import com.mohit.machinemanagerapi.data.remote.dto.ProductResponseDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ProductApi {

    @GET("products")
    suspend fun getProducts(
        @Query("limit") limit: Int,
        @Query("skip") skip: Int,
        @Query("sortBy") sortBy: String? = null,
        @Query("order") order: String? = null,
    ): ProductResponseDto

    @GET("products/search")
    suspend fun searchProducts(
        @Query("q") query: String,
        @Query("limit") limit: Int,
        @Query("skip") skip: Int,
        @Query("sortBy") sortBy: String? = null,
        @Query("order") order: String? = null,
    ): ProductResponseDto

    @GET("products/category/{category}")
    suspend fun getProductsByCategory(
        @Path("category") category: String,
        @Query("limit") limit: Int,
        @Query("skip") skip: Int,
        @Query("sortBy") sortBy: String? = null,
        @Query("order") order: String? = null,
    ): ProductResponseDto

    @GET("products/{id}")
    suspend fun getProductById(
        @Path("id") id : Int
    ): ProductDto

}