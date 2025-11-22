package com.example.actividad2_tema4.ui.theme.screens

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

data class SensorData(val x: Float = 0f, val y: Float = 0f, val z: Float = 0f)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SensorsScreen(onNavigateBack: () -> Unit) {
    val context = LocalContext.current
    var accelerometerData by remember { mutableStateOf(SensorData()) }
    var gyroscopeData by remember { mutableStateOf(SensorData()) }
    var lightData by remember { mutableStateOf(0f) }

    DisposableEffect(Unit) {
        val sensorManager = context.getSystemService(Context.SENSOR_SERVICE) as SensorManager
        val accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
        val gyroscope = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE)
        val lightSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT)

        val listener = object : SensorEventListener {
            override fun onSensorChanged(event: SensorEvent?) {
                event?.let {
                    when (it.sensor.type) {
                        Sensor.TYPE_ACCELEROMETER -> accelerometerData = SensorData(it.values[0], it.values[1], it.values[2])
                        Sensor.TYPE_GYROSCOPE -> gyroscopeData = SensorData(it.values[0], it.values[1], it.values[2])
                        Sensor.TYPE_LIGHT -> lightData = it.values[0]
                    }
                }
            }
            override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}
        }

        accelerometer?.let { sensorManager.registerListener(listener, it, SensorManager.SENSOR_DELAY_NORMAL) }
        gyroscope?.let { sensorManager.registerListener(listener, it, SensorManager.SENSOR_DELAY_NORMAL) }
        lightSensor?.let { sensorManager.registerListener(listener, it, SensorManager.SENSOR_DELAY_NORMAL) }

        onDispose {
            sensorManager.unregisterListener(listener)
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Sensores") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Volver")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            SensorCard("Acelerómetro", accelerometerData)
            Spacer(modifier = Modifier.height(16.dp))
            SensorCard("Giroscopio", gyroscopeData)
            Spacer(modifier = Modifier.height(16.dp))
            LuzCard(lightData)
        }
    }
}

@Composable
private fun SensorCard(title: String, data: SensorData) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(title, style = MaterialTheme.typography.titleLarge)
            Spacer(modifier = Modifier.height(8.dp))
            Text("X: ${String.format("%.3f", data.x)}", fontSize = 20.sp)
            Text("Y: ${String.format("%.3f", data.y)}", fontSize = 20.sp)
            Text("Z: ${String.format("%.3f", data.z)}", fontSize = 20.sp)
        }
    }
}

@Composable
private fun LuzCard(value: Float) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Sensor de Luz", style = MaterialTheme.typography.titleLarge)
            Spacer(modifier = Modifier.height(8.dp))
            Text("${String.format("%.2f", value)} lux", fontSize = 24.sp, fontWeight = androidx.compose.ui.text.font.FontWeight.Bold)
        }
    }
}