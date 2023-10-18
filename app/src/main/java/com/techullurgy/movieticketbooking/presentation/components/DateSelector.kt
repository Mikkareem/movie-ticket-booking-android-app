package com.techullurgy.movieticketbooking.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.capitalize
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.techullurgy.movieticketbooking.presentation.ColorConstant
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime


@Composable
internal fun DateSelector(
    dateList: Set<LocalDate>,
    selectedDateIndex: Int,
    onDateSelected: (LocalDate) -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.End,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
    ) {
        val availableDates = dateList.take(7)

        val textMeasurer = rememberTextMeasurer()
        val density = LocalDensity.current

        availableDates.forEachIndexed { index, date ->

            val textColor = if(index == selectedDateIndex) {
                ColorConstant.selectionColor
            } else {
                LocalTextStyle.current.color
            }

            val fontWeight = if(index == selectedDateIndex) {
                FontWeight.Bold
            } else null

            val dateStr = if(Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date == date) {
                "Today"
            } else {
                "${date.dayOfMonth.toString().padStart(2, '0')} ${date.month.name.lowercase().capitalize(Locale.current).take(3)}"
            }

            if(dateStr.contains(' ')) {
                val textWidth = textMeasurer.measure(
                    text = dateStr.split(" ").last(),
                    style = LocalTextStyle.current.copy(textAlign = TextAlign.Center, fontWeight = fontWeight)
                ).size.width

                with(density) {
                    Text(
                        text = dateStr,
                        textAlign = TextAlign.Center,
                        color = textColor,
                        fontWeight = fontWeight,
                        modifier = Modifier
                            .width(textWidth.toDp())
                            .clickable { onDateSelected(date) }
                    )
                }
            } else {
                Text(
                    text = dateStr,
                    textAlign = TextAlign.Center,
                    color = textColor,
                    fontWeight = fontWeight,
                    modifier = Modifier
                        .clickable { onDateSelected(date) }
                )
            }

            Spacer(modifier = Modifier.width(4.dp))
        }
    }
}