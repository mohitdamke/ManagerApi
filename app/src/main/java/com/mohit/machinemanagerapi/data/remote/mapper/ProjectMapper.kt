package com.mohit.machinemanagerapi.data.remote.mapper

import com.mohit.machinemanagerapi.data.remote.dto.ProductDto
import com.mohit.machinemanagerapi.domain.model.Product

fun ProductDto.toProduct() : Product {
    return Product(
        id = id,
        title = title,
        description = description,
        price = price,
        category = category
    )
}