package com.techullurgy.movieticketbooking.presentation.layouts

import androidx.compose.foundation.gestures.rememberTransformableState
import androidx.compose.foundation.gestures.transformable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.unit.IntSize

@Composable
internal fun ZoomableContainer(
    modifier: Modifier = Modifier,
    horizontalInitialScale: Float = 1f,
    verticalInitialScale: Float = 1f,
    horizontalMinScale: Float = 1f,
    horizontalMaxScale: Float = 4f,
    verticalMinScale: Float = 1f,
    verticalMaxScale: Float = 4f,
    zoomableContent: @Composable BoxScope.() -> Unit
) {
    var scaleX by remember { mutableFloatStateOf(horizontalInitialScale) }
    var scaleY by remember { mutableFloatStateOf(verticalInitialScale) }
    var offset by remember { mutableStateOf(Offset.Zero) }

    var srcSize by remember { mutableStateOf(IntSize.Zero) }

    val transformableState = rememberTransformableState { zoomChange: Float, panChange: Offset, _: Float ->
        scaleX = (scaleX * zoomChange).coerceIn(horizontalMinScale, horizontalMaxScale)
        scaleY = (scaleY * zoomChange).coerceIn(verticalMinScale, verticalMaxScale)
        offset += panChange
    }

    Box(
        modifier = Modifier.clip(RectangleShape)
    ) {
        Box(
            modifier = modifier
                .onSizeChanged {
                    srcSize = it
                }
                .transformable(transformableState)
                .graphicsLayer {
                    this.scaleX = scaleX
                    this.scaleY = scaleY

                    val dstSizeWidthInPx = srcSize.width.toFloat() * this.scaleX
                    val dstSizeHeightInPx = srcSize.height.toFloat() * this.scaleY

                    val absTranslateX = (dstSizeWidthInPx - srcSize.width.toFloat())
                    val absTranslateY = (dstSizeHeightInPx - srcSize.height.toFloat())

                    offset = offset.zoomCoerceIn(absTranslateX, absTranslateY)

                    translationX = offset.x
                    translationY = offset.y
                },
            contentAlignment = Alignment.Center,
            content = zoomableContent
        )
    }
}


private fun Offset.zoomCoerceIn(x: Float, y: Float): Offset = run {
    Offset(
        this.x.coerceIn(-x/2, x/2),
        this.y.coerceIn(-y/2, y/2)
    )
}