package com.techullurgy.movieticketbooking.presentation.components

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp

@Composable
internal fun AnnotatedText(
    leftText: String,
    rightText: String,
    modifier: Modifier = Modifier,
    fontSize: TextUnit = 18.sp
) {
    Text(
        text = buildAnnotatedString {
            withStyle(SpanStyle(color = Color.White)) {
                append("$leftText: ")
            }
            append(rightText)
        },
        modifier = modifier,
        fontSize = fontSize,
        maxLines = 1,
        softWrap = false,
        overflow = TextOverflow.Ellipsis
    )
}