package com.user.music.ui.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
 fun SortSection(
    onSortByName: () -> Unit,
    onSortByDuration: () -> Unit
) {
    Row(
        modifier = Modifier
            .padding(horizontal = 12.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        FilterChip(
            selected = false,
            onClick = onSortByName,
            label = { Text("A–Z") }
        )
        FilterChip(
            selected = false,
            onClick = onSortByDuration,
            label = { Text("Duration") }
        )
    }
}