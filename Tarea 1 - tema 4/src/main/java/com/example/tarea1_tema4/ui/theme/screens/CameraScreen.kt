package com.example.tarea1_tema4.ui.theme.screens

import android.Manifest
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.example.tarea1_tema4.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class, ExperimentalPermissionsApi::class)
@Composable
fun CameraScreen(
    navController: NavController,
    photoList: List<Bitmap>,
    setPhotoList: (List<Bitmap>) -> Unit,
    onBack: () -> Unit
) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current

    val cameraPermissionState = rememberPermissionState(Manifest.permission.CAMERA)
    var capturedImage by remember { mutableStateOf<Bitmap?>(null) }
    var imageCapture: ImageCapture? by remember { mutableStateOf(null) }
    var previewView: PreviewView? by remember { mutableStateOf(null) }
    var showStatus by remember { mutableStateOf(false) }

    LaunchedEffect(cameraPermissionState.status.isGranted) {
        if (cameraPermissionState.status.isGranted && previewView != null) {
            startCamera(context, lifecycleOwner, previewView!!) { capture ->
                imageCapture = capture
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Cámara - CameraX", fontWeight = FontWeight.Bold) },
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
            modifier = Modifier
                .fillMaxSize()
                .background(Brush.verticalGradient(listOf(TealBackground, TealLight)))
                .padding(padding)
        ) {
            if (!cameraPermissionState.status.isGranted) {
                // Pantalla de permisos
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(24.dp)
                        .verticalScroll(rememberScrollState()),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Surface(
                        modifier = Modifier.size(120.dp),
                        shape = RoundedCornerShape(24.dp),
                        color = CardBackground,
                        shadowElevation = 8.dp
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Text("📷", fontSize = 64.sp)
                        }
                    }
                    Spacer(modifier = Modifier.height(24.dp))

                    Text(
                        text = "Permisos de Cámara",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = TextPrimary
                    )
                    Text(
                        text = "Necesitamos acceso a tu cámara para capturar fotos",
                        fontSize = 15.sp,
                        color = TextSecondary
                    )
                    Spacer(modifier = Modifier.height(24.dp))

                    Button(
                        onClick = { cameraPermissionState.launchPermissionRequest() },
                        shape = RoundedCornerShape(16.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = TealPrimary)
                    ) {
                        Text("Conceder Permiso")
                    }
                }
            } else {
                // Pantalla cámara
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(24.dp)
                        .verticalScroll(rememberScrollState()), // Habilita scroll si pantalla es chica
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = "Captura de Imagen",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = TextPrimary
                    )
                    Text(
                        text = "Usa la cámara para capturar fotos",
                        fontSize = 15.sp,
                        color = TextSecondary
                    )
                    Spacer(modifier = Modifier.height(24.dp))

                    Surface(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(300.dp),
                        shape = RoundedCornerShape(20.dp),
                        shadowElevation = 8.dp,
                        color = CardBackground
                    ) {
                        if (capturedImage != null) {
                            Image(
                                bitmap = capturedImage!!.asImageBitmap(),
                                contentDescription = "Captured Image",
                                modifier = Modifier.fillMaxSize()
                            )
                        } else {
                            AndroidView(
                                factory = { ctx ->
                                    PreviewView(ctx).also { preview ->
                                        previewView = preview
                                        if (cameraPermissionState.status.isGranted) {
                                            startCamera(ctx, lifecycleOwner, preview) { capture ->
                                                imageCapture = capture
                                            }
                                        }
                                    }
                                },
                                modifier = Modifier.fillMaxSize()
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(24.dp))
                    Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                        if (capturedImage != null) {
                            OutlinedButton(
                                onClick = { capturedImage = null },
                                modifier = Modifier.weight(1f),
                                shape = RoundedCornerShape(16.dp),
                                colors = ButtonDefaults.outlinedButtonColors(contentColor = TealPrimary)
                            ) {
                                Text("🔄 Nueva Foto")
                            }
                        } else {
                            Button(
                                onClick = {
                                    imageCapture?.let { capture ->
                                        capturePhoto(context, capture) { bitmap ->
                                            capturedImage = bitmap
                                            val updated = (photoList + bitmap).takeLast(5)
                                            setPhotoList(updated)
                                            showStatus = true
                                        }
                                    }
                                },
                                modifier = Modifier.weight(1f),
                                shape = RoundedCornerShape(16.dp),
                                colors = ButtonDefaults.buttonColors(containerColor = TealPrimary)
                            ) {
                                Text("📸 Capturar Foto")
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    if (photoList.isNotEmpty()) {
                        Text("Última foto tomada:", color = TextSecondary)
                        Surface(
                            modifier = Modifier
                                .width(160.dp)
                                .height(120.dp)
                                .clip(RoundedCornerShape(16.dp)),
                            shadowElevation = 6.dp,
                            color = CardBackground
                        ) {
                            Image(
                                bitmap = photoList.last().asImageBitmap(),
                                contentDescription = "Ultima foto tomada",
                                modifier = Modifier.fillMaxSize()
                            )
                        }
                        Spacer(modifier = Modifier.height(16.dp))
                    }

                    // Botón de galería SIEMPRE visible, centrado, separado
                    Button(
                        onClick = { navController.navigate("gallery") },
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally)
                            .padding(top = 12.dp, bottom = 12.dp)
                            .width(160.dp),
                        shape = RoundedCornerShape(20.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = TealPrimary)
                    ) {
                        Text("📁 Fotos")
                    }

                    if (showStatus) {
                        Spacer(modifier = Modifier.height(24.dp))
                        Surface(
                            shape = RoundedCornerShape(12.dp),
                            color = TealPrimary.copy(alpha = 0.1f),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(
                                text = "📱 Foto guardada en galería de la app",
                                fontSize = 13.sp,
                                color = TealPrimary,
                                modifier = Modifier.padding(12.dp)
                            )
                        }
                        LaunchedEffect(showStatus) {
                            kotlinx.coroutines.delay(2000)
                            showStatus = false
                        }
                    }
                }
            }
        }
    }
}

