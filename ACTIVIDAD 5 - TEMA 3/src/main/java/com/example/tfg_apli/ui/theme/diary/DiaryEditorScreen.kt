package com.example.tfg_apli.ui.diary

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Save
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.tfg_apli.data.model.Diario
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DiaryEditorScreen(
    diaryId: Long? = null,
    viewModel: DiaryViewModel,
    navController: NavController
) {
    val diaryLoaded = viewModel.selectedDiary
    val isEditMode = diaryId != null
    var initialized by remember { mutableStateOf(false) }

    var titulo by remember { mutableStateOf("") }
    var contenido by remember { mutableStateOf("") }
    var visibilidad by remember { mutableStateOf("PRIVADO") }
    var proyectoId by remember { mutableStateOf<Long?>(null) }
    var temaTitulo by remember { mutableStateOf("") }
    var temaId by remember { mutableStateOf<Int?>(null) }
    var showDiscardDialog by remember { mutableStateOf(false) }

    LaunchedEffect(diaryId) {
        if (isEditMode) {
            viewModel.loadDiary(diaryId!!)
        }
    }
    LaunchedEffect(diaryLoaded, isEditMode) {
        if (isEditMode && diaryLoaded != null && !initialized) {
            titulo = diaryLoaded.titulo ?: ""
            contenido = diaryLoaded.contenido ?: ""
            visibilidad = diaryLoaded.visibilidad ?: "PRIVADO"
            proyectoId = diaryLoaded.proyectoId
            temaTitulo = diaryLoaded.temaTitulo ?: ""
            temaId = diaryLoaded.temaId
            initialized = true
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(if (isEditMode) "Editar Diario" else "Nuevo Diario") },
                navigationIcon = {
                    IconButton(onClick = { showDiscardDialog = true }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Volver")
                    }
                },
                actions = {
                    IconButton(onClick = {
                        if (titulo.isNotBlank() && contenido.isNotBlank()) {
                            val diario = Diario(
                                id = diaryId,
                                titulo = titulo,
                                contenido = contenido,
                                visibilidad = visibilidad,
                                proyectoId = proyectoId,
                                temaTitulo = if (temaTitulo.isBlank()) null else temaTitulo,
                                temaId = temaId
                            )
                            viewModel.viewModelScope.launch {
                                val editSuccess = if (isEditMode) {
                                    viewModel.updateDiaryAndReturnResult(diaryId!!, diario)
                                } else {
                                    viewModel.createDiaryAndReturnResult(diario)
                                }
                                if (editSuccess) {
                                    navController.previousBackStackEntry?.savedStateHandle?.set("diaryCreated", true)
                                    navController.popBackStack()
                                } else {
                                    // feedback visual si quieres
                                }
                            }
                        }
                    }) {
                        Icon(Icons.Default.Save, contentDescription = "Guardar")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(paddingValues)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            OutlinedTextField(
                value = titulo,
                onValueChange = { titulo = it },
                label = { Text("Título del diario") },
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = contenido,
                onValueChange = { contenido = it },
                label = { Text("Contenido") },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp),
                minLines = 10,
                maxLines = 20
            )
            Text(
                text = "Visibilidad",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                RadioButton(
                    selected = visibilidad == "PRIVADO",
                    onClick = { visibilidad = "PRIVADO" }
                )
                Text("Privado 🔒")
                Spacer(modifier = Modifier.width(24.dp))
                RadioButton(
                    selected = visibilidad == "PUBLICO",
                    onClick = { visibilidad = "PUBLICO" }
                )
                Text("Público 🌐")
            }
            OutlinedTextField(
                value = proyectoId?.toString() ?: "Sin proyecto asignado",
                onValueChange = {},
                label = { Text("Proyecto") },
                modifier = Modifier.fillMaxWidth(),
                enabled = false
            )
            if (temaTitulo.isNotBlank() || temaId != null) {
                Button(onClick = {
                    temaTitulo = ""
                    temaId = null
                }) {
                    Text("Eliminar tema")
                }
            }
        }
        if (showDiscardDialog) {
            AlertDialog(
                onDismissRequest = { showDiscardDialog = false },
                title = { Text("¿Descartar cambios?") },
                text = { Text("Los cambios no guardados se perderán") },
                confirmButton = {
                    TextButton(onClick = {
                        showDiscardDialog = false
                        navController.popBackStack()
                    }) {
                        Text("Descartar", color = MaterialTheme.colorScheme.error)
                    }
                },
                dismissButton = {
                    TextButton(onClick = { showDiscardDialog = false }) {
                        Text("Cancelar")
                    }
                }
            )
        }
    }
}