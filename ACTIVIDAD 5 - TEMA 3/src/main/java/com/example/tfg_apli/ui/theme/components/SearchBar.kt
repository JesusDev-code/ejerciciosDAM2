package com.example.tfg_apli.ui.theme.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.tfg_apli.ui.theme.Neutral20
import com.example.tfg_apli.ui.theme.Neutral90
import com.example.tfg_apli.ui.theme.PrimaryColor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBar(
    query: String,
    onQueryChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    TextField(
        value = query,
        onValueChange = onQueryChange,
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(Neutral90),
        placeholder = {
            Text(
                "Buscar diarios...",
                style = MaterialTheme.typography.bodyMedium,
                color = Neutral20.copy(alpha = 0.5f)
            )
        },
        leadingIcon = {
            Icon(
                Icons.Default.Search,
                contentDescription = null,
                tint = Neutral20.copy(alpha = 0.5f)
            )
        },
        colors = TextFieldDefaults.textFieldColors(
            containerColor = Neutral90,
            focusedIndicatorColor = PrimaryColor,
            unfocusedIndicatorColor = Color.Transparent
        ),
        singleLine = true
    )
}