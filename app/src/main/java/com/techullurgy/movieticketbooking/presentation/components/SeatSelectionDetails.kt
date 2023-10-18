package com.techullurgy.movieticketbooking.presentation.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp
import com.techullurgy.movieticketbooking.domain.Seat

@Composable
internal fun SeatSelectionDetails(
    totalSeatsSelected: Int,
    totalAmount: Double,
    selectedSeats: List<Seat>
) {
    AnimatedVisibility(visible = totalSeatsSelected > 0) {
        var detailOpenForSeatSelection by remember { mutableStateOf(false) }
        var detailOpenForTotalCost by remember { mutableStateOf(false) }

        Column {
            Row {
                AnnotatedText(leftText = "Total Seats Selected", rightText = "$totalSeatsSelected")
                Text(
                    text = if(!detailOpenForSeatSelection) "Details" else "Hide Details",
                    color = Color.Blue,
                    fontSize = 14.sp,
                    modifier = Modifier.clickable {
                        detailOpenForSeatSelection = !detailOpenForSeatSelection
                    }
                )
            }
            AnimatedVisibility(visible = detailOpenForSeatSelection) {
                val groupedCategories = selectedSeats.groupBy { it.seatCategory }
                Column {
                    groupedCategories.forEach {
                        val category = it.key.getCategoryStr()
                        val categoryCount = it.value.size

                        Text(text = "$category: $categoryCount")
                    }
                }
            }
            Row {
                AnnotatedText(leftText = "Total Cost", rightText = "Rs. $totalAmount")
                Text(
                    text = if(!detailOpenForTotalCost) "Details" else "Hide Details",
                    color = Color.Blue,
                    fontSize = 14.sp,
                    modifier = Modifier.clickable {
                        detailOpenForTotalCost = !detailOpenForTotalCost
                    }
                )
            }
            AnimatedVisibility(visible = detailOpenForTotalCost) {
                val groupedCategories = selectedSeats.groupBy { it.seatCategory }
                Column {
                    groupedCategories.forEach {
                        val category = it.key.getCategoryStr()
                        val categoryCount = it.value.size
                        val amount = it.value.first().seatPrice

                        Text(text = "$category: $categoryCount x $amount")
                    }
                }
            }
        }
    }
}