package com.example.librariproject.screens.home.screens.author

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
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
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.apollographql.apollo3.api.ApolloResponse
import com.example.librariproject.GetAllAuthorQuery
import com.example.librariproject.R
import com.example.librariproject.apollo.apolloClient
import com.example.librariproject.data.author.AuthorData
import com.example.librariproject.data.author.AuthorValue
import com.example.librariproject.routes.AuthorCrud
import com.example.librariproject.screens.components.DeleteConfirmationScreen
import compose.icons.FontAwesomeIcons
import compose.icons.fontawesomeicons.Solid
import compose.icons.fontawesomeicons.solid.Edit
import compose.icons.fontawesomeicons.solid.Trash
import kotlinx.coroutines.launch


@Composable
fun AuthorsLibrary(navController: NavHostController) {
    Box(
        modifier= Modifier
            .fillMaxSize()
            .background(Color(240, 240, 240))
    ) {
        var response: ApolloResponse<GetAllAuthorQuery.Data>? by remember {
            mutableStateOf(null)
        }
        var listUser by remember {
            mutableStateOf(emptyList<GetAllAuthorQuery.GetAllAuthor>())
        }
        LaunchedEffect(Unit) {
            response = apolloClient.query(GetAllAuthorQuery()).execute()
            listUser = listUser + response?.data?.getAllAuthor.orEmpty()

        }

        LazyColumn() {
            items(listUser) { launch ->
                ListItemView(author = launch, navController = navController)
            }

        }
    }
}

@Composable
fun ListItemView(author: GetAllAuthorQuery.GetAllAuthor, navController: NavHostController) : Unit {
    val scope = rememberCoroutineScope()
    val deleteState= remember {
        mutableStateOf(false)
    }
    val deleteMessage= "Desea eliminar el autor ?"
    Card(
        backgroundColor = Color.White,
        elevation = Dp(2F),
        modifier = Modifier.padding( 10.dp)
    ) {
        Box(modifier = Modifier
            .fillMaxSize()
            .drawBehind {
                drawLine(
                    color = Color(6, 91, 57),
                    start = Offset(0f, 0f),
                    end = Offset(0f, size.height),
                    strokeWidth = 20f
                )
            }
        ) {
        Row(modifier = Modifier
            .padding(all = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier
                .weight(1f)
                .padding(10.dp)) {
                Text(
                    text = author.id.toInt().toString(),
                    textAlign = TextAlign.Start,
                )
                Text(
                    text = author.name,
                    textAlign = TextAlign.Start,
                )
                Text(
                    text = author.nationality,
                    textAlign = TextAlign.Start,
                )
            }
            IconButton(onClick = {
                navController.navigate(AuthorCrud.Update.route+ "/${author.id.toInt()}")
            }) {
                Icon(
                    imageVector = FontAwesomeIcons.Solid.Edit,
                    contentDescription = null,
                    modifier = Modifier.size(20.dp)
                )
            }
            IconButton(onClick = {
                deleteState.value=true

            }) {
                Icon(
                    imageVector = FontAwesomeIcons.Solid.Trash,
                    contentDescription = null,
                    modifier = Modifier.size(20.dp)
                )
            }

            if (deleteState.value){
               DeleteConfirmationScreen(message = deleteMessage, onDeleteConfirmed = { val auth = AuthorData()
                   scope.launch {
                       val response =auth.deleteAuthor(author.id.toInt())
                       Log.d("Delete",response.toString())
                       deleteState.value=false
                   } },
                   onDismiss = {deleteState.value=false}

               )
            }
        }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun PrevieUser(){
    AuthorsLibrary(navController = rememberNavController())
}
