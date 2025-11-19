package com.example.tarea1_tema4.ui.theme.screens

import android.graphics.Bitmap
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tarea1_tema4.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GalleryScreen(
    photoList: List<Bitmap>,
    onBack: () -> Unit
) {
    var selectedImage by remember { mutableStateOf<Bitmap?>(null) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Galería de Fotos", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, "Volver")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = TealPrimary)
            )
        }
    ) { padding ->
        Box(
            Modifier
                .fillMaxSize()
                .background(Brush.verticalGradient(listOf(TealBackground, TealLight)))
                .padding(padding)
        ) {
            if (selectedImage != null) {
                // Imagen ampliada
                Box(
                    Modifier
                        .fillMaxSize()
                        .background(TealLight.copy(alpha = 0.97f))
                        .clickable { selectedImage = null },
                    contentAlignment = Alignment.Center
                ) {
                    Surface(
                        shape = RoundedCornerShape(30.dp),
                        shadowElevation = 18.dp,
                        color = CardBackground
                    ) {
                        Image(
                            bitmap = selectedImage!!.asImageBitmap(),
                            contentDescription = "Foto ampliada",
                            modifier = Modifier
                                .size(360.dp)
                                .padding(18.dp)
                                .clickable { selectedImage = null }
                        )
                    }
                }
            } else {
                Column(
                    Modifier
                        .fillMaxSize()
                        .padding(20.dp)
                        .verticalScroll(rememberScrollState()),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        "Máximo 5 fotos guardadas",
                        color = TextSecondary,
                        fontSize = 15.sp
                    )
                    Spacer(Modifier.height(24.dp))
                    photoList.reversed().forEach { bmp ->
                        Surface(
                            shape = RoundedCornerShape(16.dp),
                            shadowElevation = 7.dp,
                            color = CardBackground,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(200.dp)
                                .padding(bottom = 20.dp)
                                .clickable { selectedImage = bmp }
                        ) {
                            Image(
                                bitmap = bmp.asImageBitmap(),
                                contentDescription = null,
                                modifier = Modifier.fillMaxSize()
                            )
                        }
                    }
                }
            }
        }
    }
}
