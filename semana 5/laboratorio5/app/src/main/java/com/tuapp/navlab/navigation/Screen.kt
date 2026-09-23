package com.tuapp.navlab.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument

sealed class Screen(val route: String) {
    data object Home : Screen("home")
    data object List : Screen("list")
    data object Detail : Screen("detail/{id}") {
        fun createRoute(id: Int) = "detail/$id"
    }
    data object Profile : Screen("profile")

    companion object {
        fun fromRoute(route: String): Screen? = when {
            route == "home" -> Home
            route == "list" -> List
            route.startsWith("detail/") -> Detail
            route == "profile" -> Profile
            else -> null
        }
    }
}

@Composable
fun NavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Screen.Home.route
    ) {
        composable(Screen.Home.route) { /* HomeScreen */ }
        composable(Screen.List.route) { /* ListScreen */ }
        composable(
            route = "detail/{id}",
            arguments = listOf(navArgument("id") { type = NavType.IntType })
        ) { /* DetailScreen */ }
        composable(Screen.Profile.route) { /* ProfileScreen */ }
    }
}

// Desarrollado por Jose Barzola
