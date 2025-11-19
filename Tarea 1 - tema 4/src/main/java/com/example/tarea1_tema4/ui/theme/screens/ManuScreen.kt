package com.example.tarea1_tema4.ui.theme.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.tarea1_tema4.ui.theme.*
import com.example.tarea1_tema4.ui.theme.components.OptionCard
import com.example.tarea1_tema4.ui.theme.components.WelcomeModal

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MenuScreen(navController: NavController) {
    var searchQuery by remember { mutableStateOf("") }
    var showWelcomeModal by remember { mutableStateOf(true) }

    // OPCIONES EN UNA LISTA PARA FILTRADO
    val allOptions = listOf(
        OptionCardData(
            icon = "🔔",
            title = "Sonido Corto Local",
            description = "Reproduce efectos de sonido almacenados localmente con SoundPool",
            tag = "SoundPool",
            nav = { navController.navigate("soundpool") }
        ),
        OptionCardData(
            icon = "🎵",
            title = "Audio Largo Remoto",
            description = "Transmite música desde internet con ExoPlayer",
            tag = "ExoPlayer",
            nav = { navController.navigate("audio") }
        ),
        OptionCardData(
            icon = "🎬",
            title = "Vídeo Remoto",
            description = "Reproduce vídeos en streaming con ExoPlayer",
            tag = "ExoPlayer",
            nav = { navController.navigate("video") }
        ),
        OptionCardData(
            icon = "📷",
            title = "Captura de Imagen",
            description = "Utiliza la cámara del dispositivo para capturar fotos",
            tag = "CameraX",
            nav = { navController.navigate("camera") }
        ),
        OptionCardData(
            icon = "📁",
            title = "Galería de Fotos",
            description = "Explora y amplia las fotos que has capturado",
            tag = "Galería",
            nav = { navController.navigate("gallery") }
        )
    )

    // FILTRO INTELIGENTE
    val filteredOptions = allOptions.filter {
        val query = searchQuery.trim().lowercase()
        query.isBlank() ||
                it.title.lowercase().contains(query) ||
                it.description.lowercase().contains(query) ||
                it.tag.lowercase().contains(query)
    }

    Box(modifier = Modifier.fillMaxSize()) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(Brush.verticalGradient(listOf(TealBackground, TealLight)))
        ) {
            // Header
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Brush.linearGradient(listOf(TealPrimary, TealPrimaryDark)))
                        .padding(vertical = 40.dp, horizontal = 24.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        // Logo
                        Box(
                            modifier = Modifier
                                .size(80.dp)
                                .clip(CircleShape)
                                .background(CardBackground),
                            contentAlignment = Alignment.Center
                        ) {
                            Text("🎵", fontSize = 40.sp)
                        }
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            text = "Multimedia Player",
                            fontSize = 28.sp,
                            fontWeight = FontWeight.Bold,
                            color = TextOnPrimary
                        )
                        Text(
                            text = "Tu centro de entretenimiento multimedia",
                            fontSize = 15.sp,
                            color = TextOnPrimary.copy(alpha = 0.9f)
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        // Demo Badge
                        Surface(
                            shape = RoundedCornerShape(20.dp),
                            color = PinkDemo,
                            shadowElevation = 4.dp
                        ) {
                            Row(
                                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(6.dp)
                            ) {
                                Text("🎨", fontSize = 13.sp)
                                Text(
                                    "Sé un DJ",
                                    fontSize = 13.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    color = TextOnPrimary
                                )
                            }
                        }
                    }
                }
            }
            // Content
            item {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(24.dp)
                ) {
                    Text(
                        text = "Bienvenido/a",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = TealPrimary
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Explorador Multimedia",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = TextPrimary
                    )
                    Spacer(modifier = Modifier.height(24.dp))

                    // Search Bar
                    OutlinedTextField(
                        value = searchQuery,
                        onValueChange = { searchQuery = it },
                        placeholder = { Text("Buscar en tus opciones multimedia...") },
                        leadingIcon = { Text("🔍", fontSize = 18.sp) },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(16.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedContainerColor = CardBackground,
                            unfocusedContainerColor = BackgroundLight,
                            focusedBorderColor = TealPrimary,
                            unfocusedBorderColor = BorderLight
                        )
                    )
                    Spacer(modifier = Modifier.height(24.dp))
                    // Opciones filtradas con sus Spacers respetados
                    filteredOptions.forEach { option ->
                        OptionCard(
                            icon = option.icon,
                            title = option.title,
                            description = option.description,
                            tag = option.tag,
                            onClick = option.nav
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                    }
                    if (filteredOptions.isEmpty()) {
                        Text(
                            "No hay resultados que coincidan.",
                            color = TextPrimary,
                            modifier = Modifier.padding(16.dp)
                        )
                    }
                }
            }
        }
        // Welcome Modal original
        if (showWelcomeModal) {
            WelcomeModal(onDismiss = { showWelcomeModal = false })
        }
    }
}

// Data class para las opciones del menú
data class OptionCardData(
    val icon: String,
    val title: String,
    val description: String,
    val tag: String,
    val nav: () -> Unit
)
