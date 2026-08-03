package com.robertlevonyan.nointernetdialog

import android.graphics.RenderEffect
import android.graphics.RuntimeShader
import android.os.Build
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asComposeRenderEffect
import androidx.compose.ui.graphics.graphicsLayer

@Composable
actual fun Modifier.wobbleRenderEffect(time: Float): Modifier {
    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU) return this

    val shader = remember { RuntimeShader(WOBBLE_SHADER) }
    return this.graphicsLayer {
        shader.setFloatUniform("iResolution", size.width, size.height)
        shader.setFloatUniform("iTime", time)
        renderEffect = RenderEffect
            .createRuntimeShaderEffect(shader, "contents")
            .asComposeRenderEffect()
    }
}
