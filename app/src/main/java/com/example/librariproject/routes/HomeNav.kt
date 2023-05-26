package com.example.librariproject.routes

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.librariproject.screens.home.screens.Author.AuthorsLibrary
import com.example.librariproject.screens.home.screens.book.BooksLibrary
import com.example.library.routes.ButtonBarScreen
import com.example.library.routes.Graph
import com.example.librariproject.screens.home.screens.user.UpdateUserScreen
import com.example.librariproject.screens.home.screens.user.UsersLibrary


@Composable
fun HomeNavGraph(
    navController: NavHostController,
    isDrawerOpe:MutableState<Boolean> ){
    NavHost(
        navController = navController,
        route = Graph.HOME,
        startDestination = ButtonBarScreen.User.route
    ){
        composable(route= ButtonBarScreen.User.route){
            UsersLibrary(navController=navController)
        }
        composable(route= ButtonBarScreen.Author.route){
            AuthorsLibrary(navController = navController)
        }

        composable(route= ButtonBarScreen.Book.route){
            BooksLibrary(navController =navController)
        }
        composable(route= UserCrud.Update.route + "/{id}",arguments = listOf(navArgument("id") { type = NavType.IntType })){ backStackEntry ->
            val id = backStackEntry.arguments?.getInt("id")
            if (id != null) {
                UpdateUserScreen(navController = navController, isDrawerOpe = isDrawerOpe, id = id)
            }
        }
        Log.d("Estado",isDrawerOpe.toString())
    }
}


sealed class  UserCrud(val route :String){
    object  Update : UserCrud(route = "Update")
    object  Query : AuthScreen(route = "Query")
}