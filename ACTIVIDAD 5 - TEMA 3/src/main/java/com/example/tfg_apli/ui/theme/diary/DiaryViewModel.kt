package com.example.tfg_apli.ui.diary

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tfg_apli.data.model.Diario
import com.example.tfg_apli.data.repository.DiaryRepository
import com.example.tfg_apli.utils.Result
import kotlinx.coroutines.launch

class DiaryViewModel(
    private val diaryRepository: DiaryRepository
) : ViewModel() {
    var uiState by mutableStateOf<DiaryUiState>(DiaryUiState.Loading)
        private set

    var selectedDiary by mutableStateOf<Diario?>(null)
        private set

    fun loadDiaries() {
        viewModelScope.launch {
            uiState = DiaryUiState.Loading
            val result = diaryRepository.getDiaries()
            uiState = when (result) {
                is Result.Success -> DiaryUiState.Success(result.data)
                is Result.Error -> DiaryUiState.Error(result.message)
                else -> DiaryUiState.Error("Error desconocido")
            }
        }
    }

    fun loadDiary(id: Long) {
        viewModelScope.launch {
            uiState = DiaryUiState.Loading
            val result = diaryRepository.getDiary(id)
            uiState = when (result) {
                is Result.Success -> {
                    selectedDiary = result.data
                    DiaryUiState.Success(listOf(result.data))
                }
                is Result.Error -> DiaryUiState.Error(result.message)
                else -> DiaryUiState.Error("Error desconocido")
            }
        }
    }

    fun createDiary(diario: Diario) {
        viewModelScope.launch {
            uiState = DiaryUiState.Loading
            val result = diaryRepository.createDiary(diario)
            uiState = when (result) {
                is Result.Success -> DiaryUiState.OperationSuccess
                is Result.Error -> DiaryUiState.Error(result.message)
                else -> DiaryUiState.Error("Error desconocido")
            }
        }
    }

    fun updateDiary(id: Long, diario: Diario) {
        viewModelScope.launch {
            uiState = DiaryUiState.Loading
            val result = diaryRepository.updateDiary(id, diario)
            uiState = when (result) {
                is Result.Success -> DiaryUiState.OperationSuccess
                is Result.Error -> DiaryUiState.Error(result.message)
                else -> DiaryUiState.Error("Error desconocido")
            }
        }
    }

    fun deleteDiary(id: Long) {
        viewModelScope.launch {
            uiState = DiaryUiState.Loading
            val result = diaryRepository.deleteDiary(id)
            uiState = when (result) {
                is Result.Success -> DiaryUiState.OperationSuccess
                is Result.Error -> DiaryUiState.Error(result.message)
                else -> DiaryUiState.Error("Error desconocido")
            }
        }
    }

    fun resetState() {
        uiState = DiaryUiState.Idle
    }

    sealed class DiaryUiState {
        object Idle : DiaryUiState()
        object Loading : DiaryUiState()
        data class Success(val diaries: List<Diario>) : DiaryUiState()
        data class Error(val message: String) : DiaryUiState()
        object OperationSuccess : DiaryUiState()
    }
    suspend fun updateDiaryAndReturnResult(id: Long, diario: Diario): Boolean {
        val result = diaryRepository.updateDiary(id, diario)
        return result is Result.Success
    }
    suspend fun createDiaryAndReturnResult(diario: Diario): Boolean {
        val result = diaryRepository.createDiary(diario)
        return result is Result.Success
    }
}
