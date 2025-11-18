package com.example.tfg_apli.ui.auth

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tfg_apli.data.repository.AuthRepository
import com.example.tfg_apli.utils.Result
import kotlinx.coroutines.launch

class AuthViewModel(
    private val authRepository: AuthRepository
) : ViewModel() {

    var authState by mutableStateOf<AuthState>(AuthState.Idle)
        private set

    fun login(email: String, password: String) {
        viewModelScope.launch {
            authState = AuthState.Loading
            val result = authRepository.login(email, password)
            authState = when (result) {
                is Result.Success -> AuthState.Success
                is Result.Error -> AuthState.Error(result.message)
                else -> AuthState.Error("Error desconocido")
            }
        }
    }

    fun register(email: String, password: String, nombre: String) {
        viewModelScope.launch {
            authState = AuthState.Loading
            val result = authRepository.register(email, password, nombre)
            authState = when (result) {
                is Result.Success -> AuthState.Success
                is Result.Error -> AuthState.Error(result.message)
                else -> AuthState.Error("Error desconocido")
            }
        }
    }

    fun resetState() {
        authState = AuthState.Idle
    }
}

sealed class AuthState {
    object Idle : AuthState()
    object Loading : AuthState()
    object Success : AuthState()
    data class Error(val message: String) : AuthState()
}