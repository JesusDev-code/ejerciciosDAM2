package com.example.actividad2_tema4.model

data class Tienda(
    val id: Int,
    val nombre: String,
    val calle: String,
    val productoPrincipal: String,
    val latitud: Double,
    val longitud: Double,
    val descripcion: String,
    val oferta: Boolean,
    val foto: Int // R.drawable.tienda1, etc
)
