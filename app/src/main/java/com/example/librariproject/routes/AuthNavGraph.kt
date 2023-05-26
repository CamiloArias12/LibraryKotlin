package com.example.librariproject.routes

import android.util.Log
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.library.routes.Graph
import com.example.librariproject.screens.mainscreen.LoginScreen
import com.example.librariproject.screens.mainscreen.ScreenSplash
import com.example.librariproject.screens.mainscreen.SingIn


fun NavGraphBuilder.authNavGraph(navController: NavHostController, ){


    navigation(
        route = Graph.AUTHENTICATION,
        startDestination = AuthScreen.Splash.route
    ){
        composable(route = AuthScreen.Login.route){
            LoginScreen(navController = navController)
        }
        composable(route = AuthScreen.SignIn.route){
            SingIn(navController = navController)
        }
        composable(route = AuthScreen.Splash.route){
            ScreenSplash(navController = navController)
        }

    }
}


sealed class  AuthScreen(val route :String){
    object  Login : AuthScreen(route = "Login")
    object  SignIn : AuthScreen(route = "SingIn")
    object  Splash : AuthScreen(route = "Splash")

}