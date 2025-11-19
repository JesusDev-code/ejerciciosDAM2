package com.example.tarea1_tema4.ui.theme.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.tarea1_tema4.ui.theme.CardBackground
import com.example.tarea1_tema4.ui.theme.PinkDemo
import com.example.tarea1_tema4.ui.theme.PinkDemoDark
import com.example.tarea1_tema4.ui.theme.TextPrimary
import com.example.tarea1_tema4.ui.theme.TextSecondary

@Composable
fun WelcomeModal(onDismiss: () -> Unit) {
    Dialog(onDismissRequest = onDismiss) {
        Surface(
            shape = RoundedCornerShape(24.dp),
            color = CardBackground,
            shadowElevation = 8.dp
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(32.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Icon
                Box(
                    modifier = Modifier
                        .size(80.dp)
                        .clip(CircleShape)
                        .background(
                            Brush.linearGradient(
                                colors = listOf(PinkDemo, PinkDemoDark)
                            )
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Text("⭐", fontSize = 40.sp)
                }

                Spacer(modifier = Modifier.height(24.dp))

                Text(
                    text = "¡Bienvenido a tu aplicación para ser un verdadero DJ!",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextPrimary,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "Explora todas las funcionalidades multimedia con datos de ejemplo. Esta es una demostración interactiva del proyecto.",
                    fontSize = 15.sp,
                    color = TextSecondary,
                    textAlign = TextAlign.Center,
                    lineHeight = 22.sp
                )

                Spacer(modifier = Modifier.height(24.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {

                    Button(
                        onClick = onDismiss,
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(16.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = PinkDemo
                        )
                    ) {
                        Text("Siguiente", fontWeight = FontWeight.SemiBold)
                    }
                }
            }
        }
    }
}