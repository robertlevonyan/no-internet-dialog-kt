package com.robertlevonyan.nointernetdialog

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import kotlin.math.cos
import kotlin.math.sin

/**
 * Distorts the content with a jelly-like ripple driven by [time] (seconds).
 * No-op on Android below API 33, where AGSL RenderEffects are unavailable.
 */
@Composable
expect fun Modifier.wobbleRenderEffect(time: Float): Modifier

@Composable
fun Modifier.rememberWobbleModifier(time: Float): Modifier {
    val wobbleX = sin(time * 2f) * 12f
    val wobbleY = cos(time * 1.6f) * 4f
    return this.graphicsLayer {
        translationX = wobbleX
        translationY = wobbleY
    }
}
