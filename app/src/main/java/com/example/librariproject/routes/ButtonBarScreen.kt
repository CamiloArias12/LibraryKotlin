package com.example.library.routes

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.outlined.Email
import androidx.compose.ui.graphics.vector.ImageVector
import compose.icons.AllIcons
import compose.icons.FontAwesomeIcons
import compose.icons.fontawesomeicons.Brands
import compose.icons.fontawesomeicons.Solid
import compose.icons.fontawesomeicons.brands.Facebook
import compose.icons.fontawesomeicons.brands.Java
import compose.icons.fontawesomeicons.solid.BookOpen
import compose.icons.fontawesomeicons.solid.UserAlt
import compose.icons.fontawesomeicons.solid.UserFriends
import compose.icons.fontawesomeicons.solid.Users

sealed class ButtonBarScreen (
            val route: String,
            val title:String,
            val icon: ImageVector
        ){


        object Book: ButtonBarScreen(
            route = "book",
            title = "Libros",
            icon = FontAwesomeIcons.Solid.BookOpen
        )

        object Loan: ButtonBarScreen(
            route = "loan",
            title = "Prestamos",
            icon = FontAwesomeIcons.Brands.Facebook
        )
        object User: ButtonBarScreen(
            route = "user",
            title = "Usuraios",
            icon = FontAwesomeIcons.Solid.UserAlt
        )
        object Author: ButtonBarScreen(
            route = "author",
            title = "Autores",
            icon = FontAwesomeIcons.Solid.Users
        )
}