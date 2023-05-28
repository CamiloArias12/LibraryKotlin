package com.example.librariproject.screens.home.screens.user

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.librariproject.GetUserQuery
import com.example.librariproject.data.user.UserData
import com.example.librariproject.data.user.UserValue
import com.example.librariproject.screens.components.AlertWindow
import com.example.librariproject.screens.components.TextFieldOwn
import compose.icons.FontAwesomeIcons
import compose.icons.fontawesomeicons.Solid
import compose.icons.fontawesomeicons.solid.ArrowLeft
import kotlinx.coroutines.launch


@Composable
fun UpdateUserScreen(
    navController: NavHostController,
    isDrawerOpe: MutableState<Boolean>,
    id: Int
){
    var dataUser by remember { mutableStateOf<UserValue?>(null) }

    LaunchedEffect(Unit) {

        val user = UserData()
        val data: GetUserQuery.Users? = user.getUser(id = id)
        dataUser = UserValue(
            id = data?.id?.toInt()!!,
            name = data.firstName,
            lastName = data.lastName,
            phone = data.phone,
            address = data.address,
            email = data.email,
            password = data.password,
            isActive = data.isActive,
            rol = data.rol
        )
     }
    if(dataUser!=null) {
        Box(modifier = Modifier
            .fillMaxSize()
            .background(Color(200, 230, 230))) {
            Column {

                TopAppBar(
                    backgroundColor = Color(2, 101, 157),

                ) {
                    IconButton(onClick = {
                        isDrawerOpe.value = true
                        navController.popBackStack()
                    }) {
                        Icon(
                            imageVector = FontAwesomeIcons.Solid.ArrowLeft,
                            contentDescription = null,
                            modifier = Modifier.size(20.dp)
                        )

                    }
                }
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
                        val firstName = remember { mutableStateOf(TextFieldValue(dataUser?.name.toString())) }
                        val lastName = remember { mutableStateOf(TextFieldValue(dataUser?.lastName.toString())) }
                        val phone = remember { mutableStateOf(TextFieldValue(dataUser?.phone.toString())) }
                        val address = remember { mutableStateOf(TextFieldValue(dataUser?.address.toString())) }
                        val email = remember { mutableStateOf(TextFieldValue(dataUser?.email.toString())) }
                        val password = remember { mutableStateOf(TextFieldValue(dataUser?.password.toString())) }
                        val scope = rememberCoroutineScope()


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
                            valueData = firstName,
                            modifier = modifierText,
                            colorsText = colorsText,
                            color = color,
                            text = "Nombres",
                            keyboardType = KeyboardType.Text,
                            visualTransformation = VisualTransformation.None
                        )
                        TextFieldOwn(
                            valueData = lastName,
                            modifier = modifierText,
                            colorsText = colorsText,
                            color = color,
                            text = "Apellidos",
                            keyboardType = KeyboardType.Text,
                            visualTransformation = VisualTransformation.None
                        )
                        TextFieldOwn(
                            valueData = address,
                            modifier = modifierText,
                            colorsText = colorsText,
                            color = color,
                            text = "Direeccion",
                            keyboardType = KeyboardType.Text,
                            visualTransformation = VisualTransformation.None
                        )
                        TextFieldOwn(
                            valueData = phone,
                            modifier = modifierText,
                            colorsText = colorsText,
                            color = color,
                            text = "Telefono",
                            keyboardType = KeyboardType.Number,
                            visualTransformation = VisualTransformation.None
                        )
                        TextFieldOwn(
                            valueData = email,
                            modifier = modifierText,
                            colorsText = colorsText,
                            color = color,
                            text = "Correo electronico",
                            keyboardType = KeyboardType.Email,
                            visualTransformation = VisualTransformation.None
                        )
                        TextFieldOwn(
                            valueData = password,
                            modifier = modifierText,
                            colorsText = colorsText,
                            color = color,
                            text = "Contrasena",
                            keyboardType = KeyboardType.Text,
                            visualTransformation = PasswordVisualTransformation()
                        )
                        Button(
                            onClick = {
                                val user = UserData()
                                scope.launch {
                                    val data = user.updateUser(
                                        id = id,
                                        firstName = firstName.value.text,
                                        lastName = lastName.value.text,
                                        address = address.value.text,
                                        phone = phone.value.text,
                                        email = email.value.text,
                                        password = password.value.text
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

}


@Preview(showBackground = true)
@Composable
fun PrevieCo(){

    val isDrawerOpen = remember { mutableStateOf(true) }
    UpdateUserScreen(navController = rememberNavController(), isDrawerOpe =isDrawerOpen,0 )
}

