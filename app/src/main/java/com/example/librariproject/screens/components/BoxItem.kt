package com.example.librariproject.screens.components

import androidx.compose.foundation.layout.size
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ExposedDropdownMenuBox
import androidx.compose.material.ExposedDropdownMenuDefaults
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun BoxItem(option: MutableState<String>, items:List<String>) {
    var expanded by remember {
        mutableStateOf(false)
    }
    ExposedDropdownMenuBox(expanded = expanded, onExpandedChange = { expanded = it }) {
        TextField(
            value = option.value,
            onValueChange = {},
            readOnly = true,
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
            },
            colors = ExposedDropdownMenuDefaults.textFieldColors(),
            singleLine = false,
            modifier = Modifier.size(width = 170.dp, height = 50.dp)

        )
        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false })
        {
            items.forEach { item ->
                DropdownMenuItem(onClick = {
                    option.value = item
                    expanded = false
                }) {
                    Text(text = item)
                }

            }
        }
    }
}


