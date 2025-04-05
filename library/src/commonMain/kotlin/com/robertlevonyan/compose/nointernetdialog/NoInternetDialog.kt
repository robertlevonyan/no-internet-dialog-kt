package com.robertlevonyan.compose.nointernetdialog

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.window.Dialog

@Composable
fun NoInternetDialog(
    onDismissRequest: () -> Unit,
) {
    Dialog(onDismissRequest) {
        Box(modifier = Modifier.fillMaxSize()) {
            NoInternetContent()
        }
    }
}
