package com.robertlevonyan.compose.nointernetdialog

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.EaseInOut
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.TweenSpec
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.withInfiniteAnimationFrameMillis
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.WifiOff
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.graphics.vector.Group
import androidx.compose.ui.graphics.vector.Path
import androidx.compose.ui.graphics.vector.PathParser
import androidx.compose.ui.graphics.vector.VectorComposable
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.robertlevonyan.compose.library.generated.resources.Res
import com.robertlevonyan.compose.library.generated.resources.grave
import kotlinx.coroutines.delay
import org.jetbrains.compose.resources.painterResource

@Composable
fun NoInternetContent() {
    val imageSize = 180.dp
    val viewportSize = 512f
    val duration = 3000

    val scale = remember { Animatable(0f) }
    val scaleAnimationSpec: TweenSpec<Float> = tween(
        durationMillis = 500,
        easing = EaseInOut,
        delayMillis = 1000,
    )

    LaunchedEffect(Unit) {
        while (true) {
            scale.animateTo(
                targetValue = 0.5f,
                animationSpec = scaleAnimationSpec,
            )
            scale.animateTo(
                targetValue = 2.2f,
                animationSpec = scaleAnimationSpec,
            )
            delay(5000)
        }
    }
    val transition = rememberInfiniteTransition()
    val translationY by transition.animateFloat(
        initialValue = 0f,
        targetValue = -30f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = duration,
                easing = EaseInOut,
            ),
            repeatMode = RepeatMode.Reverse,
        )
    )
    val ghost = rememberVectorPainter(
        defaultWidth = imageSize,
        defaultHeight = imageSize,
        viewportWidth = viewportSize,
        viewportHeight = viewportSize,
        autoMirror = true,
    ) { viewPortWidth, viewPortHeight ->
        Group(name = "ghost") {
            Group(
                name = "body",
                translationY = translationY,
                pivotY = viewportSize,
                scaleX = scale.value,
                scaleY = scale.value,
            ) {
                GhostBody()
            }
            Group(
                name = "face",
                translationY = translationY,
                pivotY = viewportSize,
                scaleX = scale.value,
                scaleY = scale.value,
            ) {
                GhostFace()
            }
        }
    }

    val time by produceState(0f) {
        while (true) {
            withInfiniteAnimationFrameMillis {
                value = it / 1000f
            }
        }
    }

//    val shader = RuntimeShader(WOBBLE_SHADER)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.linearGradient(
                    colors = listOf(gradientStartColor, gradientEndColor),
                    tileMode = TileMode.Clamp
                )
            ),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Box(modifier = Modifier.size(300.dp)) {
            Image(
                modifier = Modifier.align(Alignment.TopEnd),
//                    .onSizeChanged { size ->
//                        shader.setFloatUniform(
//                            "resolution",
//                            size.width.toFloat(),
//                            size.height.toFloat()
//                        )
//                    }
//                    .graphicsLayer {
//                        shader.setFloatUniform("time", time)
//                        renderEffect = RenderEffect
//                            .createRuntimeShaderEffect(
//                                shader,
//                                "contents"
//                            )
//                            .asComposeRenderEffect()
//                    }
                painter = ghost,
                contentDescription = null,
                contentScale = ContentScale.Crop,
            )
            Image(
                modifier = Modifier.size(230.dp).align(Alignment.Center),
                painter = painterResource(Res.drawable.grave),
                contentDescription = null,
            )
            Image(
                modifier = Modifier
                    .padding(end = 24.dp, bottom = 12.dp)
                    .size(40.dp)
                    .align(Alignment.Center),
                imageVector = Icons.Rounded.WifiOff,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                colorFilter = ColorFilter.tint(color = noSignalColor)
            )
        }
    }
}

//@Language("AGSL")
//private val WOBBLE_SHADER = """
//    uniform float2 resolution;
//    uniform float time;
//    uniform shader contents;
//    vec4 main(in vec2 fragCoord) {
//    	vec2 uv = fragCoord.xy / resolution.xy * 0.8 + 0.1;
//
//        uv += sin(time * vec2(1.0, 2.0) + uv* 2.0) * 0.01;
//
//        return contents.eval(uv * resolution.xy);
//    }
//""".trimIndent()

