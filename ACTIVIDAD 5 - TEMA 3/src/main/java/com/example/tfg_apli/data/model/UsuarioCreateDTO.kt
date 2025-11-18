package com.example.tfg_apli.data.model

data class UsuarioCreateDto(
    val nombre: String,
    val email: String,
    val rolId: Int = 1,           // Valor por defecto: USER
    val departamentoId: Int? = 1  // Valor por defecto: Tecnología
)