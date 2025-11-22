package com.example.actividad2_tema4

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.actividad2_tema4.ui.theme.screens.TiendasScreen
import com.example.actividad2_tema4.ui.theme.screens.MapScreen
import com.example.actividad2_tema4.ui.theme.screens.SensorsScreen


@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = "tiendas"
    ) {
        composable("tiendas") {
            TiendasScreen(
                onNavigateToMap = { navController.navigate("map") },
                onNavigateToSensors = { navController.navigate("sensors") }
            )
        }
        composable("map") {
            MapScreen(onNavigateBack = { navController.popBackStack() })
        }
        composable("sensors") {
            SensorsScreen(onNavigateBack = { navController.popBackStack() })
        }
    }
}