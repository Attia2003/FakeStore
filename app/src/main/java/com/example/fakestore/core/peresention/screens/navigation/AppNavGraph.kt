package com.example.fakestore.core.peresention.screens.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.fakestore.core.peresention.screens.AccountScreen
import com.example.fakestore.core.peresention.screens.AddProductScreen
import com.example.fakestore.core.peresention.screens.HomeScreen
import com.example.fakestore.core.peresention.screens.LoginScreen
import com.example.fakestore.core.peresention.screens.SignUpScreen
import com.example.fakestore.core.peresention.screens.component.getProductById
import com.example.fakestore.ui.theme.FakeStoreTheme


@Composable
fun AppNavGraph() {
    FakeStoreTheme {
        val navController = rememberNavController()
        

        MainScaffold(navController = navController) { paddingValues ->
            NavHost(
                navController = navController,
                startDestination = Routes.LOGIN,
                modifier = Modifier.padding(paddingValues)
            ) {

                composable(Routes.HOME) {
                    HomeScreen(
                        onProductClick = { product ->
                            navController.navigate(Routes.details(product.id))
                        },
                        onAddProductClick = {
                            navController.navigate(Routes.ADD_PRODUCT)
                        }
                    )
                }


                composable(Routes.ADD_PRODUCT) {
                    AddProductScreen(
                        onProductCreated = {
                            navController.popBackStack()
                        }
                    )
                }
                

                composable(Routes.ACCOUNT) {
                    AccountScreen()
                }
                
                // Login screen
                composable(Routes.LOGIN) {
                    LoginScreen(
                        onLoginSuccess = {
                            navController.navigate(Routes.HOME) {
                                popUpTo(Routes.LOGIN) { inclusive = true }
                            }
                        },
                        onNavigateToSignUp = {
                            navController.navigate(Routes.SIGNUP) {
                                popUpTo(Routes.LOGIN) { inclusive = true }
                            }
                        }
                    )
                }

                // Signup screen
                composable(Routes.SIGNUP) {
                    SignUpScreen(
                        onSignUpSuccess = {
                            navController.navigate(Routes.HOME) {
                                popUpTo(Routes.SIGNUP) { inclusive = true }
                            }
                        },
                        onNavigateToLogin = {
                            navController.navigate(Routes.LOGIN)
                        }
                    )
                }
                
                // Product details screen
                composable(
                    route = Routes.DETAILS,
                    arguments = listOf(navArgument("id") { type = NavType.IntType })
                ) { entry ->
                    val id = entry.arguments?.getInt("id") ?: return@composable
                    getProductById(
                        id = id,
                        onNavigateBack = { navController.popBackStack() }
                    )
                }
            }
        }
    }
}