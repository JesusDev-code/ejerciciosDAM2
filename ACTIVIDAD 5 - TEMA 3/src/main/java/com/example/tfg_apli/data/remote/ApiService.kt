package com.example.tfg_apli.data.remote

import com.example.tfg_apli.data.model.Diario
import com.example.tfg_apli.data.model.PageResponse
import com.example.tfg_apli.data.model.Proyecto
import com.example.tfg_apli.data.model.UsuarioCreateDto
import retrofit2.Response
import retrofit2.http.*

interface ApiService {

    @POST("/api/usuarios/registro")
    suspend fun registrarUsuario(
        @Header("Authorization") token: String,
        @Body request: UsuarioCreateDto
    ): Response<Unit>

    @GET("/api/proyectos")
    suspend fun obtenerProyectos(
        @Header("Authorization") token: String
    ): Response<List<Proyecto>>

    // Solo "mis diarios" para ver los privados y públicos del usuario
    @GET("/api/diarios/mis-diarios")
    suspend fun obtenerMisDiarios(
        @Header("Authorization") token: String
    ): Response<PageResponse<Diario>>

    @GET("/api/diarios/{id}")
    suspend fun obtenerDiario(
        @Header("Authorization") token: String,
        @Path("id") id: Long
    ): Response<Diario>

    @POST("/api/diarios")
    suspend fun crearDiario(
        @Header("Authorization") token: String,
        @Body diario: Diario
    ): Response<Diario>


    @PUT("/api/diarios/{id}")
    suspend fun actualizarDiario(
        @Header("Authorization") token: String,
        @Path("id") id: Long,
        @Body diario: Diario
    ): Response<Diario>

    @DELETE("/api/diarios/{id}")
    suspend fun eliminarDiario(
        @Header("Authorization") token: String,
        @Path("id") id: Long
    ): Response<Unit>
}
