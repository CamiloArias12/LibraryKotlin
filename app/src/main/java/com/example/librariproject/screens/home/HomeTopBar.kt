package com.example.librariproject.screens.home

import android.media.Image
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.ModalDrawer
import androidx.compose.material.ScaffoldState
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.rounded.AccountBox
import androidx.compose.material.icons.rounded.AccountCircle
import androidx.compose.material.icons.rounded.Menu
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.librariproject.GetUserQuery
import com.example.librariproject.R
import com.example.librariproject.data.user.UserData
import com.example.librariproject.data.user.UserValue
import com.example.librariproject.routes.UserCrud
import com.example.library.routes.ButtonBarScreen
import com.example.library.routes.Graph
import compose.icons.FontAwesomeIcons
import compose.icons.fontawesomeicons.Solid
import compose.icons.fontawesomeicons.solid.UserCircle
import compose.icons.fontawesomeicons.solid.UserCog
import kotlinx.coroutines.launch


@Composable
fun HomeTopBar(onClickIcon: () -> Unit){
    TopAppBar(
        backgroundColor = Color(2,101,157),
        title = {
        },
        navigationIcon = {

            IconButton(
                onClick = onClickIcon,
            ) {
                Icon(
                    imageVector = FontAwesomeIcons.Solid.UserCircle,
                    contentDescription = "",
                    modifier = Modifier.size(25.dp)
                )
            }
        })
}

@Composable
fun Drawer(
    id: Int,
    navController: NavHostController,
    isDrawerOpen: () -> Unit
) {
    var dataUser by remember { mutableStateOf<UserValue?>(null) }
    val scope = rememberCoroutineScope()
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
            password = data.password
        )
        Log.d("Data", dataUser.toString())
    }

    Surface(
        modifier=Modifier.fillMaxSize(),
        color =Color(2, 101, 157),
    ) {
        Column(
            horizontalAlignment = Alignment.End
        ) {

            IconButton(onClick = {

            }) {
                Icon(painter = painterResource(id = R.drawable.logout) , contentDescription = null, modifier = Modifier.size(40.dp))
            }
        }

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            Image(
                painter = painterResource(R.drawable.jdc),
                contentDescription = "App icon"
            )

            Spacer(modifier = Modifier.padding(10.dp))
            Card(
                shape = RoundedCornerShape(20.dp),
                modifier = Modifier.size(250.dp),

                backgroundColor = Color.White
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {

                    val style = TextStyle(fontSize = 16.sp, color = Color.DarkGray)
                    Text(
                        text = "Información personal",
                        style = TextStyle(
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp,
                            color = Color.Black
                        ),
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                    Text(
                        text = "Cédula: ${dataUser?.id}",
                        style = style
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Nombres: ${dataUser?.name}",
                        style = style
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Apellidos: ${dataUser?.lastName}",
                        style = style
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Teléfono: ${dataUser?.phone}",
                        style = style
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Dirección: ${dataUser?.address}",
                        style = style
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Correo: ${dataUser?.email}",
                        style = style
                    )
                }
            }
            Button(
                onClick = {
                    isDrawerOpen()
                    navController.navigate(UserCrud.Update.route+"/$id")
                          },
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = Color.Black,
                    contentColor = Color.White
                ),
                modifier = Modifier.padding(16.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = FontAwesomeIcons.Solid.UserCog,
                        contentDescription = "Compose Icon",
                        tint = Color.White,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(text = "Actualizar")
                }
            }

        }
    }

}

@Preview
@Composable
fun DrawerPreview() {
        Drawer(id=10, navController = rememberNavController(), isDrawerOpen = {})
}

@Preview(showBackground = true)
@Composable
fun PreviewTop(){
    HomeTopBar(onClickIcon = {})
}