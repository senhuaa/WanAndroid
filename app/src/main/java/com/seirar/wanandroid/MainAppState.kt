package com.seirar.wanandroid

import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.util.trace
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navOptions
import com.seirar.wanandroid.navigation.TopLevelDestination
import com.seirar.wanandroid.ui.home.homeNavigationRoute
import com.seirar.wanandroid.ui.home.navigateToHome
import com.seirar.wanandroid.ui.project.navigateToProject
import com.seirar.wanandroid.ui.project.projectNavigationRoute
import kotlinx.coroutines.CoroutineScope

@Stable
class MainAppState(
    val navController: NavHostController,
    coroutineScope: CoroutineScope,
    private val WindowSizeClass: WindowSizeClass
) {
    val currentDestination: NavDestination?
        @Composable get() = navController
            .currentBackStackEntryAsState().value?.destination

    val currentTopLevelDestination: TopLevelDestination?
        @Composable get() = when (currentDestination?.route) {
            homeNavigationRoute -> TopLevelDestination.HOME
            projectNavigationRoute -> TopLevelDestination.PROJECT
            else -> null
        }

    val shouldShowBottomBar: Boolean
        @Composable get() = currentDestination?.route?.startsWith("webview") == false

    val topLevelDestination: List<TopLevelDestination> = TopLevelDestination.entries

    fun navigateToTopLevelDestination(topLevelDestination: TopLevelDestination) {
        trace("Navigation: ${topLevelDestination.name}") {
            val startDestinationId = navController.graph.startDestinationId
            if (startDestinationId != 0) {
                val topLevelNavOptions = navOptions {
                    popUpTo(navController.graph.findStartDestination().id) {
                        saveState = true
                    }
                    launchSingleTop = true
                    restoreState = true
                }
                when (topLevelDestination) {
                    TopLevelDestination.HOME -> navController.navigateToHome(topLevelNavOptions)
                    TopLevelDestination.PROJECT -> navController.navigateToProject(topLevelNavOptions)
                }
            }

        }
    }
}

@Composable
fun rememberWanAppState(
    windowSizeClass: WindowSizeClass,
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
    navController: NavHostController = rememberNavController(),
) : MainAppState {
    return remember(
        navController,
        coroutineScope,
        windowSizeClass,
    ) {
        MainAppState(
            navController,
            coroutineScope,
            windowSizeClass
        )
    }
}