package com.techullurgy.movieticketbooking.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.techullurgy.movieticketbooking.R
import com.techullurgy.movieticketbooking.domain.Movie

@Composable
fun BookableMovies(
    movies: List<Movie>,
    navigateToTheatresListScreen: (Long) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        movies.forEach {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { navigateToTheatresListScreen(it.id) },
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                if(it.posterUrl.isEmpty()) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_launcher_background),
                        contentDescription = null,
                        modifier = Modifier.size(80.dp),
                        contentScale = ContentScale.Crop
                    )
                } else {
                    AsyncImage(
                        model = it.posterUrl,
                        contentDescription = null,
                        modifier = Modifier.clipToBounds().width(150.dp).height(100.dp),
                        contentScale = ContentScale.Crop
                    )
                }

                Text(
                    text = it.name,
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 18.sp,
                    fontStyle = FontStyle.Italic
                )

            }
            Divider()
        }
    }
}