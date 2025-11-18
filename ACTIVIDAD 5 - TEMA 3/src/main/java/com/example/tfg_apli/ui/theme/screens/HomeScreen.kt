// Archivo: app/src/main/java/com/example/tfg_apli/ui/theme/screens/HomeScreen.kt
package com.example.tfg_apli.ui.theme.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.tfg_apli.ui.components.DiaryCard
import com.example.tfg_apli.ui.components.EmptyState
import com.example.tfg_apli.ui.components.ErrorState
import com.example.tfg_apli.ui.diary.DiaryViewModel
import com.example.tfg_apli.ui.theme.components.SearchBar // Importación correcta
import com.example.tfg_apli.ui.theme.components.ProfileMenu // Importación correcta
import com.example.tfg_apli.ui.theme.PrimaryColor // AÑADE ESTO
import com.example.tfg_apli.ui.theme.Neutral90 // AÑADE ESTO
import com.example.tfg_apli.ui.theme.Neutral20 // AÑADE ESTO
import com.example.tfg_apli.ui.theme.Neutral100 // AÑADE ESTO

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    viewModel: DiaryViewModel,
    navController: NavController,
    onLogout: () -> Unit
) {
    var searchQuery by remember { mutableStateOf("") }
    val uiState = viewModel.uiState

    LaunchedEffect(Unit) {
        viewModel.loadDiaries()
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        "Mi Diario",
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Bold
                    )
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = Neutral100,
                    titleContentColor = PrimaryColor // CORREGIDO
                ),
                actions = {
                    ProfileMenu(onLogout = onLogout)
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { navController.navigate("diary_editor") },
                containerColor = PrimaryColor, // CORREGIDO
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier.size(64.dp)
            ) {
                Icon(
                    Icons.Default.Add,
                    contentDescription = "Crear",
                    tint = Neutral100,
                    modifier = Modifier.size(28.dp)
                )
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 16.dp)
        ) {
            SearchBar(
                query = searchQuery,
                onQueryChange = { searchQuery = it },
                modifier = Modifier.padding(vertical = 16.dp)
            )

            when (uiState) {
                is DiaryViewModel.DiaryUiState.Loading -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            CircularProgressIndicator(color = PrimaryColor) // CORREGIDO
                            Spacer(modifier = Modifier.height(16.dp))
                            Text(
                                "Cargando tus diarios...",
                                style = MaterialTheme.typography.bodyMedium,
                                color = Neutral20.copy(alpha = 0.6f)
                            )
                        }
                    }
                }

                is DiaryViewModel.DiaryUiState.Success -> {
                    val diaries = (uiState as DiaryViewModel.DiaryUiState.Success).diaries
                    val filteredDiaries = diaries.filter {
                        it.titulo.contains(searchQuery, ignoreCase = true) ||
                                it.contenido.contains(searchQuery, ignoreCase = true)
                    }

                    if (filteredDiaries.isEmpty()) {
                        EmptyState(
                            title = if (searchQuery.isEmpty()) "Aún no hay diarios" else "No se encontraron resultados",
                            message = if (searchQuery.isEmpty())
                                "Crea tu primer diario para empezar a escribir tus memorias"
                            else "Prueba con otras palabras",
                            onCreateClick = { navController.navigate("diary_editor") }
                        )
                    } else {
                        LazyVerticalGrid(
                            columns = GridCells.Adaptive(minSize = 300.dp),
                            contentPadding = PaddingValues(vertical = 8.dp),
                            horizontalArrangement = Arrangement.spacedBy(16.dp),
                            verticalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            items(filteredDiaries, key = { it.id ?: 0 }) { diary ->
                                DiaryCard(
                                    diario = diary,
                                    onClick = {
                                        navController.navigate("diary_detail/${diary.id}")
                                    }
                                )
                            }
                        }
                    }
                }

                is DiaryViewModel.DiaryUiState.Error -> {
                    ErrorState(
                        message = (uiState as DiaryViewModel.DiaryUiState.Error).message,
                        onRetry = { viewModel.loadDiaries() }
                    )
                }

                else -> Unit
            }
        }
    }
}