package com.techullurgy.movieticketbooking

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.ProvideTextStyle
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.unit.sp
import com.techullurgy.movieticketbooking.navigation.MainNavigation
import com.techullurgy.movieticketbooking.ui.theme.MovieTicketBookingTheme
import com.techullurgy.movieticketbooking.ui.theme.PoppinsFontFamily

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MovieTicketBookingTheme {
                val currentTextStyle = LocalTextStyle.current

                val poppinsTextStyle = remember(currentTextStyle) {
                    currentTextStyle.copy(
                        fontFamily = PoppinsFontFamily,
                        platformStyle = PlatformTextStyle(includeFontPadding = false),
                        letterSpacing = 0.sp
                    )
                }

                ProvideTextStyle(value = poppinsTextStyle) {
                    MainNavigation()
                }
            }
        }
    }
}

@Composable
internal fun PreviewTheme(
    content: @Composable () -> Unit
) {
    MovieTicketBookingTheme {

        val currentTextStyle = LocalTextStyle.current

        val poppinsTextStyle = remember(currentTextStyle) {
            currentTextStyle.copy(
                fontFamily = PoppinsFontFamily,
                platformStyle = PlatformTextStyle(includeFontPadding = false),
                letterSpacing = 0.sp
            )
        }

        ProvideTextStyle(value = poppinsTextStyle) {
            content()
        }
    }
}