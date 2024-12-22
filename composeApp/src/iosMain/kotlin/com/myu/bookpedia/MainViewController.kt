package com.myu.bookpedia

import androidx.compose.ui.window.ComposeUIViewController
import com.myu.bookpedia.app.App
import com.myu.bookpedia.di.initKoin

fun MainViewController() = ComposeUIViewController(
    configure = {
        initKoin()
    }
) {
    App()
}