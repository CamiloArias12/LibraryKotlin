package com.example.librariproject.screens.mainscreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.librariproject.data.user.UserData
import com.example.librariproject.routes.AuthScreen
import com.example.librariproject.screens.components.AlertWindow
import com.example.librariproject.screens.components.TextFieldOwn
import kotlinx.coroutines.launch


@Composable
fun SingIn (navController: NavHostController){

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
    ) {

        Card(
            shape = RoundedCornerShape(topStart = 40.dp, topEnd = 40.dp),
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 20.dp),
            backgroundColor = Color(2,101,157),

        ) {

            Column(
                modifier = Modifier.padding(horizontal = 30.dp, vertical = 30.dp),
                horizontalAlignment = CenterHorizontally,
                verticalArrangement = Arrangement.Center

            ) {
                val showDialog =remember { mutableStateOf(false) }

                val identification = remember { mutableStateOf(TextFieldValue()) }
                val firstName = remember { mutableStateOf(TextFieldValue()) }
                val lastName = remember { mutableStateOf(TextFieldValue()) }
                val phone= remember { mutableStateOf(TextFieldValue()) }
                val adress = remember { mutableStateOf(TextFieldValue()) }
                val email = remember { mutableStateOf(TextFieldValue()) }
                val password = remember { mutableStateOf(TextFieldValue()) }
                val scope= rememberCoroutineScope()

                val colorsText = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = Color.White,
                    unfocusedBorderColor = Color.Black,
                    focusedLabelColor = Color.White,
                    cursorColor = Color.White,
                    textColor = Color.White,
                    )
                val modifierText=Modifier.padding(2.dp)
                val color=Color.White

                TextFieldOwn(
                    valueData = identification,
                    modifier =modifierText ,
                    colorsText = colorsText,
                    color = color,
                    text = "Identificacion",
                    keyboardType = KeyboardType.Number,
                    visualTransformation = VisualTransformation.None
                )
                 TextFieldOwn(
                    valueData = firstName,
                    modifier =modifierText ,
                    colorsText = colorsText,
                    color = color,
                    text = "Nombres",
                    keyboardType = KeyboardType.Text,
                    visualTransformation = VisualTransformation.None
                )
                TextFieldOwn(
                    valueData = lastName,
                    modifier =modifierText ,
                    colorsText = colorsText,
                    color = color,
                    text = "Apellidos",
                    keyboardType = KeyboardType.Text,
                    visualTransformation = VisualTransformation.None
                )
                 TextFieldOwn(
                    valueData = adress,
                    modifier =modifierText ,
                    colorsText = colorsText,
                    color = color,
                    text = "Direeccion",
                    keyboardType = KeyboardType.Text,
                    visualTransformation = VisualTransformation.None
                )
                 TextFieldOwn(
                    valueData = phone,
                    modifier =modifierText ,
                    colorsText = colorsText,
                    color = color,
                    text = "Telefono",
                    keyboardType = KeyboardType.Number,
                    visualTransformation = VisualTransformation.None
                )
                 TextFieldOwn(
                    valueData = email,
                    modifier =modifierText ,
                    colorsText = colorsText,
                    color = color,
                    text = "Correo electronico",
                    keyboardType = KeyboardType.Email,
                    visualTransformation = VisualTransformation.None
                )
                 TextFieldOwn(
                    valueData = password,
                    modifier =modifierText ,
                    colorsText = colorsText,
                    color = color,
                    text = "Contrasena",
                    keyboardType = KeyboardType.Text,
                    visualTransformation = PasswordVisualTransformation()
                )
                Button(
                    onClick = {
                         val user =UserData()
                        scope.launch {
                            val data = user.createUser(
                                id=(identification.value.text).toInt(),
                                firstName = firstName.value.text,
                                lastName = lastName.value.text,
                                address =adress.value.text,
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
                    Text(text = "Registrarse")
                }
                
                if (showDialog.value){
                    AlertWindow(showDialog = showDialog, text = "Usuario o correo ya existe")
                }
                ClickableText(
                    text = AnnotatedString("Iniciar sesion"),
                    onClick = {navController.navigate(AuthScreen.Login.route)},
                    style = TextStyle(
                        fontSize = 13.sp,
                        fontFamily = FontFamily.Monospace,
                        textDecoration = TextDecoration.Underline,
                        color = Color(255, 255, 255)
                    )
                )
            }
        }
    }
}

@Preview
@Composable
fun PreviewSin(){
    val navController= rememberNavController()
    SingIn(navController = navController)

}
