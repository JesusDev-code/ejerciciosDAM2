package com.example.tfg_apli.utils

import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.tasks.await

class FirebaseAuthHelper {
    private val auth = FirebaseAuth.getInstance()

    fun getCurrentUser() = auth.currentUser

    suspend fun login(email: String, password: String): Result<String> {
        return try {
            val result = auth.signInWithEmailAndPassword(email, password).await()
            val token = result.user?.getIdToken(false)?.await()?.token
            if (token != null) Result.Success(token) else Result.Error("No se pudo obtener el token")
        } catch (e: Exception) {
            Result.Error(e.message ?: "Error de autenticación")
        }
    }

    suspend fun register(email: String, password: String): Result<String> {
        return try {
            val result = auth.createUserWithEmailAndPassword(email, password).await()
            val token = result.user?.getIdToken(false)?.await()?.token
            if (token != null) Result.Success(token) else Result.Error("No se pudo obtener el token")
        } catch (e: Exception) {
            Result.Error(e.message ?: "Error de registro")
        }
    }

    fun logout() {
        auth.signOut()
    }
}