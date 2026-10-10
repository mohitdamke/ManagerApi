package com.mohit.machinemanagerapi.domain.model

data class ProductPage(
        val products: List<Product>,
        val total: Int
    )