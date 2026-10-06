package com.example.fakestore.core.peresention.screens.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.fakestore.core.peresention.screens.AccountScreen
import com.example.fakestore.core.peresention.screens.addProductScreenRoute
import com.example.fakestore.core.peresention.screens.cartConterntRoute
import com.example.fakestore.core.peresention.screens.component.categoryByIdScreen
import com.example.fakestore.core.peresention.screens.component.productDetailsScreen
import com.example.fakestore.core.peresention.screens.homeScreen
import com.example.fakestore.core.peresention.screens.loginScreen
import com.example.fakestore.core.peresention.screens.signUpScreen
import com.example.fakestore.core.peresention.screens.splashScreen
import com.example.fakestore.core.peresention.vm.SessionViewModel
import com.example.fakestore.ui.theme.fakeStoreTheme

@Composable
fun appNavGraph() {
    fakeStoreTheme {
        val navController = rememberNavController()

        val sessionViewModel: SessionViewModel = hiltViewModel()
        val isLoggedIn by sessionViewModel.isLoggedIn.collectAsStateWithLifecycle()

        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route

        MainScaffold(
            currentRoute = currentRoute,
            onNavigate = { route ->
                navController.navigate(route) {
                    popUpTo(navController.graph.findStartDestination().id) {
                        saveState = true
                    }
                    launchSingleTop = true
                    restoreState = true
                }
            },
        ) { paddingValues ->
            NavHost(
                navController = navController,
                startDestination = Screen.Splash.route,
                modifier = Modifier.padding(paddingValues),
            ) {
                composable(Screen.Splash.route) {
                    splashScreen(
                        isLoggedIn = isLoggedIn,
                        onNavigate = { loggedIn ->
                            val destination = if (loggedIn) Screen.Home.route else Screen.Login.route
                            navController.navigate(destination) {
                                popUpTo(Screen.Splash.route) { inclusive = true }
                            }
                        },
                    )
                }

                composable(Screen.Home.route) {
                    homeScreen(
                        onProductClick = { product ->
                            navController.navigate(Screen.Details.createRoute(product.id))
                        },
                        onAddProductClick = {
                            navController.navigate(Screen.AddProduct.route)
                        },
                        onCategoryClick = { category ->
                            navController.navigate(Screen.CategoryDetail.createRoute(category.id))
                        },
                    )
                }

                composable(Screen.AddProduct.route) {
                    addProductScreenRoute(
                        onNavigateBack = {
                            navController.popBackStack()
                        },
                    )
                }

                composable(Screen.Cart.route) {
                    cartConterntRoute(
                        onNavigateShopping = {
                            navController.navigate(Screen.Home.route) {
                                popUpTo(Screen.Home.route) { inclusive = false }
                            }
                        },
                    )
                }

                composable(Screen.Account.route) {
                    AccountScreen()
                }

                composable(Screen.Login.route) {
                    loginScreen(
                        onLoginSuccess = {
                            navController.navigate(Screen.Home.route) {
                                popUpTo(Screen.Login.route) { inclusive = true }
                            }
                        },
                        onNavigateToSignUp = {
                            navController.navigate(Screen.SignUp.route) {
                                popUpTo(Screen.Login.route) { inclusive = true }
                            }
                        },
                    )
                }

                composable(Screen.SignUp.route) {
                    signUpScreen(
                        onSignUpSuccess = {
                            navController.navigate(Screen.Home.route) {
                                popUpTo(Screen.SignUp.route) { inclusive = true }
                            }
                        },
                        onNavigateToLogin = {
                            navController.navigate(Screen.Login.route)
                        },
                    )
                }

                composable(
                    route = Screen.Details.route,
                    arguments = listOf(navArgument("id") { type = NavType.IntType }),
                ) { entry ->
                    val id = entry.arguments?.getInt("id") ?: return@composable
                    productDetailsScreen(
                        id = id,
                        onNavigateBack = { navController.popBackStack() },
                    )
                }

                composable(
                    route = Screen.CategoryDetail.route,
                    arguments = listOf(navArgument("id") { type = NavType.IntType }),
                ) { entry ->
                    val id = entry.arguments?.getInt("id") ?: return@composable
                    categoryByIdScreen(
                        id = id,
                        onNavigateBack = { navController.popBackStack() },
                        onProductClick = { productId ->
                            navController.navigate(Screen.Details.createRoute(productId))
                        },
                    )
                }
            }
        }
    }
}
