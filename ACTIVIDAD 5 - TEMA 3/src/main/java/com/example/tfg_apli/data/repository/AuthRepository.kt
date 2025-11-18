package com.example.tfg_apli.data.repository

import com.example.tfg_apli.data.model.UsuarioCreateDto
import com.example.tfg_apli.data.remote.ApiService
import com.example.tfg_apli.utils.FirebaseAuthHelper
import com.example.tfg_apli.utils.Result
import com.example.tfg_apli.utils.TokenManager
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.tasks.await

class AuthRepository(
    private val apiService: ApiService,
    private val tokenManager: TokenManager,
    private val firebaseAuthHelper: FirebaseAuthHelper
) {
    // Login
    suspend fun login(email: String, password: String): Result<Unit> {
        return try {
            when (val result = firebaseAuthHelper.login(email, password)) {
                is Result.Success -> {
                    val user = FirebaseAuth.getInstance().currentUser
                    val idToken = user?.getIdToken(true)?.await()?.token
                    if (idToken != null) {
                        tokenManager.saveToken(idToken)
                        Result.Success(Unit)
                    } else {
                        Result.Error("No se pudo obtener el idToken de Firebase al hacer login.")
                    }
                }
                is Result.Error -> Result.Error(result.message)
                else -> Result.Error("Error desconocido")
            }
        } catch (e: Exception) {
            Result.Error(e.message ?: "Error en login")
        }
    }

    // Registro
    suspend fun register(email: String, password: String, nombre: String, departamentoId: Int = 1): Result<Unit> {
        return try {
            when (val result = firebaseAuthHelper.register(email, password)) {
                is Result.Success -> {
                    val user = FirebaseAuth.getInstance().currentUser
                    val idToken = user?.getIdToken(true)?.await()?.token
                    if (idToken != null) {
                        tokenManager.saveToken(idToken)
                        val response = apiService.registrarUsuario(
                            token = "Bearer $idToken",
                            request = UsuarioCreateDto(
                                nombre = nombre,
                                email = email,
                                rolId = 1,
                                departamentoId = departamentoId
                            )
                        )
                        if (response.isSuccessful) {
                            Result.Success(Unit)
                        } else {
                            Result.Error("Error al registrar en backend: ${response.code()}")
                        }
                    } else {
                        Result.Error("No se pudo obtener el idToken de Firebase al registrar.")
                    }
                }
                is Result.Error -> Result.Error(result.message)
                else -> Result.Error("Error desconocido")
            }
        } catch (e: Exception) {
            Result.Error(e.message ?: "Error en registro")
        }
    }

    suspend fun logout() {
        firebaseAuthHelper.logout()
        tokenManager.clearToken()
    }
}
