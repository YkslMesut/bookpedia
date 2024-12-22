package com.myu.bookpedia

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.myu.bookpedia.app.App
import com.myu.bookpedia.di.initKoin

fun main() {
    initKoin()
    application {

        Window(
            onCloseRequest = ::exitApplication,
            title = "BookPedia",
        ) {
            App()
        }
    }
}