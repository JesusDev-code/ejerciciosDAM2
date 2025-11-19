package com.example.tarea1_tema4.ui.theme.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import com.example.tarea1_tema4.ui.theme.*
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExoPlayerAudioScreen(onBack: () -> Unit) {
    val context = LocalContext.current
    val exoPlayer = remember { ExoPlayer.Builder(context).build() }
    var isPlaying by remember { mutableStateOf(false) }
    var currentProgress by remember { mutableStateOf(0f) }
    var isMuted by remember { mutableStateOf(false) }
    var showStatus by remember { mutableStateOf(false) }
    var currentIndex by remember { mutableStateOf(0) }

    // Añade tus dos (o más) enlaces aquí
    val audioUrls = listOf(
        "https://www.soundhelix.com/examples/mp3/SoundHelix-Song-4.mp3",
        "https://www.soundhelix.com/examples/mp3/SoundHelix-Song-9.mp3"
    )
    val audioTitles = listOf(
        "Canción de ejemplo número 1",
        "Canción de ejemplo número 2"
    )

    LaunchedEffect(isPlaying) {
        while (isPlaying) {
            val duration = exoPlayer.duration
            val position = exoPlayer.currentPosition
            if (duration > 0) {
                currentProgress = (position.toFloat() / duration.toFloat())
            }
            delay(100)
        }
    }

    DisposableEffect(Unit) {
        onDispose {
            exoPlayer.release()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Audio Remoto - ExoPlayer", fontWeight = FontWeight.Bold) },
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
                .background(Brush.verticalGradient(listOf(TealBackground, TealLight)))
                .padding(padding)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                // Album Art
                Surface(
                    modifier = Modifier.size(200.dp),
                    shape = RoundedCornerShape(32.dp),
                    color = CardBackground,
                    shadowElevation = 16.dp
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Brush.linearGradient(listOf(TealPrimary, TealPrimaryDark))),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("🎵", fontSize = 80.sp)
                    }
                }

                Spacer(modifier = Modifier.height(32.dp))

                Text(
                    text = audioTitles[currentIndex],
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextPrimary
                )

                Text(
                    text = "Audio desde servidor remoto",
                    fontSize = 15.sp,
                    color = TextSecondary
                )

                Spacer(modifier = Modifier.height(32.dp))

                // Progress Bar
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    LinearProgressIndicator(
                        progress = currentProgress,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(6.dp)
                            .clip(RoundedCornerShape(3.dp)),
                        color = TealPrimary,
                        trackColor = BorderLight
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                }

                Spacer(modifier = Modifier.height(32.dp))

                Row(
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Previous Button
                    IconButton(
                        onClick = {
                            // Retrocede, si puede
                            if (currentIndex > 0) {
                                currentIndex -= 1
                                val mediaItem = MediaItem.fromUri(audioUrls[currentIndex])
                                exoPlayer.setMediaItem(mediaItem)
                                exoPlayer.prepare()
                                exoPlayer.play()
                                isPlaying = true
                                showStatus = true
                            }
                        },
                        modifier = Modifier
                            .size(56.dp)
                            .clip(CircleShape)
                            .background(CardBackground)
                    ) {
                        Text("⏮️", fontSize = 24.sp)
                    }

                    // Play/Pause Button
                    Surface(
                        modifier = Modifier
                            .size(72.dp)
                            .clip(CircleShape),
                        color = TealPrimary,
                        shadowElevation = 8.dp,
                        onClick = {
                            if (isPlaying) {
                                exoPlayer.pause()
                                isPlaying = false
                            } else {
                                val mediaItem = MediaItem.fromUri(audioUrls[currentIndex])
                                exoPlayer.setMediaItem(mediaItem)
                                exoPlayer.prepare()
                                exoPlayer.play()
                                isPlaying = true
                                showStatus = true
                            }
                        }
                    ) {
                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier.fillMaxSize()
                        ) {
                            Text(
                                if (isPlaying) "⏸️" else "▶️",
                                fontSize = 32.sp,
                                color = TextOnPrimary
                            )
                        }
                    }

                    // Next Button
                    IconButton(
                        onClick = {
                            // Avanza, si puede
                            if (currentIndex < audioUrls.size - 1) {
                                currentIndex += 1
                                val mediaItem = MediaItem.fromUri(audioUrls[currentIndex])
                                exoPlayer.setMediaItem(mediaItem)
                                exoPlayer.prepare()
                                exoPlayer.play()
                                isPlaying = true
                                showStatus = true
                            }
                        },
                        modifier = Modifier
                            .size(56.dp)
                            .clip(CircleShape)
                            .background(CardBackground)
                    ) {
                        Text("⏭️", fontSize = 24.sp)
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                // Volume Control
                Row(
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(
                        onClick = {
                            isMuted = !isMuted
                            exoPlayer.volume = if (isMuted) 0f else 1f
                        },
                        modifier = Modifier
                            .size(48.dp)
                            .clip(CircleShape)
                            .background(CardBackground)
                    ) {
                        Text(if (isMuted) "🔇" else "🔊", fontSize = 20.sp)
                    }
                }

                if (showStatus) {
                    Spacer(modifier = Modifier.height(24.dp))
                    Surface(
                        shape = RoundedCornerShape(12.dp),
                        color = TealPrimary.copy(alpha = 0.1f),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = "🎧 Audio cargado desde servidor remoto",
                            fontSize = 13.sp,
                            color = TealPrimary,
                            modifier = Modifier.padding(12.dp)
                        )
                    }
                    LaunchedEffect(Unit) {
                        delay(2000)
                        showStatus = false
                    }
                }
            }
        }
    }
}
