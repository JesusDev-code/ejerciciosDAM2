package com.example.tarea1_tema4

import android.graphics.Bitmap
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.tarea1_tema4.ui.theme.MultimediaAppTheme

import com.example.tarea1_tema4.ui.theme.screens.CameraScreen
import com.example.tarea1_tema4.ui.theme.screens.ExoPlayerAudioScreen
import com.example.tarea1_tema4.ui.theme.screens.ExoPlayerVideoScreen
import com.example.tarea1_tema4.ui.theme.screens.MenuScreen
import com.example.tarea1_tema4.ui.theme.screens.SoundPoolScreen
import com.example.tarea1_tema4.ui.theme.screens.GalleryScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MultimediaAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    // Estado compartido: lista de fotos (máximo 5)
                    var photoList by remember { mutableStateOf(listOf<Bitmap>()) }

                    NavHost(
                        navController = navController,
                        startDestination = "menu"
                    ) {
                        composable("menu") {
                            MenuScreen(navController = navController)
                        }
                        composable("soundpool") {
                            SoundPoolScreen(onBack = { navController.popBackStack() })
                        }
                        composable("audio") {
                            ExoPlayerAudioScreen(onBack = { navController.popBackStack() })
                        }
                        composable("video") {
                            ExoPlayerVideoScreen(onBack = { navController.popBackStack() })
                        }
                        composable("camera") {
                            CameraScreen(
                                navController = navController,
                                photoList = photoList,
                                setPhotoList = { newList -> photoList = newList.takeLast(5) },
                                onBack = { navController.popBackStack() }
                            )
                        }
                        composable("gallery") {
                            GalleryScreen(
                                photoList = photoList,
                                onBack = { navController.popBackStack() }
                            )
                        }
                    }
                }
            }
        }
    }
}