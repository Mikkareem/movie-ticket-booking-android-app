package com.techullurgy.movieticketbooking.presentation.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.drawscope.translate
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.techullurgy.movieticketbooking.domain.Seat

@Composable
fun PriceList(
    categories: List<Seat>
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        categories.forEach {
            PriceTag(seat = it)
        }
    }
}

@Composable
private fun PriceTag(
    seat: Seat,
    modifier: Modifier = Modifier
) {

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceAround,
        modifier = modifier
            .padding(vertical = 4.dp)
    ) {
        Canvas(
            modifier = Modifier.size(30.dp),
            onDraw = {
                translate(top = -(size.width * 0.1f)) {
                    seatView(seat, size, Offset.Zero)
                }
            }
        )

        Spacer(modifier = Modifier.width(6.dp))

        Column(
            verticalArrangement = Arrangement.Center
        ) {
            Text(text = "Rs. ${seat.seatPrice}", fontWeight = FontWeight.Bold)

            val seatCategory = seat.seatCategory.getCategoryStr()
            Text(text = seatCategory, fontWeight = FontWeight.Light, fontSize = 14.sp)
        }
    }
}