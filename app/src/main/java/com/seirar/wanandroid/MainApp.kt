package com.seirar.wanandroid

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.seirar.wanandroid.ui.components.WanTopBar

@Composable
fun MainApp() {

    Scaffold(
        topBar = {
            WanTopBar()
        }
    ) { paddingValues ->

        Box(
            modifier = Modifier.fillMaxSize().padding(paddingValues)
        )

    }

}