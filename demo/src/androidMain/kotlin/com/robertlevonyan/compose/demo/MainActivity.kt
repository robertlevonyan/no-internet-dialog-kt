package com.robertlevonyan.compose.demo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.robertlevonyan.compose.nointernetdialog.NoInternetContent
import com.robertlevonyan.compose.nointernetdialog.NoInternetDialog

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
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
    }
}

@PreviewLightDark
@Composable
private fun NoInternetContentPreview() {
    MaterialTheme {
        Box(modifier = Modifier.fillMaxSize()) {
            NoInternetContent()
        }
    }
}
