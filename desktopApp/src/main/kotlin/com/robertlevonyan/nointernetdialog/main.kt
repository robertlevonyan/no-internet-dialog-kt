package com.robertlevonyan.nointernetdialog

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "No Internet Dialog",
    ) {
        App()
    }
}
