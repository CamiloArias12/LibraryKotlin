package com.example.librariproject.screens.components

import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp

@Composable
fun DeleteConfirmationScreen(
    message:String,
    onDeleteConfirmed: () -> Unit,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = { onDismiss() },
        text = { Text(text =  message,fontSize = 18.sp, color = Color.Black)},
        confirmButton = {
            Button(
                onClick = { onDeleteConfirmed() },
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = Color(2,101,157),
                    contentColor = Color.White
                )
            ) {
                Text(text = "SI")
            }
        },
        dismissButton = {
            Button(
                onClick = { onDismiss() },
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = Color(2,101,157),
                    contentColor = Color.White
                )
            ) {
                Text(text = "NO")
            }
        }
    )
}


