package com.robertlevonyan.compose.demo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.robertlevonyan.compose.nointernetdialog.NoInternetContent

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            App()
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
