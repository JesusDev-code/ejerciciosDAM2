package com.example.tarea1_tema4.ui.theme.screens


import android.content.Context
import android.media.SoundPool
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tarea1_tema4.R
import com.example.tarea1_tema4.ui.theme.CardBackground
import com.example.tarea1_tema4.ui.theme.TealBackground
import com.example.tarea1_tema4.ui.theme.TealLight
import com.example.tarea1_tema4.ui.theme.TealPrimary
import com.example.tarea1_tema4.ui.theme.TextOnPrimary
import com.example.tarea1_tema4.ui.theme.TextPrimary
import com.example.tarea1_tema4.ui.theme.TextSecondary

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SoundPoolScreen(onBack: () -> Unit) {
    val context = LocalContext.current
    var isPlaying by remember { mutableStateOf(false) }
    var soundPool: SoundPool? by remember { mutableStateOf(null) }
    var soundId by remember { mutableStateOf(0) }

    DisposableEffect(Unit) {
        soundPool = SoundPool.Builder().setMaxStreams(1).build()
        soundId = soundPool?.load(context, R.raw.soprano, 1) ?: 0

        onDispose {
            soundPool?.release()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "SoundPool - Sonido Local",
                        fontWeight = FontWeight.Bold
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, "Volver")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = TealPrimary,
                    titleContentColor = TextOnPrimary,
                    navigationIconContentColor = TextOnPrimary
                )
            )
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(TealBackground, TealLight)
                    )
                )
                .padding(padding),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(24.dp),
                modifier = Modifier.padding(24.dp)
            ) {
                // Icon Display
                Surface(
                    modifier = Modifier.size(120.dp),
                    shape = RoundedCornerShape(24.dp),
                    color = CardBackground,
                    shadowElevation = 8.dp
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Text("🔔", fontSize = 64.sp)
                    }
                }

                Text(
                    text = "Reproductor de Sonidos",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextPrimary
                )

                Text(
                    text = "Reproduce efectos de sonido cortos almacenados localmente",
                    fontSize = 15.sp,
                    color = TextSecondary
                )

                Row(
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Button(
                        onClick = {
                            soundPool?.play(soundId, 1f, 1f, 1, 0, 1f)
                            isPlaying = true
                        },
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(16.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = TealPrimary
                        )
                    ) {
                        Text("▶️ Reproducir")
                    }

                    OutlinedButton(
                        onClick = {
                            soundPool?.stop(soundId)
                            isPlaying = false
                        },
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(16.dp),
                        colors = ButtonDefaults.outlinedButtonColors(
                            contentColor = TealPrimary
                        )
                    ) {
                        Text("⏹️ Detener")
                    }
                }

                if (isPlaying) {
                    Surface(
                        shape = RoundedCornerShape(12.dp),
                        color = TealPrimary.copy(alpha = 0.1f),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = "✅ Sonido reproducido correctamente",
                            fontSize = 13.sp,
                            color = TealPrimary,
                            modifier = Modifier.padding(12.dp)
                        )
                    }
                }
            }
        }
    }
}