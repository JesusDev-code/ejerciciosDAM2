package com.example.tfg_apli.data.model

data class Diario(
    val id: Long? = null,
    val titulo: String = "",
    val contenido: String = "",
    val visibilidad: String = "PRIVADO",
    val fechaCreacion: String? = null,
    val fechaModificacion: String? = null,
    val usuarioId: Long? = null,
    val proyectoId: Long? = null,
    val usuarioNombre: String? = null,
    val temaTitulo: String? = null,
    val temaId: Int? = null
) {
    fun getVisibilidadIcono(): String {
        return if (visibilidad == "PUBLICO") "🌐" else "🔒"
    }
}
