package com.seirar.wanandroid.ui.home

import androidx.compose.material3.SnackbarHostState
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.navigation

const val homeNavigationGraph = "homeGraph/"
const val homeNavigationRoute = "home/"

fun NavController.navigateToHome(navOptions: NavOptions) = navigate(route = homeNavigationRoute, navOptions)


fun NavGraphBuilder.homeGraph(
    snackbarHostState: SnackbarHostState,
    navController: NavController
) {
    navigation(
        route = homeNavigationGraph,
        startDestination = homeNavigationRoute
    ) {
        composable(
            route = homeNavigationRoute
        ) {
            HomeScreen(
                snackbarHostState = snackbarHostState,
                navController = navController
            )
        }
    }
}