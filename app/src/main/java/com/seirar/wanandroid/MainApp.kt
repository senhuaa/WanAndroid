package com.seirar.wanandroid

import android.annotation.SuppressLint
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import com.seirar.wanandroid.navigation.TopLevelDestination
import com.seirar.wanandroid.navigation.WanNavHost
import com.seirar.wanandroid.ui.components.WanNavigationBar
import com.seirar.wanandroid.ui.components.WanNavigationBarItem

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MainApp(
    windowSizeClass: WindowSizeClass,
    appState: MainAppState = rememberWanAppState(
        windowSizeClass = windowSizeClass
    )
) {
    val snackbarHostState = remember { SnackbarHostState() }

    Scaffold(
        bottomBar = {
            if (appState.shouldShowBottomBar) {
                WanBottomBar(
                    destinations = appState.topLevelDestination,
                    onNavigateToDestination = appState::navigateToTopLevelDestination,
                    currentDestination = appState.currentDestination,
                    modifier = Modifier.testTag("WanBottomBar")
                )
            }
        },
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
    ) {
        WanNavHost(
            appState = appState,
            snackbarHostState = snackbarHostState
        )
    }
}

@Composable
private fun WanBottomBar(
    destinations: List<TopLevelDestination>,
    onNavigateToDestination: (TopLevelDestination) -> Unit,
    currentDestination: NavDestination?,
    modifier: Modifier = Modifier,
) {
    WanNavigationBar(
        modifier = modifier,
        content = {
            destinations.forEach { destinations ->
                val selected = currentDestination.isTopLevelDestinationInHierarchy(destinations)
                WanNavigationBarItem(
                    selected = selected,
                    onClick = { onNavigateToDestination(destinations) },
                    icon = {
                        Icon(
                            imageVector = destinations.unselectedIcon,
                            contentDescription = "Home"
                        )
                    },
                    selectedIcon = {
                        Icon(
                            imageVector = destinations.selectedIcon,
                            contentDescription = "Home"
                        )
                    },
                    label = { Text(stringResource(destinations.iconTextId)) },
                    modifier = modifier
                )
            }
        }
    )
}

private fun NavDestination?.isTopLevelDestinationInHierarchy(destination: TopLevelDestination) =
    this?.hierarchy?.any {
        it.route?.contains(destination.name, true) ?: false
    } ?: false