// Rota la foto para que salga vertical si CameraX la guarda girada
fun rotateBitmapIfNeeded(bitmap: Bitmap): Bitmap {
    val matrix = Matrix().apply {
        postRotate(90f) // Cambia a 270f si sale mal
    }
    return Bitmap.createBitmap(bitmap, 0, 0, bitmap.width, bitmap.height, matrix, true)
}

// ------------ FUNCIONES DE CÁMARA ----------
private fun startCamera(
    context: Context,
    lifecycleOwner: androidx.lifecycle.LifecycleOwner,
    previewView: PreviewView,
    onImageCaptureReady: (ImageCapture) -> Unit
) {
    val cameraProviderFuture = ProcessCameraProvider.getInstance(context)
    cameraProviderFuture.addListener({
        val cameraProvider = cameraProviderFuture.get()
        val preview = Preview.Builder().build().also {
            it.setSurfaceProvider(previewView.surfaceProvider)
        }
        val imageCapture = ImageCapture.Builder()
            .setCaptureMode(ImageCapture.CAPTURE_MODE_MINIMIZE_LATENCY)
            .build()
        val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA
        try {
            cameraProvider.unbindAll()
            cameraProvider.bindToLifecycle(
                lifecycleOwner,
                cameraSelector,
                preview,
                imageCapture
            )
            onImageCaptureReady(imageCapture)
        } catch (e: Exception) { e.printStackTrace() }
    }, ContextCompat.getMainExecutor(context))
}

private fun capturePhoto(
    context: Context,
    imageCapture: ImageCapture,
    onImageCaptured: (Bitmap) -> Unit
) {
    val fileName = java.text.SimpleDateFormat("yyyy-MM-dd-HH-mm-ss", java.util.Locale.US)
        .format(System.currentTimeMillis()) + ".jpg"
    val file = java.io.File(context.getExternalFilesDir(null), fileName)
    val outputOptions = ImageCapture.OutputFileOptions.Builder(file).build()
    imageCapture.takePicture(
        outputOptions,
        ContextCompat.getMainExecutor(context),
        object : ImageCapture.OnImageSavedCallback {
            override fun onImageSaved(output: ImageCapture.OutputFileResults) {
                var bitmap = BitmapFactory.decodeFile(file.absolutePath)
                bitmap = rotateBitmapIfNeeded(bitmap)
                onImageCaptured(bitmap)
            }
            override fun onError(exc: ImageCaptureException) { exc.printStackTrace() }
        }
    )
}
