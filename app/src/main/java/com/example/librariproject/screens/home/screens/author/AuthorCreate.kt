package com.example.librariproject.screens.home.screens.author

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.librariproject.data.author.AuthorData
import com.example.librariproject.data.user.UserData
import com.example.librariproject.screens.components.AlertWindow
import com.example.librariproject.screens.components.BarTop
import com.example.librariproject.screens.components.TextFieldOwn
import kotlinx.coroutines.launch

@Composable
fun CreateAuthor( navController: NavHostController, isDrawerOpen: MutableState<Boolean>){

        LaunchedEffect(Unit){
            isDrawerOpen.value=false
        }
        Box(modifier = Modifier
            .fillMaxSize()
            .background(Color(200, 230, 230))) {
            Column {
                BarTop(navController = navController, isDrawerOpe = isDrawerOpen, title ="Crear autor" )
                Card(
                    shape = RoundedCornerShape(20.dp
                    ),
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(40.dp),
                    backgroundColor = Color.White,

                    ) {

                    Column(
                        modifier = Modifier.padding(horizontal = 30.dp, vertical = 30.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center

                    ) {
                        val showDialog = remember { mutableStateOf(false) }
                        val identification= remember { mutableStateOf(TextFieldValue())}
                        val name= remember { mutableStateOf(TextFieldValue()) }
                        val country = remember { mutableStateOf(TextFieldValue()) }
                        val biography = remember { mutableStateOf(TextFieldValue()) }
                        val scope= rememberCoroutineScope()

                        val colorsText = TextFieldDefaults.outlinedTextFieldColors(
                            focusedBorderColor = Color.Magenta,
                            unfocusedBorderColor = Color.Black,
                            focusedLabelColor = Color.Black,
                            cursorColor = Color.Black,
                            textColor = Color.Black,
                        )
                        val modifierText = Modifier
                            .padding(2.dp)
                            .size(width = 200.dp, height = 60.dp)
                        val color = Color.Black

                        TextFieldOwn(
                            valueData = identification,
                            modifier = modifierText,
                            colorsText = colorsText,
                            color = color,
                            text = "Identificacion",
                            keyboardType = KeyboardType.Text,
                            visualTransformation = VisualTransformation.None
                        )
                        TextFieldOwn(
                            valueData = name,
                            modifier = modifierText,
                            colorsText = colorsText,
                            color = color,
                            text = "Nombres",
                            keyboardType = KeyboardType.Text,
                            visualTransformation = VisualTransformation.None
                        )
                        TextFieldOwn(
                            valueData = country,
                            modifier = modifierText,
                            colorsText = colorsText,
                            color = color,
                            text = "Nacionalidad",
                            keyboardType = KeyboardType.Text,
                            visualTransformation = VisualTransformation.None
                        )
                        TextFieldOwn(
                            valueData = biography,
                            modifier = modifierText,
                            colorsText = colorsText,
                            color = color,
                            text = "Biografia",
                            keyboardType = KeyboardType.Number,
                            visualTransformation = VisualTransformation.None
                        )
                        Button(
                            onClick = {
                                val author = AuthorData()
                                scope.launch {
                                    val data =author.createAuthor(
                                        id = identification.value.text.toInt(),
                                        name = name.value.text,
                                        country = country.value.text,
                                        biography = biography.value.text
                                    )

                                    if (data == null) {
                                        showDialog.value = true
                                    }
                                }

                            },
                            colors = ButtonDefaults.buttonColors(
                                contentColor = Color.White,
                                backgroundColor = Color.Black
                            ),
                            modifier = Modifier
                                .padding(10.dp),

                            ) {
                            Text(text = "Actualizar")
                        }

                        if (showDialog.value) {
                            AlertWindow(
                                showDialog = showDialog,
                                text = "Usuario o correo ya existe"
                            )
                        }
                    }

                }
            }
    }

}