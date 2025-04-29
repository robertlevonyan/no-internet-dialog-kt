package com.robertlevonyan.compose.demo

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.robertlevonyan.compose.nointernetdialog.NoInternetContent
import com.robertlevonyan.compose.nointernetdialog.NoInternetDialog

@Composable
fun App() {
    var showDialog by remember { mutableStateOf(false) }

    if (showDialog) {
        NoInternetDialog(
            onDismissRequest = {
                showDialog = false
            }
        )
    }

    Box(modifier = Modifier.fillMaxSize()) {
        NoInternetContent()
//                Button(
//                    modifier = Modifier.align(Alignment.Center),
//                    onClick = {
//                        showDialog = true
//                    }
//                ) {
//                    Text("Show dialog")
//                }
    }
}