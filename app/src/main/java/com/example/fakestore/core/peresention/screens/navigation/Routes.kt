package com.example.fakestore.core.peresention.screens.navigation

object Routes {
    const val HOME = "Home"
    const val ADD_PRODUCT = "Add_Product"
    const val ACCOUNT = "Account"
    const val PRODUCTS = "Products"
    const val DETAILS = "Details/{id}"
    const val SIGNUP = "Signup"
    const val LOGIN = "Login"
    fun details(id: Int) = "details/$id"
}