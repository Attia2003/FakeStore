package com.example.fakestore.core.peresention.screens.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.fakestore.core.peresention.screens.AccountScreen
import com.example.fakestore.core.peresention.screens.AddProductScreen
import com.example.fakestore.core.peresention.screens.CartScreen
import com.example.fakestore.core.peresention.screens.HomeScreen
import com.example.fakestore.core.peresention.screens.LoginScreen
import com.example.fakestore.core.peresention.screens.SignUpScreen
import com.example.fakestore.core.peresention.screens.SplashScreen
import com.example.fakestore.core.peresention.screens.component.CategoryByIdScreen
import com.example.fakestore.core.peresention.screens.component.getProductById
import com.example.fakestore.core.peresention.vm.SessionViewModel
import com.example.fakestore.ui.theme.FakeStoreTheme


@Composable
fun AppNavGraph() {
    FakeStoreTheme {
        val navController = rememberNavController()

        val sessionViewModel: SessionViewModel = hiltViewModel()
        val isLoggedIn by sessionViewModel.isLoggedIn.collectAsStateWithLifecycle()

        MainScaffold(navController = navController) { paddingValues ->
            NavHost(
                navController = navController,
                startDestination = Screen.Splash.route,
                modifier = Modifier.padding(paddingValues)
            ) {

                composable(Screen.Splash.route) {
                    SplashScreen(
                        isLoggedIn = isLoggedIn,
                        onNavigate = { loggedIn ->
                            val destination = if (loggedIn) Screen.Home.route else Screen.Login.route
                            navController.navigate(destination) {
                                popUpTo(Screen.Splash.route) { inclusive = true }
                            }
                        }
                    )
                }

                composable(Screen.Home.route) {
                    HomeScreen(
                        onProductClick = { product ->
                            navController.navigate(Screen.Details.createRoute(product.id))
                        },
                        onAddProductClick = {
                            navController.navigate(Screen.AddProduct.route)
                        },
                        onCategoryClick = { category ->
                            navController.navigate(Screen.CategoryDetail.createRoute(category.id))
                        }
                    )
                }

                composable(Screen.AddProduct.route) {
                    AddProductScreen(
                        onProductCreated = {
                            navController.popBackStack()
                        }
                    )
                }

                composable(Screen.Cart.route) {
                    CartScreen(
                        onGoShopping = {
                            navController.navigate(Screen.Home.route) {
                                popUpTo(Screen.Home.route) { inclusive = false }
                            }
                        }
                    )
                }

                composable(Screen.Account.route) {
                    AccountScreen()
                }

                composable(Screen.Login.route) {
                    LoginScreen(
                        onLoginSuccess = {
                            navController.navigate(Screen.Home.route) {
                                popUpTo(Screen.Login.route) { inclusive = true }
                            }
                        },
                        onNavigateToSignUp = {
                            navController.navigate(Screen.SignUp.route) {
                                popUpTo(Screen.Login.route) { inclusive = true }
                            }
                        }
                    )
                }

                composable(Screen.SignUp.route) {
                    SignUpScreen(
                        onSignUpSuccess = {
                            navController.navigate(Screen.Home.route) {
                                popUpTo(Screen.SignUp.route) { inclusive = true }
                            }
                        },
                        onNavigateToLogin = {
                            navController.navigate(Screen.Login.route)
                        }
                    )
                }

                composable(
                    route = Screen.Details.route,
                    arguments = listOf(navArgument("id") { type = NavType.IntType })
                ) { entry ->
                    val id = entry.arguments?.getInt("id") ?: return@composable
                    getProductById(
                        id = id,
                        onNavigateBack = { navController.popBackStack() }
                    )
                }

                composable(
                    route = Screen.CategoryDetail.route,
                    arguments = listOf(navArgument("id") { type = NavType.IntType })
                ) { entry ->
                    val id = entry.arguments?.getInt("id") ?: return@composable
                    CategoryByIdScreen(
                        id = id,
                        onNavigateBack = { navController.popBackStack() },
                        onProductClick = { productId ->
                            navController.navigate(Screen.Details.createRoute(productId))
                        }
                    )
                }
            }
        }
    }
}
