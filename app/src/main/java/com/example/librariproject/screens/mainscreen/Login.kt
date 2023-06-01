package com.example.librariproject.screens.mainscreen

import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.launch
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import com.example.librariproject.R
import com.example.librariproject.UserLoginMutation
import com.example.librariproject.data.user.UserData
import com.example.librariproject.routes.AuthScreen
import com.example.librariproject.screens.components.AlertWindow
import com.example.library.routes.Graph

@Composable
fun LoginScreen(navController: NavHostController){

    Scaffold(
        content =  { innerPadding ->

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White)
                    .padding(innerPadding),
            ) {
                Image(
                    painter = painterResource(id = R.drawable.logo), contentDescription = null,
                    modifier = Modifier
                        .size(150.dp)
                        .align(Alignment.TopCenter)
                )

                Card(
                    shape = RoundedCornerShape(topStart = 40.dp, topEnd = 40.dp),
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = 200.dp),
                    backgroundColor = Color(2,101,157),

                ) {

                    Column(
                        modifier = Modifier.padding(horizontal = 30.dp, vertical = 50.dp),
                        horizontalAlignment = CenterHorizontally,
                        verticalArrangement = Arrangement.Center


                    ) {
                        val email = remember { mutableStateOf(TextFieldValue()) }
                        val password = remember { mutableStateOf(TextFieldValue()) }
                        val showDialog = remember { mutableStateOf(false) }
                        val scope = rememberCoroutineScope( )
                        val message= remember {
                            mutableStateOf("")
                        }

                        OutlinedTextField(
                            label = { Text(text = "Correo", color = Color.White) },
                            value = email.value,
                            onValueChange = { email.value = it },

                            leadingIcon = {
                                Icon(
                                    imageVector = Icons.Outlined.Email,
                                    contentDescription = "icono",
                                    modifier = Modifier.size(20.dp)
                                )
                            },
                            modifier = Modifier.padding(20.dp),
                            colors = TextFieldDefaults.outlinedTextFieldColors(
                                focusedBorderColor = Color.Black,
                                unfocusedBorderColor = Color.White,
                                focusedLabelColor = Color.Black,
                                cursorColor = Color.White,
                                textColor=Color.White
                            ),
                            textStyle = TextStyle(fontSize = 15.sp)

                        )
                        OutlinedTextField(
                            label = { Text(text = "Contrasena", color = Color.White) },
                            value = password.value,
                            onValueChange = { password.value = it },
                            leadingIcon = {
                                Icon(
                                    imageVector = Icons.Outlined.Lock,
                                    contentDescription = "icono",
                                    modifier = Modifier.size(20.dp)
                                )
                            },
                            modifier = Modifier
                                .padding(20.dp),
                            visualTransformation = PasswordVisualTransformation(),
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),

                            colors = TextFieldDefaults.outlinedTextFieldColors(
                                focusedBorderColor = Color.Black,
                                unfocusedBorderColor = Color.White,
                                focusedLabelColor = Color.Black,
                                cursorColor = Color.White ,
                                textColor = Color.White
                            ),
                            textStyle = TextStyle(fontSize = 15.sp)

                        )

                        Button(
                            onClick = {
                                scope.launch {
                                    val  user= UserData()
                                    val data : UserLoginMutation.UserValidate?= user.loginUser(email =email.value.text, password = password.value.text)
                                    if (data!=null){
                                        if (data.isActive)
                                            navController.navigate(Graph.HOME+ "/${data.id.toInt()}")
                                        else {
                                            message.value = "Usuario inactivo"
                                            showDialog.value = true
                                        }
                                    }else{
                                        message.value = "Usuario o contrasena incorrectos"
                                        showDialog.value=true
                                    }
                                }
                            },
                            colors = ButtonDefaults.buttonColors(
                                contentColor = Color.White,
                                backgroundColor = Color.Black
                            ),
                            modifier = Modifier
                                .padding(20.dp),
                            ) {
                            Text(text = "Iniciar sesion")
                        }
                        if (showDialog.value) {
                            AlertWindow(showDialog = showDialog, text = message.value)
                        }
                ClickableText(
                    text = AnnotatedString("Registrarse"),
                    onClick = {navController.navigate(AuthScreen.SignIn.route)},
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

    )
}


@Preview(showBackground = true)
@Composable
fun PreviewLogin(){
LoginScreen(navController = rememberNavController())
}