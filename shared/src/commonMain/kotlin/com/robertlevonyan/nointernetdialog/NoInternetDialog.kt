package com.robertlevonyan.nointernetdialog

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.Dialog

@Composable
fun NoInternetDialog(
    onDismissRequest: () -> Unit,
) {
    Dialog(onDismissRequest) {
        Card(modifier = Modifier.fillMaxSize(0.8f)) {
            NoInternetContent()
        }
    }
}
