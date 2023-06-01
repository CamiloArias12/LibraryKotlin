package com.example.librariproject.screens.home.screens.book

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavHostController
import com.apollographql.apollo3.api.ApolloResponse
import com.example.librariproject.GetAllAuthorQuery
import com.example.librariproject.GetAllBookQuery
import com.example.librariproject.GetBookQuery
import com.example.librariproject.apollo.apolloClient
import com.example.librariproject.data.author.AuthorData
import com.example.librariproject.data.author.AuthorValue
import com.example.librariproject.data.book.BookData
import com.example.librariproject.data.book.BookValue
import com.example.librariproject.screens.components.AlertWindow
import com.example.librariproject.screens.components.BarTop
import com.example.librariproject.screens.components.TextFieldOwn
import kotlinx.coroutines.launch

@Composable

fun BookUpdate(id: Int, navController: NavHostController, isDrawerOpen: MutableState<Boolean>) {
    var dataBook by remember { mutableStateOf<BookValue?>(null) }
    val book = BookData()

    LaunchedEffect(Unit){
        isDrawerOpen.value=false
        val response: GetBookQuery.GetBook? = book.getBook(id=id)
        dataBook= BookValue(
            id= response?.id?.toInt()!!,
            title = response.title,
            summary =response.summary,
            area = response.area,
            publisher = response.publisher
        )

    }

    var response: ApolloResponse<GetAllAuthorQuery.Data>? by remember {
        mutableStateOf(null)
    }
    var listUser by remember {
        mutableStateOf(emptyList<GetAllAuthorQuery.GetAllAuthor>())
    }
    var items by remember {
        mutableStateOf(emptyList<ListItemData>())
    }

    val showDialog = remember { mutableStateOf(false) }
    val showAuthors = remember { mutableStateOf(false) }

    val scope = rememberCoroutineScope()
    val authors: MutableList<Int> = mutableListOf()

    val colorsText = TextFieldDefaults.outlinedTextFieldColors(
        focusedBorderColor = Color.Magenta,
        unfocusedBorderColor = Color.Black,
        focusedLabelColor = Color.Black,
        cursorColor = Color.Black,
        textColor = Color.Black,
    )
    val modifierText = Modifier
        .padding(2.dp)
        .size(width = 200.dp, height = 60.dp)
    val color = Color.Black

if (dataBook!=null) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(200, 230, 230))
    ) {
        Column {
            BarTop(
                navController = navController,
                isDrawerOpe = isDrawerOpen,
                title = "Actualizar libro"
            )
            Card(
                shape = RoundedCornerShape(
                    20.dp
                ),
                modifier = Modifier
                    .fillMaxSize()
                    .padding(40.dp),
                backgroundColor = Color.White,

                ) {

                Column(
                    modifier = Modifier.padding(horizontal = 30.dp, vertical = 30.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center

                ) {
                    val title =
                        remember { mutableStateOf(TextFieldValue(dataBook?.title.toString())) }
                    val publisher =
                        remember { mutableStateOf(TextFieldValue(dataBook?.publisher.toString())) }
                    val area =
                        remember { mutableStateOf(TextFieldValue(dataBook?.area.toString())) }
                    val summary =
                        remember { mutableStateOf(TextFieldValue(dataBook?.summary.toString())) }

                    TextFieldOwn(

                        valueData = title,
                        modifier = modifierText,
                        colorsText = colorsText,
                        color = color,
                        text = "Titulo",
                        keyboardType = KeyboardType.Text,
                        visualTransformation = VisualTransformation.None
                    )
                    TextFieldOwn(
                        valueData = publisher,
                        modifier = modifierText,
                        colorsText = colorsText,
                        color = color,
                        text = "Editor",
                        keyboardType = KeyboardType.Text,
                        visualTransformation = VisualTransformation.None
                    )
                    TextFieldOwn(
                        valueData = area,
                        modifier = modifierText,
                        colorsText = colorsText,
                        color = color,
                        text = "Area",
                        keyboardType = KeyboardType.Text,
                        visualTransformation = VisualTransformation.None
                    )
                    TextFieldOwn(
                        valueData = summary,
                        modifier = modifierText,
                        colorsText = colorsText,
                        color = color,
                        text = "Resumen",
                        keyboardType = KeyboardType.Number,
                        visualTransformation = VisualTransformation.None
                    )
                    Button(
                        onClick = {
                            showAuthors.value = true
                        },
                        colors = ButtonDefaults.buttonColors(
                            contentColor = Color.White,
                            backgroundColor = Color.Black
                        ),
                        modifier = Modifier
                            .padding(10.dp)

                    ) {
                        Text(text = "Autores")
                    }



                    Button(
                        onClick = {
                            val book = BookData()
                            scope.launch {

                                val data = book.createBook(
                                    title = title.value.text,
                                    publisher = publisher.value.text,
                                    area = area.value.text,
                                    summary = summary.value.text,
                                    authors = authors
                                )

                            }

                        },
                        colors = ButtonDefaults.buttonColors(
                            contentColor = Color.White,
                            backgroundColor = Color.Black
                        ),
                        modifier = Modifier
                            .padding(10.dp),

                        ) {
                        Text(text = "Aceptar")
                    }

                    if (showDialog.value) {
                        AlertWindow(
                            showDialog = showDialog,
                            text = "Usuario o correo ya existe"
                        )
                    }
                }

            }
        }

    }
}

    LaunchedEffect(Unit) {
        response = apolloClient.query(GetAllAuthorQuery()).execute()
        listUser = listUser + response?.data?.getAllAuthor.orEmpty()

        items = listUser.map {
            ListItemData(
                id = it.id.toInt(),
                title = it.name,
                isSelected = false
            )
        }
    }


    if (showAuthors.value) {
        Log.d("items", items.toString())
        Dialog(onDismissRequest = { showAuthors.value=false }) {

            Surface(
                modifier = Modifier
                    .size(300.dp, 400.dp)
                    .wrapContentHeight(),
                shape = RoundedCornerShape(size = 10.dp)
            ) {

                Column(modifier = Modifier.fillMaxSize()) {
                    Button(
                        onClick = {
                            showAuthors.value = false
                            items.forEach{item ->
                                if (item.isSelected){
                                    authors.add(item.id)
                                }
                            }
                            Log.d("Authors", authors.toString())
                        },
                        colors = ButtonDefaults.buttonColors(
                            contentColor = Color.White,
                            backgroundColor = Color.Black
                        ),
                        modifier = Modifier
                            .padding(10.dp)

                    ) {
                        Text(text = "Actualizar")
                    }
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                    ) {

                        items(items.size) { i ->
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable {
                                        items = items.mapIndexed { j, item ->
                                            if (i == j) {
                                                item.copy(isSelected = !item.isSelected)
                                            } else item
                                        }
                                    }
                                    .padding(16.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(text = items[i].title)
                                if (items[i].isSelected) {
                                    Icon(
                                        imageVector = Icons.Default.Check,
                                        contentDescription = "Selected",
                                        tint = Color.Red,
                                        modifier = Modifier.size(20.dp)
                                    )
                                }
                            }
                        }
                    }


                }
            }
        }

    }
}