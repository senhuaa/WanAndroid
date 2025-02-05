package com.seirar.wanandroid.ui.components

import android.annotation.SuppressLint
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("SetJavaScriptEnabled")
@Composable
fun WanWebView(
    url: String,
    title: String,
    navController: NavController
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(
                    text = title,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                ) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        AndroidView(
            factory = { context ->
                WebView(context).apply {
                    settings.javaScriptEnabled = true
                    settings.domStorageEnabled = true
                    settings.loadWithOverviewMode = true
                    settings.useWideViewPort = true

                    webViewClient = object : WebViewClient() {
                        override fun onPageFinished(view: WebView?, url: String?) {
                            super.onPageFinished(view, url)
                        }
                    }
                    loadUrl(url)
                }
            },
            modifier = Modifier.padding(paddingValues)
        )
    }
}

fun NavGraphBuilder.webviewGraph(
    navController: NavController
) {
    composable(
        route = "webview?url={url}&title={title}",
        arguments = listOf(
            navArgument("url") { type = NavType.StringType },
            navArgument("title") {type = NavType.StringType }
        )
    ) { navBackStackEntry ->
        val url = navBackStackEntry.arguments?.getString("url") ?: ""
        val title = navBackStackEntry.arguments?.getString("title") ?: ""

        WanWebView(
            url = url,
            title = title,
            navController = navController
        )
    }
}