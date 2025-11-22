package com.example.actividad2_tema4.ui.theme.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.maps.android.compose.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MapScreen(onNavigateBack: () -> Unit) {
    val tiendas = com.example.actividad2_tema4.data.TiendasData.tiendas

    // Calcular límites para mostrar todas las tiendas
    val boundsBuilder = LatLngBounds.builder()
    tiendas.forEach { tienda ->
        boundsBuilder.include(LatLng(tienda.latitud, tienda.longitud))
    }
    val bounds = boundsBuilder.build()

    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(bounds.center, 13f)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Mapa de Tiendas") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Volver")
                    }
                }
            )
        }
    ) { paddingValues ->
        GoogleMap(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            cameraPositionState = cameraPositionState
        ) {
            tiendas.forEach { tienda ->
                Marker(
                    state = MarkerState(
                        position = LatLng(tienda.latitud, tienda.longitud)
                    ),
                    title = tienda.nombre,
                    snippet = tienda.calle,
                    // ✅ ICONO VERDE
                    icon = com.google.android.gms.maps.model.BitmapDescriptorFactory.defaultMarker(
                        com.google.android.gms.maps.model.BitmapDescriptorFactory.HUE_GREEN
                    )
                )
            }
        }
    }
}