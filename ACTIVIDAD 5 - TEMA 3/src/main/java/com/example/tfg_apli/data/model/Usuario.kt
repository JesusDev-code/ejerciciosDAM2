package com.example.tfg_apli.data.model

data class Usuario(
    val id: Long,
    val firebaseUid: String,
    val nombre: String,
    val email: String,
    val rolId: Long,
    val departamentoId: Long?,
    val fechaCreacion: String
)