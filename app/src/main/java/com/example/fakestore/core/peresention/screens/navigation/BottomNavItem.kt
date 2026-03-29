package com.example.fakestore.core.peresention.screens.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.ui.graphics.vector.ImageVector


sealed class BottomNavItem(
    val screen: Screen,
    val title: String,
    val icon: ImageVector
) {
    data object Home : BottomNavItem(
        screen = Screen.Home,
        title = "Home",
        icon = Icons.Default.Home
    )

    data object Account : BottomNavItem(
        screen = Screen.Account,
        title = "Account",
        icon = Icons.Default.Person
    )
}


val bottomNavItems = listOf(
    BottomNavItem.Home,
    BottomNavItem.Account
)
