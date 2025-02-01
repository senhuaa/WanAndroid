package com.seirar.wanandroid.navigation

import androidx.annotation.StringRes
import androidx.compose.ui.graphics.vector.ImageVector
import com.seirar.wanandroid.R
import com.seirar.wanandroid.ui.icon.WanIcons

enum class TopLevelDestination (
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    @StringRes val iconTextId: Int,
    @StringRes val titleTextId: Int,
) {
    HOME(
        selectedIcon = WanIcons.Home,
        unselectedIcon = WanIcons.HomeBorder,
        iconTextId = R.string.home_route_title,
        titleTextId = R.string.app_name,
    ),
    PROJECT(
        selectedIcon = WanIcons.Project,
        unselectedIcon = WanIcons.ProjectBroder,
        iconTextId = R.string.project_route_title,
        titleTextId = R.string.app_name,
    )
}