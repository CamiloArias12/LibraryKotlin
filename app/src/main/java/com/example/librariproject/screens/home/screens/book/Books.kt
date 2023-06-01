package com.example.librariproject.screens.home.screens.book

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import com.apollographql.apollo3.api.ApolloResponse
import com.example.librariproject.GetAllBookQuery
import com.example.librariproject.R
import com.example.librariproject.apollo.apolloClient
import com.example.librariproject.data.author.AuthorData
import com.example.librariproject.data.book.BookData
import com.example.librariproject.routes.BookCrud
import com.example.librariproject.screens.components.AlertWindow
import com.example.librariproject.screens.components.DeleteConfirmationScreen
import compose.icons.FontAwesomeIcons
import compose.icons.fontawesomeicons.Solid
import compose.icons.fontawesomeicons.solid.Edit
import compose.icons.fontawesomeicons.solid.Trash
import kotlinx.coroutines.launch


@Composable
fun BooksLibrary(navController: NavHostController) {

    Box(
        modifier= Modifier
            .fillMaxSize()
            .background(Color(240, 240, 240))
    ) {

        var response: ApolloResponse<GetAllBookQuery.Data>? by remember {
            mutableStateOf(null)
        }
        var listUser by remember {
            mutableStateOf(emptyList<GetAllBookQuery.GetAllBook>())
        }
        LaunchedEffect(Unit) {
            response = apolloClient.query(GetAllBookQuery()).execute()
            listUser = listUser + response?.data?.getAllBook.orEmpty()

        }

        LazyColumn() {
            items(listUser) { launch ->
                ListItemView(book = launch, navController = navController)
            }

        }
    }
}

@Composable
fun ListItemView(book: GetAllBookQuery.GetAllBook, navController: NavHostController) : Unit {
    val scope = rememberCoroutineScope()
    val deleteState= remember {
        mutableStateOf(false)
    }
    val deleteStateBook= remember {
        mutableStateOf(false)
    }
    val deleteMessage= "Desea eliminar el autor ?"

    Card(
        backgroundColor = Color.White,
        shape = RoundedCornerShape(10.dp),
        elevation = Dp(2F),
        modifier = Modifier.padding(all = 16.dp)
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
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(all = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = book.id.toString(),
                        textAlign = TextAlign.Start,
                    )
                    Text(
                        text = book.title,
                        textAlign = TextAlign.Start,
                    )
                    Text(
                        text = book.area,
                        textAlign = TextAlign.Start,
                    )
                }
                IconButton(onClick = {
                    navController.navigate(BookCrud.Update.route + "/${book.id.toInt()}")

                }) {
                    Icon(
                        imageVector = FontAwesomeIcons.Solid.Edit,
                        contentDescription = null,
                        modifier = Modifier.size(20.dp)
                    )
                }
                IconButton(onClick = { deleteState.value=true }) {
                    Icon(
                        imageVector = FontAwesomeIcons.Solid.Trash,
                        contentDescription = null,
                        modifier = Modifier.size(20.dp)
                    )
                }

                if (deleteState.value) {
                    DeleteConfirmationScreen(message = deleteMessage, onDeleteConfirmed = {
                        val bookData = BookData()
                        scope.launch {
                            val response = bookData.deleteBook(book.id.toInt())
                            if (!response) {
                                deleteStateBook.value = true
                            }
                        }
                        deleteState.value = false
                    },
                        onDismiss = { deleteState.value = false }

                    )
                }
                if(deleteStateBook.value){
                    AlertWindow(showDialog = deleteStateBook, text ="El libro no se puede eliminar" )
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun PrevieUser(){

}
