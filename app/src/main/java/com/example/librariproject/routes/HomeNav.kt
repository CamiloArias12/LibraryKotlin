package com.example.librariproject.routes

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.librariproject.screens.home.screens.author.AuthorsLibrary
import com.example.librariproject.screens.home.screens.author.CreateAuthor
import com.example.librariproject.screens.home.screens.author.UpdateAuthor
import com.example.librariproject.screens.home.screens.book.BookUpdate
import com.example.librariproject.screens.home.screens.book.BooksLibrary
import com.example.librariproject.screens.home.screens.book.BooksLibraryStudent
import com.example.librariproject.screens.home.screens.book.CreateBook
import com.example.librariproject.screens.home.screens.loan.LoanCreate
import com.example.librariproject.screens.home.screens.loan.LoanLibrary
import com.example.librariproject.screens.home.screens.loan.LoanStudent
import com.example.librariproject.screens.home.screens.loan.LoanUpdate
import com.example.librariproject.screens.home.screens.user.AdminUpdate
import com.example.library.routes.ButtonBarScreen
import com.example.library.routes.Graph
import com.example.librariproject.screens.home.screens.user.UpdateUserScreen
import com.example.librariproject.screens.home.screens.user.UsersLibrary


@Composable
fun HomeNavGraph(
    navController: NavHostController,
    isDrawerOpe: MutableState<Boolean>,
    rol: MutableState<Boolean>,
    id: Int
){
    var startDestination=""

    startDestination = if (rol.value){
        ButtonBarScreen.BookStudent.route
    }else{
        ButtonBarScreen.User.route
    }
    NavHost(
        navController = navController,
        route = Graph.HOME,
        startDestination = startDestination
    ){
        composable(route= ButtonBarScreen.User.route){
                UsersLibrary(navController = navController)
        }
        composable(route= ButtonBarScreen.Author.route){
            AuthorsLibrary(navController = navController)
        }

        composable(route= ButtonBarScreen.Book.route){
            BooksLibrary(navController =navController)
        }
        composable(route= ButtonBarScreen.BookStudent.route){
            BooksLibraryStudent(navController =navController)
        }
        composable(route= ButtonBarScreen.LoanStudent.route){
                LoanStudent(navController = navController, id = id)
        }
        composable(route= ButtonBarScreen.Loan.route){
            LoanLibrary(navController =navController)
        }
        composable(route= UserCrud.Update.route + "/{id}",arguments = listOf(navArgument("id") { type = NavType.IntType })){ backStackEntry ->
            val id = backStackEntry.arguments?.getInt("id")
            if (id != null) {
                UpdateUserScreen(navController = navController, isDrawerOpe = isDrawerOpe, id = id)
            }
        }
        composable(route= UserCrud.UpdateAdmin.route + "/{id}",arguments = listOf(navArgument("id") { type = NavType.IntType })){ backStackEntry ->
            val id = backStackEntry.arguments?.getInt("id")
            if (id != null) {
                AdminUpdate(navController = navController, id = id, isDrawerOpe = isDrawerOpe)
            }
        }
        composable(route= AuthorCrud.Create.route){
            CreateAuthor(navController =navController, isDrawerOpen = isDrawerOpe)
        }
        composable(route= BookCrud.Create.route){
            CreateBook(navController =navController, isDrawerOpen = isDrawerOpe)
        }
        composable(route= AuthorCrud.Update.route + "/{id}",arguments = listOf(navArgument("id") { type = NavType.IntType })){ backStackEntry ->
            val id = backStackEntry.arguments?.getInt("id")
            if (id != null) {
                UpdateAuthor(navController = navController, id = id, isDrawerOpen =isDrawerOpe)
            }
        }

        composable(route= BookCrud.Update.route + "/{id}",arguments = listOf(navArgument("id") { type = NavType.IntType })){ backStackEntry ->
            val id = backStackEntry.arguments?.getInt("id")
            if (id != null) {
                BookUpdate(navController = navController, id = id, isDrawerOpen =isDrawerOpe)
            }
        }

        composable(route= LoanCrud.Update.route + "/{id}",arguments = listOf(navArgument("id") { type = NavType.IntType })){ backStackEntry ->
            val id = backStackEntry.arguments?.getInt("id")
            if (id != null) {
                LoanUpdate(navController = navController, id = id, isDrawerOpe =isDrawerOpe)
            }
        }
        composable(route= LoanCrud.Create.route + "/{id}",arguments = listOf(navArgument("id") { type = NavType.IntType })){ backStackEntry ->
            val id = backStackEntry.arguments?.getInt("id")
            if (id != null) {
                LoanCreate(navController = navController, id = id, isDrawerOpe =isDrawerOpe)
            }
        }
    }
}


sealed class  UserCrud(val route :String){
    object  Update : UserCrud(route = "Update")
    object  UpdateAdmin : UserCrud(route = "UpdateAdmin")
}

sealed class  AuthorCrud(val route :String){
    object  Update : UserCrud(route = "UpdateAuthor")
    object  Create : UserCrud(route = "CreateAuthor")
}
sealed class  BookCrud(val route :String){
    object  Update : BookCrud(route = "UpdateBook")
    object  Create : BookCrud(route = "CreateBook")
}
sealed class  LoanCrud(val route :String){
    object  Update : BookCrud(route = "UpdateLoan")
    object  Create : BookCrud(route = "CreateLoan")
}