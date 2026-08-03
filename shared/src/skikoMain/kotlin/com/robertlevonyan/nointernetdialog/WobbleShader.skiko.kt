package com.robertlevonyan.nointernetdialog

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asComposeRenderEffect
import androidx.compose.ui.graphics.graphicsLayer
import org.jetbrains.skia.ImageFilter
import org.jetbrains.skia.RuntimeEffect
import org.jetbrains.skia.RuntimeShaderBuilder

@Composable
actual fun Modifier.wobbleRenderEffect(time: Float): Modifier {
    val shaderBuilder = remember {
        RuntimeShaderBuilder(RuntimeEffect.makeForShader(WOBBLE_SHADER))
    }
    return this.graphicsLayer {
        shaderBuilder.uniform("iResolution", size.width, size.height)
        shaderBuilder.uniform("iTime", time)
        renderEffect = ImageFilter
            .makeRuntimeShader(shaderBuilder, "contents", null)
            .asComposeRenderEffect()
    }
}
