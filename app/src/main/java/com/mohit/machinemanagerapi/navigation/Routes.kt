package com.mohit.machinemanagerapi.navigation

object Routes {
    const val ProductList = "product_list"
    const val ProductDetails = "product_details/{productId}"

    fun productDetails(productId: Int): String {
        return "product_details/$productId"
    }
}