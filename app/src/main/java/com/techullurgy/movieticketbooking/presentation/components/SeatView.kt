package com.techullurgy.movieticketbooking.presentation.components

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.DrawStyle
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp
import com.techullurgy.movieticketbooking.domain.Seat
import com.techullurgy.movieticketbooking.domain.SeatCategory
import com.techullurgy.movieticketbooking.domain.SeatStatus

internal fun DrawScope.seatView(
    seat: Seat,
    seatSize: Size,
    at: Offset
) {
    val handleWidth = seatSize.width * 0.1f
    val handleHeight = seatSize.height * 0.5f

    val padding = seatSize.width * 0.05f

    val color = when(seat.seatCategory) {
        SeatCategory.FIRST_CLASS -> Color.White
        SeatCategory.SECOND_CLASS -> Color.Yellow
        SeatCategory.BALCONY -> Color(0xff151ac1)// Color.Red
    }

    val seatStyle = when(seat.seatStatus) {
        SeatStatus.AVAILABLE, SeatStatus.NOT_AVAILABLE -> Stroke(width = 1.dp.toPx(), cap = StrokeCap.Round, join = StrokeJoin.Round)
        SeatStatus.BOOKED, SeatStatus.SELECTED -> Fill
    }

    val seatPath = Path().apply {
        val startEndY = if(seatStyle is Fill) {
            at.y + seatSize.height
        } else {
            at.y + seatSize.height - handleHeight
        }
        moveTo(at.x + padding + handleWidth/2, startEndY)
        lineTo(at.x + padding + handleWidth/2, at.y + seatSize.height * 0.3f)
        quadraticBezierTo(
            at.x + seatSize.width / 2, at.y + seatSize.height * 0.05f,
            at.x + seatSize.width - (padding + handleWidth/2), at.y + seatSize.height * .3f
        )
        lineTo(at.x + seatSize.width - (padding + handleWidth / 2), startEndY)
    }

    val seatColor = when(seat.seatStatus) {
        SeatStatus.BOOKED -> Color.Blue
        SeatStatus.SELECTED -> Color.Magenta
        else -> color
    }

    drawPath(
        path = seatPath,
        color = seatColor,
        style = seatStyle
    )

    drawHandles(
        color = seatColor,
        seatSize = seatSize,
        style = seatStyle,
        at = at,
        padding = padding,
        handleWidth = handleWidth,
        handleHeight = handleHeight
    )
}

private fun DrawScope.drawHandles(
    color: Color,
    seatSize: Size,
    style: DrawStyle,
    at: Offset,
    padding: Float,
    handleWidth: Float,
    handleHeight: Float
) {
    drawRoundRect(
        color = color,
        topLeft = Offset(at.x + padding, at.y + seatSize.height - handleHeight),
        size = Size(handleWidth, handleHeight),
        style = style
    )

    drawRoundRect(
        color = color,
        topLeft = Offset(at.x + seatSize.width - padding - handleWidth, at.y + seatSize.height - handleHeight),
        size = Size(handleWidth, handleHeight),
        style = style
    )

    drawRoundRect(
        color = color,
        topLeft = Offset(at.x + padding + handleWidth, at.y + seatSize.height - handleWidth),
        size = Size(seatSize.width - 2*padding - 2*handleWidth, handleWidth),
        style = style
    )
}

