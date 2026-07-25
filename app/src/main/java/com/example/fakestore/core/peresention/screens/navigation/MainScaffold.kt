package com.example.fakestore.core.peresention.screens.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState


@Composable
fun MainScaffold(
    currentRoute: String?,
    onNavigate: (String) -> Unit,
    content: @Composable (PaddingValues) -> Unit
) {
    val routesWithoutBottomBar = listOf(
        Screen.Login.route,
        Screen.SignUp.route,
        Screen.Splash.route
    )

    val shouldShowBottomBar = currentRoute !in routesWithoutBottomBar

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        bottomBar = {
            if (shouldShowBottomBar) {
                NavigationBar(
                    containerColor = MaterialTheme.colorScheme.background,
                    contentColor = MaterialTheme.colorScheme.onBackground,
                    tonalElevation = 0.dp
                ) {
                    bottomNavItems.forEach { item ->

                        val isSelected = currentRoute == item.screen.route

                        NavigationBarItem(
                            icon = {
                                Icon(
                                    imageVector = item.icon,
                                    contentDescription = item.title
                                )
                            },
                            label = { Text(item.title) },
                            selected = isSelected,
                            colors = NavigationBarItemDefaults.colors(
                                selectedIconColor = Color(0xFF4285F4),
                                selectedTextColor = Color(0xFF4285F4),
                                unselectedIconColor = Color(0xFF8C9AB5),
                                unselectedTextColor = Color(0xFF8C9AB5),
                                indicatorColor = Color.Transparent
                            ),
                            onClick = {
                                onNavigate(item.screen.route)
                            }
                        )
                    }
                }
            }
        },
        content = content
    )
}
