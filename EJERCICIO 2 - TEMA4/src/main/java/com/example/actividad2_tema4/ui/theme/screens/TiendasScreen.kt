package com.example.actividad2_tema4.ui.theme.screens

import android.content.Context
import android.media.MediaPlayer
import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Map
import androidx.compose.material.icons.filled.Sensors
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.example.actividad2_tema4.R
import com.example.actividad2_tema4.data.TiendasData
import com.example.actividad2_tema4.model.Tienda
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TiendasScreen(
    onNavigateToMap: () -> Unit,
    onNavigateToSensors: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Tiendas de Cádiz") },
                actions = {
                    IconButton(onClick = onNavigateToMap) {
                        Icon(
                            imageVector = Icons.Default.Map,
                            contentDescription = "Ver Mapa"
                        )
                    }
                },
            )
        },
        bottomBar = {
            BottomAppBar {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Button(onClick = onNavigateToSensors) {
                        Icon(
                            imageVector = Icons.Default.Sensors,
                            contentDescription = "Sensores"
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Ver Sensores")
                    }
                }
            }
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(TiendasData.tiendas) { tienda ->
                TiendaCard(tienda = tienda)
            }
        }
    }
}

@Composable
fun TiendaCard(tienda: Tienda) {
    val context = LocalContext.current
    var isDetalle by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()

    val infiniteTransition = rememberInfiniteTransition(label = "oferta")
    val scale by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = if (tienda.oferta) 1.05f else 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(1000, easing = EaseInOut),
            repeatMode = RepeatMode.Reverse
        ),
        label = "scale"
    )

    val transition = updateTransition(
        targetState = isDetalle,
        label = "detalleTransition"
    )
    val cardScale by transition.animateFloat(
        label = "cardScale",
        transitionSpec = { tween(300) }
    ) { detalle ->
        if (detalle) 1.02f else 1f
    }
    val shadowColor by transition.animateColor(
        label = "shadowColor",
        transitionSpec = { tween(300) }
    ) { detalle ->
        if (detalle) Color.Red else Color.Gray
    }
    val offsetX = remember { Animatable(0f) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .scale(if (tienda.oferta) scale else 1f)
            .scale(cardScale)
            .shadow(8.dp, shape = RoundedCornerShape(12.dp))
            .offset { IntOffset(offsetX.value.roundToInt(), 0) }
            .pointerInput(Unit) {
                detectHorizontalDragGestures(
                    onDragEnd = {
                        scope.launch {
                            if (offsetX.value > 300) {
                                playCashRegisterSound(context)
                                offsetX.animateTo(
                                    targetValue = 1000f,
                                    animationSpec = tween(300)
                                )
                                offsetX.animateTo(
                                    targetValue = 0f,
                                    animationSpec = spring(
                                        dampingRatio = Spring.DampingRatioMediumBouncy,
                                        stiffness = Spring.StiffnessLow
                                    )
                                )
                            } else if (offsetX.value < -300) {
                                playExplosionSound(context)
                                offsetX.animateTo(
                                    targetValue = -1000f,
                                    animationSpec = tween(300)
                                )
                                offsetX.animateTo(
                                    targetValue = 0f,
                                    animationSpec = spring(
                                        dampingRatio = Spring.DampingRatioMediumBouncy,
                                        stiffness = Spring.StiffnessLow
                                    )
                                )
                            } else {
                                offsetX.animateTo(0f, animationSpec = spring())
                            }
                        }
                    },
                    onHorizontalDrag = { change, dragAmount ->
                        change.consume()
                        scope.launch {
                            offsetX.snapTo(offsetX.value + dragAmount)
                        }
                    }
                )
            },
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.surface)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Image(
                    painter = painterResource(id = tienda.foto),
                    contentDescription = tienda.nombre,
                    modifier = Modifier
                        .size(100.dp)
                        .padding(end = 16.dp),
                    contentScale = ContentScale.Crop
                )

                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = tienda.nombre,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = tienda.calle,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Producto: ${tienda.productoPrincipal}",
                        style = MaterialTheme.typography.bodySmall
                    )
                    if (tienda.oferta) {
                        Spacer(modifier = Modifier.height(8.dp))
                        Surface(
                            color = Color.Red,
                            shape = RoundedCornerShape(4.dp)
                        ) {
                            Text(
                                text = "¡OFERTA!",
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                                color = Color.White,
                                style = MaterialTheme.typography.labelSmall,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            }

            // AnimatedVisibility para descripción
            AnimatedVisibility(
                visible = isDetalle,
                enter = expandVertically() + fadeIn(),
                exit = shrinkVertically() + fadeOut()
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(MaterialTheme.colorScheme.surfaceVariant)
                        .padding(16.dp)
                ) {
                    Text(
                        text = "Descripción:",
                        style = MaterialTheme.typography.labelMedium,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = tienda.descripcion,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }

            // Botón para cambiar modo
            Button(
                onClick = { isDetalle = !isDetalle },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text(if (isDetalle) "Ocultar Detalles" else "Ver Detalles")
            }
        }
    }
}

private fun playCashRegisterSound(context: Context) {
    try {
        val mediaPlayer = MediaPlayer.create(context, R.raw.cash_register)
        mediaPlayer?.start()
        mediaPlayer?.setOnCompletionListener { it.release() }
    } catch (e: Exception) {
        e.printStackTrace()
    }
}

private fun playExplosionSound(context: Context) {
    try {
        val mediaPlayer = MediaPlayer.create(context, R.raw.explosion)
        mediaPlayer?.start()
        mediaPlayer?.setOnCompletionListener { it.release() }
    } catch (e: Exception) {
        e.printStackTrace()
    }
}