@Composable
@VectorComposable
private fun GhostBody() {
    val shadowColor = Color(0xffe9e8e8)
    Path(
        pathData = PathParser().parsePathString(
            """
                            M198.3,334.4c2.5,2.3 3.2,7 3.2,8.4 0.7,12.5 1.8,41.8 -3.7,50.9 -3.1,5.1 -16,3.8 -14.1,-10.4 0.5,-3.6 -16.4,5.3 -16.7,0.2 -0.5,-7.6 -6.7,-12.8 -6.9,-12.1 -2.4,11.5 -7.7,23.8 -11.1,33.3 -8.1,22.8 -35.2,76.5 -49.2,76.5s-1.5,-4.9 -5.5,-7.6c-3.2,-2.2 -10.8,2.3 -16.6,-2.4 -4.6,-3.7 7.9,-15.9 5.7,-20.4 -2.2,-4.4 -17.2,2.5 -18.4,-2.3 -1.5,-5.8 11.5,-19.8 9.8,-29.2 -0.8,-4.1 -15.2,0 -15.6,-4.2 -0.2,-2.5 11.8,-22.7 17.1,-37.7 2.3,-6.7 1.1,-14.6 0.6,-15.7 -0.5,-1.2 -4.9,0 -7.2,3.3 -1.6,2.2 -0.7,6.5 -3.4,7.3 -6.3,1.7 -13.2,-7.4 -15.8,-5.3 -2.3,1.8 -0.9,6.5 -3.3,9.1 -5.5,5.7 -10.8,0.6 -12.6,-2.1 -2.5,-3.8 0.4,-16.3 2.3,-26.5 3.2,-17 8.4,-33.1 16,-32.6 14.6,1 28.2,19.7 28.5,18.9 2.8,-9.6 6,-18 10.6,-23.7 6.3,-7.7 11.5,-10.8 28.9,-10.8s25.6,4.2 32.6,16.2c5.2,8.9 7,17.2 7.6,28.1 0,0.3 0.1,0.6 0.3,0.6 2.9,1.8 14.6,-14.1 31,-12 1.6,0.2 4.2,0.7 5.9,2.3h0Z
                        """.trimIndent()
        ).toNodes(),
        fill = SolidColor(Color.White),
    )
    Path(
        pathData = PathParser().parsePathString(
            """
                           M166.5,379.9h0v-0.5c1.6,-11.8 4.8,-23.3 9.5,-34.3h0c-6.7,7.7 -12,16.5 -15.6,26h0c0.1,-0.4 4.5,3.2 6.2,8.8Z
                        """.trimIndent()
        ).toNodes(),
        fill = SolidColor(shadowColor),
    )
    Path(
        pathData = PathParser().parsePathString(
            """
                            M189.4,345.1h0c-2.4,12.8 -6.7,25.3 -12.5,37l-0.9,1.7h0c4.1,-1.1 8,-2.4 7.8,-0.6l0.6,-1.9c4,-11.6 5.7,-23.9 4.9,-36.2h0Z
                        """.trimIndent()
        ).toNodes(),
        fill = SolidColor(shadowColor),
    )
    Path(
        pathData = PathParser().parsePathString(
            """
                            M77.7,369.7h0s0,0 0,0Z
                        """.trimIndent()
        ).toNodes(),
        fill = SolidColor(shadowColor),
    )
    Path(
        pathData = PathParser().parsePathString(
            """
                            M68,331.7c1.2,11.1 1.6,22.4 0.9,33.6l-0.2,2.5h0c0.2,-1.1 0.4,-2.1 1,-2.9 2.4,-3.3 6.8,-4.5 7.2,-3.3 0.2,0.5 0.6,2.6 0.7,5.3h0c0,-11.7 -3,-23.1 -8.7,-33.3l-1.1,-1.9h0Z
                        """.trimIndent()
        ).toNodes(),
        fill = SolidColor(shadowColor),
    )
    Path(
        pathData = PathParser().parsePathString(
            """
                            M56,337.3h0c-4.1,9.5 -6.4,19.6 -6.9,29.9l-0.2,4.7c0.3,-2 0.4,-4.1 1.7,-5.1 1.6,-1.3 4.9,1.7 8.7,3.8l-1.9,-6.9c-2.4,-8.6 -2.9,-17.7 -1.3,-26.5h0Z
                        """.trimIndent()
        ).toNodes(),
        fill = SolidColor(shadowColor),
    )
    Path(
        pathData = PathParser().parsePathString(
            """
                            M70.1,417.1c2.5,0.1 4.5,0.5 4.8,2 0.9,4.7 -1.9,10.5 -4.8,15.9h0c10.9,-19.2 17.6,-40.6 19.5,-62.7v-0.3s-4.8,15.5 -4.8,15.5c-3.3,10.6 -8.2,20.6 -14.7,29.5h0Z
                        """.trimIndent()
        ).toNodes(),
        fill = SolidColor(shadowColor),
    )
    Path(
        pathData = PathParser().parsePathString(
            """
                           M113.2,386.8h0c-7.3,17.5 -16.3,34.2 -27,49.8l-8.5,12.4h0c2.8,-0.2 5.1,0 5.8,1.5 1.3,2.6 -2.3,7.8 -4.7,12.4l11.5,-19.4c10.4,-17.6 18.1,-36.8 22.8,-56.7h0Z
                        """.trimIndent()
        ).toNodes(),
        fill = SolidColor(shadowColor),
    )
    Path(
        pathData = PathParser().parsePathString(
            """
                            M122,419.4l3.4,-11.5h0c-8.1,22.1 -19.4,42.9 -33.4,61.8l-2.4,3.2h0c2,-0.2 3.7,-0.2 4.8,0.5 2.1,1.5 2.3,3.5 2.7,5.2l1.5,-2.8c9.6,-18 17.4,-36.9 23.3,-56.4h0Z
                        """.trimIndent()
        ).toNodes(),
        fill = SolidColor(shadowColor),
    )
}

