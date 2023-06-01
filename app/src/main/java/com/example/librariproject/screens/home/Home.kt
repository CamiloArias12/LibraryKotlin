package com.example.librariproject.screens.home

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.librariproject.GetUserQuery
import com.example.librariproject.data.user.UserData
import com.example.librariproject.data.user.UserValue
import com.example.library.routes.ButtonBarScreen
import com.example.librariproject.routes.HomeNavGraph
import kotlinx.coroutines.launch

import com.example.librariproject.R
import com.example.librariproject.routes.AuthorCrud
import com.example.librariproject.routes.BookCrud
import com.example.librariproject.routes.LoanCrud
import compose.icons.FontAwesomeIcons
import compose.icons.fontawesomeicons.Solid
import compose.icons.fontawesomeicons.solid.BookOpen

@RequiresApi(Build.VERSION_CODES.M)
@Composable
fun HomeScreen(navController: NavHostController = rememberNavController(), id:Int) {
    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()
    val isDrawerOpen = remember {mutableStateOf(true)}
    val rol = remember {mutableStateOf(false)}

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
    if (dataUser!=null) {
        rol.value= dataUser!!.rol
        Scaffold(
            backgroundColor = Color.White,
            scaffoldState = scaffoldState,
            floatingActionButton = { FloatingActionButtonCompose(navController = navController,rol=rol,id=id) },
            topBar = {
                if (isDrawerOpen.value) {
                    HomeTopBar(
                        onClickIcon = {
                            scope.launch {
                                scaffoldState.drawerState.open()
                            }
                        }
                    )
                }
            },
            bottomBar = {
                if (isDrawerOpen.value) {
                    BottonBar(navController = navController, dataUser = dataUser)
                }
            },
            drawerContent = {
                Drawer(
                    id = id,
                    navController = navController,
                    isDrawerOpen = { isDrawerOpen.value = false }
                )
            },
            drawerShape = RoundedCornerShape(topEnd = 16.dp, bottomEnd = 16.dp),
            drawerGesturesEnabled = true

        ) { paddingValues: PaddingValues ->
            PaddingValues(10.dp)
            Modifier.padding(paddingValues)
            HomeNavGraph(navController = navController, isDrawerOpe = isDrawerOpen, rol = rol,id=id)

        }
    }
}


@RequiresApi(Build.VERSION_CODES.M)
@Composable
fun  BottonBar(navController: NavHostController, dataUser: UserValue?) {


       if(dataUser!=null) {
            var screens = listOf<ButtonBarScreen>()

           screens = if (dataUser.rol) {
               listOf(
                   ButtonBarScreen.User,
                   ButtonBarScreen.Author,
                   ButtonBarScreen.Book,
                   ButtonBarScreen.Loan
               )
           }else{
               listOf(
                   ButtonBarScreen.BookStudent,
                   ButtonBarScreen.LoanStudent,
               )

           }
       BottomNavigation(
           backgroundColor = Color(2, 101, 157),
       ) {
           val navBackStackEntry by navController.currentBackStackEntryAsState()
           val currentDestination = navBackStackEntry?.destination?.route

           screens.forEach { screen ->
               BottomNavigationItem(
                   label = { Text(text = screen.title) },
                   icon = {
                       Icon(
                           imageVector = screen.icon,
                           contentDescription = null,
                           modifier = Modifier.size(20.dp)
                       )
                   },
                   selected = currentDestination == screen.route,
                   selectedContentColor = Color.White,
                   unselectedContentColor = Color.Black,
                   onClick = {
                       Log.d("route",screen.route)
                       navController.navigate(screen.route) {
                           popUpTo(navController.graph.findStartDestination().id) {
                               saveState = true
                           }
                           launchSingleTop = true
                           restoreState = true
                       }
                   },
                   alwaysShowLabel = false
               )
           }
       }
   }
   }
@OptIn(ExperimentalAnimationApi::class)
@Composable
fun FloatingActionButtonCompose(
    navController: NavHostController,
    rol: MutableState<Boolean>,
    id: Int
) {
    val fabExpandedState = remember { mutableStateOf(false) }

    Column {
        FloatingActionButton(
            onClick = { fabExpandedState.value = !fabExpandedState.value },
            content = {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Expand FAB"
                )
            }
        )

        if (fabExpandedState.value) {

            if (rol.value) {
                AnimatedVisibility(
                    visible = fabExpandedState.value,
                    enter = scaleIn(initialScale = 0.5f),
                    exit = scaleOut(targetScale = 0.5f)
                ) {
                    Column {
                        FloatingActionButton(

                            onClick = {
                                fabExpandedState.value = false
                                navController.navigate(AuthorCrud.Create.route)
                            },
                            content = {
                                Icon(
                                    painter = painterResource(id = R.drawable.author),
                                    contentDescription = "Menu item 1",
                                    modifier = Modifier.size(30.dp)
                                )
                            }
                        )
                        FloatingActionButton(
                            onClick = {
                                fabExpandedState.value = false
                                navController.navigate(BookCrud.Create.route)

                            },
                            content = {
                                Icon(
                                    imageVector = FontAwesomeIcons.Solid.BookOpen,
                                    contentDescription = "Menu item 2",
                                    modifier = Modifier.size(30.dp)
                                )
                            }
                        )
                    }
                }
            }else{

                Column {
                    FloatingActionButton(
                        onClick = {
                            fabExpandedState.value = false
                            navController.navigate(LoanCrud.Create.route+ "/$id")
                        },
                        content = {
                            Icon(
                                painter = painterResource(id = R.drawable.loan),
                                contentDescription = "Menu item 1",
                                modifier = Modifier.size(30.dp)
                            )
                        }
                    )
                }
            }
        }
    }

}
@RequiresApi(Build.VERSION_CODES.M)
@Preview(showBackground = true, device = "id:Nexus 4")
@Composable
fun PreviBar(){
    HomeScreen(id = 1)
}