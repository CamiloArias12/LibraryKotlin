package com.example.librariproject.screens.components

import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp


@Composable
fun TextFieldOwn(valueData :MutableState<TextFieldValue>,modifier: Modifier,colorsText: TextFieldColors, color:Color, text:String ,
                 keyboardType: KeyboardType, visualTransformation: VisualTransformation) {
    OutlinedTextField(
        label = { Text(text = text, color = color) },
        value = valueData.value,
        onValueChange = { valueData.value = it },
        keyboardOptions = KeyboardOptions(keyboardType=keyboardType),
        colors = colorsText,
        modifier = modifier,
        singleLine = true,
        visualTransformation = visualTransformation
    )
}