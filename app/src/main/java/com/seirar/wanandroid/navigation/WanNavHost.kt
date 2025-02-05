package com.seirar.wanandroid.navigation

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import com.seirar.wanandroid.MainAppState
import com.seirar.wanandroid.ui.components.webviewGraph
import com.seirar.wanandroid.ui.home.homeGraph
import com.seirar.wanandroid.ui.home.homeNavigationGraph
import com.seirar.wanandroid.ui.project.projectGraph

@Composable
fun WanNavHost(
    appState: MainAppState,
    snackbarHostState: SnackbarHostState,
    modifier: Modifier = Modifier,
    startDestination: String = homeNavigationGraph
) {
    val navController = appState.navController

    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {
        homeGraph(
            snackbarHostState = snackbarHostState,
            navController = navController
        )
        projectGraph()
        webviewGraph(navController)
    }
}