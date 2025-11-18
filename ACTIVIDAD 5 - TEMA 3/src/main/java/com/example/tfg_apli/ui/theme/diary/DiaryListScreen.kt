package com.example.tfg_apli.ui.diary

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.tfg_apli.ui.components.DiaryCard
import com.example.tfg_apli.ui.components.EmptyState
import com.example.tfg_apli.ui.components.ErrorState
import com.example.tfg_apli.ui.components.LoadingState
import androidx.compose.runtime.livedata.observeAsState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DiaryListScreen(
    viewModel: DiaryViewModel,
    navController: NavController,
    onLogout: () -> Unit
) {
    val uiState = viewModel.uiState
    val diaryCreated =
        navController.currentBackStackEntry
            ?.savedStateHandle
            ?.getLiveData<Boolean>("diaryCreated")
            ?.observeAsState()


    LaunchedEffect(diaryCreated?.value) {
        if (diaryCreated?.value == true) {
            viewModel.loadDiaries()
            navController.currentBackStackEntry?.savedStateHandle?.set("diaryCreated", false)
        }
    }
    LaunchedEffect(Unit) {
        viewModel.loadDiaries()
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        "Mis Diarios",
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Bold
                    )
                },
                actions = {
                    IconButton(onClick = onLogout) {
                        Icon(Icons.Default.AccountCircle, contentDescription = "Perfil")
                    }
                },
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { navController.navigate("diary_editor") },
                containerColor = MaterialTheme.colorScheme.primary
            ) {
                Icon(Icons.Default.Add, contentDescription = "Crear")
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            when (uiState) {
                is DiaryViewModel.DiaryUiState.Loading -> {
                    LoadingState(message = "Cargando tus diarios...")
                }
                is DiaryViewModel.DiaryUiState.Error -> {
                    ErrorState(
                        message = uiState.message,
                        onRetry = { viewModel.loadDiaries() }
                    )
                }
                is DiaryViewModel.DiaryUiState.Success -> {
                    val diaries = uiState.diaries
                    if (diaries.isEmpty()) {
                        EmptyState(
                            title = "Aún no hay diarios",
                            message = "Crea tu primer diario para empezar",
                            onCreateClick = { navController.navigate("diary_editor") }
                        )
                    } else {
                        LazyVerticalGrid(
                            columns = GridCells.Adaptive(minSize = 300.dp),
                            contentPadding = PaddingValues(16.dp),
                            horizontalArrangement = Arrangement.spacedBy(16.dp),
                            verticalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            items(diaries, key = { it.id ?: 0 }) { diary ->
                                DiaryCard(
                                    diario = diary,
                                    onClick = { navController.navigate("diary_detail/${diary.id}") }
                                )
                            }
                        }
                    }
                }
                else -> Unit
            }
        }
    }
}
