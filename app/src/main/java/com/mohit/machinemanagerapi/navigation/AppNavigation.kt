package com.mohit.machinemanagerapi.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.mohit.machinemanagerapi.presentation.screens.ProductDetailsScreen
import com.mohit.machinemanagerapi.presentation.screens.ProductListScreen

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Routes.ProductList
    ) {
        composable(Routes.ProductList) {
            ProductListScreen(
                onProductClick = {
                    navController.navigate(Routes.productDetails(it))
                }
            )
        }

        composable(
            route = Routes.ProductDetails
        ) { backStackEntry ->

            val productId =
                backStackEntry.arguments?.getString("productId")?.toIntOrNull() ?: 0

            ProductDetailsScreen(
                productId = productId,
                onBack = {
                    navController.popBackStack()
                }
            )
        }
    }
}