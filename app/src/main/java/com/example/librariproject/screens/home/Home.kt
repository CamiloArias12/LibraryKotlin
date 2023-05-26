package com.example.librariproject.screens.home

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.library.routes.ButtonBarScreen
import com.example.librariproject.routes.HomeNavGraph
import kotlinx.coroutines.launch


@RequiresApi(Build.VERSION_CODES.M)
@Composable
fun HomeScreen(navController: NavHostController = rememberNavController(), id:Int) {
    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()
    val isDrawerOpen = remember {mutableStateOf(true)}
    Scaffold(
        backgroundColor = Color.White,
        scaffoldState = scaffoldState,
        topBar = {
            if(isDrawerOpen.value) {
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
            if(isDrawerOpen.value){
                BottonBar(navController = navController)
            } },
        drawerContent = {
                    Drawer(id = id, navController = navController, isDrawerOpen = { isDrawerOpen.value=false}
                    )
            },
        drawerShape = RoundedCornerShape(topEnd = 16.dp, bottomEnd = 16.dp),
        drawerGesturesEnabled = true

    ) {paddingValues: PaddingValues -> PaddingValues(10.dp)
        Modifier.padding(paddingValues)
        HomeNavGraph(navController = navController,isDrawerOpe=isDrawerOpen)

    }
}


@RequiresApi(Build.VERSION_CODES.M)
@Composable
fun  BottonBar(navController: NavHostController) {
   val screens= listOf(
       ButtonBarScreen.User,
       ButtonBarScreen.Author,
       ButtonBarScreen.Book,
       ButtonBarScreen.Loan
   )
    BottomNavigation(
           backgroundColor = Color(2,101,157),
       ) {
           val navBackStackEntry by navController.currentBackStackEntryAsState()
           val currentDestination= navBackStackEntry?.destination?.route

           screens.forEach { screen ->
               BottomNavigationItem(
                   label = { Text(text = screen.title)},
                   icon = { Icon(imageVector = screen.icon, contentDescription =null , modifier = Modifier.size(20.dp))},
                   selected = currentDestination == screen.route,
                   selectedContentColor = Color.White,
                   unselectedContentColor = Color.Black,
                   onClick = {
                       navController.navigate(screen.route){
                           popUpTo(navController.graph.findStartDestination().id){
                               saveState=true
                           }
                           launchSingleTop=true
                           restoreState=true
                       }
                   },
                   alwaysShowLabel = false
               )
           }
       }
   }



@RequiresApi(Build.VERSION_CODES.M)
@Preview(showBackground = true, device = "id:Nexus 4")
@Composable
fun PreviBar(){
    HomeScreen(id = 1)
}