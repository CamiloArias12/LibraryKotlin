package com.example.library.routes

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.librariproject.data.user.UserStore
import com.example.librariproject.routes.authNavGraph
import com.example.librariproject.screens.home.HomeScreen


@RequiresApi(Build.VERSION_CODES.M)
@Composable
fun RootNaivigationGraph(navController:NavHostController){
    NavHost(
        navController = navController,
        route = Graph.ROOT,
        startDestination = Graph.AUTHENTICATION
    ){
        authNavGraph(navController = navController )
        composable(route= Graph.HOME +"/{id}", arguments = listOf(navArgument("id") { type = NavType.IntType })){ backStackEntry ->
            val id = backStackEntry.arguments?.getInt("id")
            if (id != null) {
                HomeScreen(id = id)
            }

        }

    }
}

object  Graph {
    const val ROOT= "root_graph"
    const val AUTHENTICATION= "auth_graph"
    const val  HOME = "home_graph"
    const val  ACCOUNT= "Update"
}