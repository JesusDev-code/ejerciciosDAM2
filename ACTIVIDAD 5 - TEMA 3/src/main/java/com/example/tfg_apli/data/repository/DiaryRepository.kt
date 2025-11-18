package com.example.tfg_apli.data.repository

import com.example.tfg_apli.data.model.Diario
import com.example.tfg_apli.data.remote.ApiService
import com.example.tfg_apli.utils.Result
import com.example.tfg_apli.utils.TokenManager
import kotlinx.coroutines.flow.firstOrNull

class DiaryRepository(
    private val apiService: ApiService,
    private val tokenManager: TokenManager
) {
    private suspend fun getToken(): String? {
        return tokenManager.getToken().firstOrNull()
    }

    // Solo los diarios del propio usuario (privados + públicos)
    suspend fun getDiaries(): Result<List<Diario>> {
        return try {
            val token = getToken()
            if (token.isNullOrEmpty()) return Result.Error("No autenticado")
            val response = apiService.obtenerMisDiarios("Bearer $token")
            if (response.isSuccessful) {
                val page = response.body()
                Result.Success(page?.content ?: emptyList())
            } else {
                Result.Error("Error: ${response.code()}")
            }
        } catch (e: Exception) {
            Result.Error(e.message ?: "Error de conexión")
        }
    }

    suspend fun getDiary(id: Long): Result<Diario> {
        return try {
            val token = getToken()
            if (token.isNullOrEmpty()) return Result.Error("No autenticado")
            val response = apiService.obtenerDiario("Bearer $token", id)
            if (response.isSuccessful) {
                response.body()?.let {
                    Result.Success(it)
                } ?: Result.Error("Diario no encontrado")
            } else {
                Result.Error("Error: ${response.code()}")
            }
        } catch (e: Exception) {
            Result.Error(e.message ?: "Error de conexión")
        }
    }

    suspend fun createDiary(diario: Diario): Result<Diario> {
        return try {
            val token = getToken()
            if (token.isNullOrEmpty()) return Result.Error("No autenticado")
            val response = apiService.crearDiario("Bearer $token", diario)
            if (response.isSuccessful) {
                response.body()?.let {
                    Result.Success(it)
                } ?: Result.Error("Error al crear")
            } else {
                Result.Error("Error: ${response.code()}")
            }
        } catch (e: Exception) {
            Result.Error(e.message ?: "Error de conexión")
        }
    }

    suspend fun updateDiary(id: Long, diario: Diario): Result<Diario> {
        return try {
            val token = getToken()
            if (token.isNullOrEmpty()) return Result.Error("No autenticado")
            val response = apiService.actualizarDiario("Bearer $token", id, diario)
            if (response.isSuccessful) {
                response.body()?.let {
                    Result.Success(it)
                } ?: Result.Error("Error al actualizar")
            } else {
                Result.Error("Error: ${response.code()}")
            }
        } catch (e: Exception) {
            Result.Error(e.message ?: "Error de conexión")
        }
    }

    suspend fun deleteDiary(id: Long): Result<Unit> {
        return try {
            val token = getToken()
            if (token.isNullOrEmpty()) return Result.Error("No autenticado")
            val response = apiService.eliminarDiario("Bearer $token", id)
            if (response.isSuccessful) {
                Result.Success(Unit)
            } else {
                Result.Error("Error: ${response.code()}")
            }
        } catch (e: Exception) {
            Result.Error(e.message ?: "Error de conexión")
        }
    }
}
