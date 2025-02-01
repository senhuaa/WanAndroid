package com.seirar.wanandroid.ui.project

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import kotlinx.serialization.Serializable

const val projectNavigationGraph = "projectGraph/"
const val projectNavigationRoute = "project/"

fun NavController.navigateToProject(
    navOptions: NavOptions
) {
    navigate(route = projectNavigationRoute, navOptions)
}

fun NavGraphBuilder.projectGraph(){
    navigation(
        route = projectNavigationGraph,
        startDestination = projectNavigationRoute
    ) {
        composable(
            route = projectNavigationRoute
        ) {
            ProjectScreen()
        }
    }
}