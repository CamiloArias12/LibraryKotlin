package com.example.librariproject.screens.components

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import com.vanpra.composematerialdialogs.MaterialDialog
import com.vanpra.composematerialdialogs.datetime.date.DatePickerDefaults
import com.vanpra.composematerialdialogs.datetime.date.datepicker
import com.vanpra.composematerialdialogs.rememberMaterialDialogState
import java.time.LocalDate


@Composable
fun CalendarWithDateSelection(
    pickedDateStart: MutableState<LocalDate>,
    pickedDateStartFlag: MutableState<Boolean>
) {
    val dateDialogState = rememberMaterialDialogState()
    dateDialogState.show()
    MaterialDialog(
        dialogState = dateDialogState,
        buttons = {
            positiveButton(text = "Ok", textStyle = TextStyle(color = Color(2,101,157)) ) {
                Log.d("sasdf",pickedDateStart.value.toString())
                pickedDateStartFlag.value=false
            }
            negativeButton(text = "Cancelar",textStyle = TextStyle(color = Color(2,101,157))){
                pickedDateStartFlag.value=false
            }
        },

    ) {
        datepicker(
            initialDate = LocalDate.now(),
            title = "Fecha",
            colors= DatePickerDefaults.colors(headerBackgroundColor = Color(2,101,157) ,
                dateActiveBackgroundColor = Color(2,101,157),
                 )

        ) {
            pickedDateStart.value = it
        }
    }
}

