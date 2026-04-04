package com.example.fakestore.core.peresention.screens.navigation

sealed class Screen(val route: String) {
    data object Splash : Screen("splash")
    data object Home : Screen("home")
    data object AddProduct : Screen("add_product")
    data object Cart : Screen("cart")
    data object Account : Screen("account")
    data object Login : Screen("login")
    data object SignUp : Screen("signup")


    data object Details : Screen("details/{id}") {
        fun createRoute(id: Int) = "details/$id"
    }

    data object CategoryDetail : Screen("category_detail/{id}") {
        fun createRoute(id: Int) = "category_detail/$id"
    }
}