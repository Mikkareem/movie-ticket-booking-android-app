package com.techullurgy.movieticketbooking.presentation.components

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun SeatSelectionButtonSection(
    totalSeatsSelected: Int,
    onSuccessClick: () -> Unit = {},
    onCancelClick: () -> Unit = {}
) {
    Crossfade(totalSeatsSelected, label = "buttons_section") {
        if(it > 0) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Buttons(
                    totalSeatsSelected = it,
                    onSuccessClick = onSuccessClick,
                    onCancelClick = onCancelClick
                )
            }
        } else {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(50))
                    .background(color = Color.Gray)
                    .padding(vertical = 12.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(text = "Please Select Seats to continue further", color = Color.White)
            }
        }
    }
}

@Composable
private fun RowScope.Buttons(
    totalSeatsSelected: Int,
    onSuccessClick: () -> Unit,
    onCancelClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth(0.4f)
            .clip(RoundedCornerShape(20.dp))
            .background(color = Color(0xFFF28282))
            .clickable { onCancelClick() }
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "Cancel",
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp,
            color = Color.Black
        )
    }
    Spacer(modifier = Modifier.width(16.dp))
    Box(
        modifier = Modifier
            .weight(1f)
            .clip(RoundedCornerShape(20.dp))
            .background(color = Color(0xFFA11A1A))
            .clickable { onSuccessClick() }
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "Book $totalSeatsSelected Ticket${if(totalSeatsSelected > 1) "s" else ""}",
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp,
            color = Color.White
        )
    }
}