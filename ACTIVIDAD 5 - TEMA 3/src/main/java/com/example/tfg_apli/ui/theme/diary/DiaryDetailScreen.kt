package com.example.tfg_apli.ui.diary

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.tfg_apli.ui.components.LoadingState
import com.example.tfg_apli.utils.formatDate
import com.example.tfg_apli.ui.components.ErrorState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DiaryDetailScreen(
    diaryId: Long,
    viewModel: DiaryViewModel,
    navController: NavController
) {
    LaunchedEffect(diaryId) { viewModel.loadDiary(diaryId) }
    val uiState = viewModel.uiState
    val diary = viewModel.selectedDiary
    var showDeleteDialog by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(diary?.titulo ?: "") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Volver")
                    }
                }
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            when {
                uiState is DiaryViewModel.DiaryUiState.Loading -> {
                    LoadingState()
                }
                diary != null -> {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .verticalScroll(rememberScrollState())
                            .padding(16.dp),
                        verticalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column {
                            Card(
                                modifier = Modifier.fillMaxWidth(),
                                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
                            ) {
                                Column(modifier = Modifier.padding(16.dp)) {
                                    // Info Diario
                                    Row {
                                        Text("Visibilidad: ", fontWeight = FontWeight.Bold)
                                        Text("${diary.visibilidad} ${diary.getVisibilidadIcono()}")
                                    }
                                    Text("Creado: ${formatDate(diary.fechaCreacion)}")
                                    Text("Última edición: ${formatDate(diary.fechaModificacion)}")
                                }
                            }
                            Spacer(modifier = Modifier.height(16.dp))
                            Card(
                                modifier = Modifier.fillMaxWidth(),
                                elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
                            ) {
                                Text(
                                    text = diary.contenido,
                                    style = MaterialTheme.typography.bodyLarge,
                                    modifier = Modifier.padding(16.dp)
                                )
                            }
                        }
                        // --- Botones Editar/Borrar abajo ---
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 16.dp),
                            horizontalArrangement = Arrangement.SpaceEvenly
                        ) {
                            Button(
                                onClick = { navController.navigate("diary_editor?diaryId=$diaryId") },
                                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
                            ) { Text("Editar") }
                            Button(
                                onClick = { showDeleteDialog = true },
                                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error)
                            ) { Text("Borrar") }
                        }
                        if (showDeleteDialog) {
                            AlertDialog(
                                onDismissRequest = { showDeleteDialog = false },
                                title = { Text("¿Eliminar diario?") },
                                text = { Text("Esta acción no se puede deshacer. ¿Seguro que quieres eliminar este diario?") },
                                confirmButton = {
                                    TextButton(onClick = {
                                        viewModel.deleteDiary(diaryId)
                                        showDeleteDialog = false
                                        navController.popBackStack()
                                    }) {
                                        Text("Eliminar", color = MaterialTheme.colorScheme.error)
                                    }
                                },
                                dismissButton = {
                                    TextButton(onClick = { showDeleteDialog = false }) {
                                        Text("Cancelar")
                                    }
                                }
                            )
                        }
                    }
                }
                uiState is DiaryViewModel.DiaryUiState.Error -> {
                    ErrorState(message = uiState.message, onRetry = { viewModel.loadDiary(diaryId) })
                }
            }
        }
    }
}