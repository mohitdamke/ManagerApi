package com.mohit.machinemanagerapi.data.remote.dto

data class ProductDto(
    val id: Int,
    val title: String,
    val description: String,
    val price: Double,
    val category: String
)