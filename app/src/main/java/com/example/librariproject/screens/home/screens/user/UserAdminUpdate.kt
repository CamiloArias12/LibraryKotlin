package com.example.librariproject.screens.home.screens.user

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ExposedDropdownMenuBox
import androidx.compose.material.ExposedDropdownMenuDefaults
import androidx.compose.material.Text
import androidx.compose.material.TextField
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.librariproject.GetUserQuery
import com.example.librariproject.data.user.UserData
import com.example.librariproject.data.user.UserValue
import com.example.librariproject.screens.components.BarTop
import com.example.librariproject.screens.components.BoxItem
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun AdminUpdate(id: Int, navController: NavHostController ,isDrawerOpe:MutableState<Boolean>) {

    var dataUser by remember { mutableStateOf<UserValue?>(null) }
    val user = UserData()
    val scope = rememberCoroutineScope()
    LaunchedEffect(Unit) {
        isDrawerOpe.value=false
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
    if (dataUser != null) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(200, 230, 230))
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
            ) {
                BarTop(
                    navController = navController,
                    isDrawerOpe = isDrawerOpe,
                    title = "Informacion Personal"
                )

                val listActive = listOf("Activo", "Inactivo")
                val rolAdSt = listOf("Administrador", "Estudiante")

                val isActive = remember {
                    mutableStateOf("")
                }
                val rol = remember {
                    mutableStateOf("")
                }

                if (dataUser!!.isActive) {
                    isActive.value = listActive[0]
                } else {
                    isActive.value = listActive[1]
                }
                if (dataUser!!.rol) {
                    rol.value = rolAdSt[0]
                } else {
                    rol.value = rolAdSt[1]
                }

                Text(text = "Estado")
                BoxItem(option = isActive, items = listActive)

                Text(text = "Rol")
                BoxItem(option = rol, items = rolAdSt)

                Button(
                    onClick = {
                        dataUser!!.isActive = isActive.value == listActive[0]
                        dataUser!!.rol = rol.value == rolAdSt[0]
                        scope.launch {
                            user.updateUserAdmin(
                                id = dataUser!!.id,
                                isActive = dataUser!!.isActive,
                                rol = dataUser!!.rol
                            )
                        }
                        Log.d("ROl", dataUser.toString())

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
            }
        }

    }
}

