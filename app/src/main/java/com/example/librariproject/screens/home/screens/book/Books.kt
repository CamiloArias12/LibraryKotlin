package com.example.librariproject.screens.home.screens.book

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
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.apollographql.apollo3.api.ApolloResponse
import com.example.librariproject.GetAllBookQuery
import com.example.librariproject.apollo.apolloClient
import compose.icons.FontAwesomeIcons
import compose.icons.fontawesomeicons.Solid
import compose.icons.fontawesomeicons.solid.Edit
import compose.icons.fontawesomeicons.solid.Trash


@Composable
fun BooksLibrary(navController: NavHostController) {

    Box(
        modifier= Modifier.fillMaxSize()
            .background(Color(240,240,240))
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

    Card(
        backgroundColor = Color.White,
        shape = RoundedCornerShape(10.dp),
        elevation = Dp(2F),
        modifier = Modifier.padding(all = 16.dp)
    ) {
        Row(modifier = Modifier
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
            IconButton(onClick = { /*TODO*/ }) {
                Icon(imageVector = FontAwesomeIcons.Solid.Edit, contentDescription = null, modifier = Modifier.size(20.dp))
            }
            IconButton(onClick = { /*TODO*/ }) {
                Icon(imageVector = FontAwesomeIcons.Solid.Trash, contentDescription = null, modifier = Modifier.size(20.dp))
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun PrevieUser(){

}