@Composable
@VectorComposable
private fun GhostFace() {
    val faceColor = Color(0xff414042)
    Path(
        pathData = PathParser().parsePathString(
            """
                            M113.4,320.7c0.8,0.2 2.9,0.9 4.6,2.9 0.2,0.2 0.4,0.5 0.5,0.7 0.5,0.8 1.8,0.5 1.8,-0.5 0,-0.5 0,-1.1 -0.1,-1.7 -0.6,-2.9 -3.1,-5.3 -6,-5.7 -3.9,-0.5 -7.2,2.1 -8,5.7 0,0.2 0.1,0.4 0.3,0.2 0.4,-0.3 0.8,-0.5 1.3,-0.8 2.3,-1 4.4,-1 5.7,-0.9h0Z
                        """.trimIndent()
        ).toNodes(),
        fill = SolidColor(faceColor),
    )
    Path(
        pathData = PathParser().parsePathString(
            """
                            M138.2,323.6c0.8,0.2 2.9,0.9 4.6,2.9 0.2,0.2 0.4,0.5 0.5,0.7 0.5,0.8 1.8,0.4 1.8,-0.5 0,-0.6 0,-1.1 -0.1,-1.7 -0.6,-2.9 -3.1,-5.3 -6,-5.7 -3.9,-0.5 -7.2,2.1 -8,5.7 0,0.2 0.1,0.4 0.3,0.2 0.4,-0.3 0.8,-0.5 1.4,-0.8 2.3,-1 4.4,-1 5.6,-0.9h0Z
                        """.trimIndent()
        ).toNodes(),
        fill = SolidColor(faceColor),
    )
    Path(
        pathData = PathParser().parsePathString(
            """
                            M107.3,328.9c7.3,-1.6 12.4,4.7 20.7,4.8 6.6,0 8.8,-4 13.8,-2.1 2.4,0.9 5.2,3.2 5.3,5.8 0,3.5 -4.8,6 -7.3,7.3 -2.4,1.2 -7.2,3.2 -14.3,2.5 -4.2,-0.8 -14.6,-3.4 -19.4,-9.6 -0.8,-1.1 -2.6,-2.9 -2.1,-4.8 0.8,-2.7 2.7,-3.7 3.3,-3.8h0Z
                        """.trimIndent()
        ).toNodes(),
        fill = SolidColor(faceColor),
    )
}
