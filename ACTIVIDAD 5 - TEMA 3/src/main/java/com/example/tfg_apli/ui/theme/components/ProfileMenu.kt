package com.example.tfg_apli.ui.theme.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import com.example.tfg_apli.ui.theme.PrimaryColor
import com.example.tfg_apli.ui.theme.ErrorColor

@Composable
fun ProfileMenu(
    onLogout: () -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    Box {
        IconButton(onClick = { expanded = true }) {
            Icon(
                Icons.Default.AccountCircle,
                contentDescription = "Perfil",
                tint = PrimaryColor
            )
        }

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            DropdownMenuItem(
                text = { Text("Configuración") },
                onClick = { /* TODO */ },
                leadingIcon = {
                    Icon(Icons.Default.Settings, contentDescription = null)
                }
            )
            Divider()
            DropdownMenuItem(
                text = { Text("Cerrar sesión", color = ErrorColor) },
                onClick = {
                    expanded = false
                    onLogout()
                },
                leadingIcon = {
                    Icon(
                        Icons.Default.ExitToApp,
                        contentDescription = null,
                        tint = ErrorColor
                    )
                }
            )
        }
    }
}