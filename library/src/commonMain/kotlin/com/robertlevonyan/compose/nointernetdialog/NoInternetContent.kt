package com.robertlevonyan.compose.nointernetdialog

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.TileMode

@Composable
fun NoInternetContent() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.linearGradient(
                    colors = listOf(gradientStartColor, gradientEndColor),
                    tileMode = TileMode.Clamp
                )
            )
    ) {

    }
}