package com.techullurgy.movieticketbooking.presentation.layouts

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.techullurgy.movieticketbooking.presentation.ColorConstant

@Composable
fun LoadableContainer(
    isLoading: Boolean,
    backgroundColor: Color = ColorConstant.backgroundColor,
    modifier: Modifier = Modifier,
    label: String = "",
    content: @Composable ColumnScope.() -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(backgroundColor)
    ) {
        Crossfade(targetState = isLoading, label = label) {
            if (it) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            } else {
                Column {
                    content()
                }
            }
        }
    }
}