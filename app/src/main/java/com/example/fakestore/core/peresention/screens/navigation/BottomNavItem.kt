package com.example.fakestore.core.peresention.screens.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.ui.graphics.vector.ImageVector


sealed class BottomNavItem(
    val route: String,
    val title: String,
    val icon: ImageVector
) {
    data object Home : BottomNavItem(
        route = Routes.HOME,
        title = "Home",
        icon = Icons.Default.Home
    )
    
    data object Account : BottomNavItem(
        route = Routes.ACCOUNT,
        title = "Account",
        icon = Icons.Default.Person
    )
}


val bottomNavItems = listOf(
    BottomNavItem.Home,
    BottomNavItem.Account
)
