package org.ethereum.dappstore.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.ethereum.dappstore.ui.theme.BackgroundAccent

@Composable
fun SearchBarUi() {
    Row(
        Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp, vertical = 12.dp)
            .background(color = BackgroundAccent, shape = RoundedCornerShape(24.dp))
            .padding(12.dp)
    ) {
        Icon(
            Icons.Default.Search,
            contentDescription = "Search",
            tint = MaterialTheme.colors.secondary
        )
        Spacer(Modifier.width(12.dp))
        Text("Search for dApps...", color = MaterialTheme.colors.secondary)
    }
}