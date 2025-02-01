package com.seirar.wanandroid.ui.home

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.navDeepLink
import androidx.navigation.navigation
import kotlinx.serialization.Serializable

const val homeNavigationGraph = "homeGraph/"
const val homeNavigationRoute = "home/"

fun NavController.navigateToHome(navOptions: NavOptions) = navigate(route = homeNavigationRoute, navOptions)


fun NavGraphBuilder.homeGraph() {
    navigation(
        route = homeNavigationGraph,
        startDestination = homeNavigationRoute
    ) {
        composable(
            route = homeNavigationRoute
        ) {
            HomeScreen()
        }
    }
}