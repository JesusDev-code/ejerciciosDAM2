package com.example.tfg_apli

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.tfg_apli.data.remote.RetrofitClient
import com.example.tfg_apli.data.repository.AuthRepository
import com.example.tfg_apli.data.repository.DiaryRepository
import com.example.tfg_apli.ui.auth.AuthScreen
import com.example.tfg_apli.ui.auth.AuthViewModel
import com.example.tfg_apli.ui.diary.DiaryDetailScreen
import com.example.tfg_apli.ui.diary.DiaryEditorScreen
import com.example.tfg_apli.ui.diary.DiaryListScreen
import com.example.tfg_apli.ui.diary.DiaryViewModel
import com.example.tfg_apli.ui.theme.DiaryAppTheme
import com.example.tfg_apli.utils.FirebaseAuthHelper
import com.example.tfg_apli.utils.TokenManager
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {

    private val tokenManager by lazy { TokenManager(this) }
    private val firebaseAuthHelper by lazy { FirebaseAuthHelper() }

    private val apiService by lazy { RetrofitClient.create() }

    private val diaryRepository by lazy { DiaryRepository(apiService, tokenManager) }
    private val authRepository by lazy { AuthRepository(apiService, tokenManager, firebaseAuthHelper) }

    private val viewModelFactory by lazy {
        object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return when {
                    modelClass.isAssignableFrom(AuthViewModel::class.java) -> {
                        AuthViewModel(authRepository) as T
                    }
                    modelClass.isAssignableFrom(DiaryViewModel::class.java) -> {
                        DiaryViewModel(diaryRepository) as T
                    }
                    else -> throw IllegalArgumentException("ViewModel desconocido: ${modelClass.name}")
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            DiaryAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    val isLoggedIn = firebaseAuthHelper.getCurrentUser() != null

                    NavHost(
                        navController = navController,
                        startDestination = if (isLoggedIn) "home" else "auth"
                    ) {
                        composable("auth") {
                            val authViewModel: AuthViewModel = viewModel(factory = viewModelFactory)
                            AuthScreen(
                                viewModel = authViewModel,
                                onAuthSuccess = {
                                    navController.navigate("home") {
                                        popUpTo("auth") { inclusive = true }
                                    }
                                }
                            )
                        }

                        composable("home") {
                            val diaryViewModel: DiaryViewModel = viewModel(factory = viewModelFactory)
                            DiaryListScreen(
                                viewModel = diaryViewModel,
                                navController = navController,
                                onLogout = {
                                    lifecycleScope.launch {
                                        authRepository.logout()
                                        navController.navigate("auth") {
                                            popUpTo("home") { inclusive = true }
                                        }
                                    }
                                }
                            )
                        }

                        composable(
                            route = "diary_detail/{diaryId}",
                            arguments = listOf(navArgument("diaryId") { type = NavType.LongType })
                        ) { backStackEntry ->
                            val diaryId = backStackEntry.arguments?.getLong("diaryId") ?: 0L
                            val diaryViewModel: DiaryViewModel = viewModel(factory = viewModelFactory)
                            DiaryDetailScreen(
                                diaryId = diaryId,
                                viewModel = diaryViewModel,
                                navController = navController
                            )
                        }

                        composable(
                            route = "diary_editor?diaryId={diaryId}", // IMPORTANTE: ESTE FORMATO
                            arguments = listOf(
                                navArgument("diaryId") {
                                    type = NavType.LongType
                                    defaultValue = -1L
                                    nullable = false
                                }
                            )
                        ) { backStackEntry ->
                            val diaryId = backStackEntry.arguments?.getLong("diaryId")
                            val diaryViewModel: DiaryViewModel = viewModel(factory = viewModelFactory)
                            DiaryEditorScreen(
                                diaryId = if (diaryId == -1L) null else diaryId,
                                viewModel = diaryViewModel,
                                navController = navController
                            )
                        }
                    }
                }
            }
        }
    }
}
