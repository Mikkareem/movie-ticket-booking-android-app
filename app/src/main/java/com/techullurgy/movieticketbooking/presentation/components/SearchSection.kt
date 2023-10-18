package com.techullurgy.movieticketbooking.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.capitalize
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.unit.dp

@Composable
fun SearchSection() {
    var searchValue by remember { mutableStateOf("") }
    var searchMode by rememberSaveable { mutableStateOf(SearchMode.SEARCH_BY_MOVIE) }

    TextField(
        value = searchValue,
        onValueChange = { searchValue = it },
        leadingIcon = {
            Icon(imageVector = Icons.Default.Search, contentDescription = "Search")
        },
        placeholder = {
            Text(text = searchMode.getSearchModeSearchStr())
        },
        modifier = Modifier.fillMaxWidth()
    )
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = "Search By: ")
        Box(
            modifier = Modifier
                .clickable { searchMode = SearchMode.SEARCH_BY_MOVIE }
                .padding(12.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Movie",
                fontWeight = if(searchMode == SearchMode.SEARCH_BY_MOVIE) FontWeight.Bold else null,
                color = if(searchMode == SearchMode.SEARCH_BY_MOVIE) Color.Magenta else Color.Black
            )
        }
        Box(
            modifier = Modifier
                .clickable { searchMode = SearchMode.SEARCH_BY_THEATRE }
                .padding(12.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Theatre",
                fontWeight = if(searchMode == SearchMode.SEARCH_BY_THEATRE) FontWeight.Bold else null,
                color = if(searchMode == SearchMode.SEARCH_BY_THEATRE) Color.Magenta else Color.Black
            )
        }
    }
}

private enum class SearchMode {
    SEARCH_BY_MOVIE, SEARCH_BY_THEATRE;

    fun getSearchModeSearchStr(): String = name.lowercase().let {
        val searchMode = it.split("_").last().capitalize(Locale.current)
        "Search $searchMode"
    }
}