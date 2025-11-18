package com.example.tfg_apli.utils

import java.text.SimpleDateFormat
import java.util.Locale

fun formatDate(dateString: String?): String {
    if (dateString.isNullOrEmpty()) return "Fecha desconocida"

    return try {
        val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault())
        val outputFormat = SimpleDateFormat("dd MMM yyyy, HH:mm", Locale("es", "ES"))
        val date = inputFormat.parse(dateString)
        outputFormat.format(date)
    } catch (e: Exception) {
        "Fecha inválida"
    }
}