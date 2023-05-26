package com.example.librariproject.screens.components

import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.graphics.Color

@Composable
fun AlertWindow(showDialog:MutableState<Boolean>, text:String){
    AlertDialog(
        onDismissRequest = { showDialog.value = false },
        title = { Text(text = text) },
        confirmButton = {
            Button(
                onClick = { showDialog.value = false },
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = Color(2,101,157),
                    contentColor = Color.White
                )
            ) {
                Text(text = "Aceptar")
            }
        }
    )
}