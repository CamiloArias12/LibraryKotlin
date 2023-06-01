package com.example.librariproject.screens.home.screens.loan

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
import androidx.compose.material.OutlinedTextField
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavHostController
import com.apollographql.apollo3.api.ApolloResponse
import com.example.librariproject.GetAllBookQuery
import com.example.librariproject.GetUserQuery
import com.example.librariproject.LoanQuery
import com.example.librariproject.apollo.apolloClient
import com.example.librariproject.data.book.BookData
import com.example.librariproject.data.loan.LoanData
import com.example.librariproject.data.loan.LoanValue
import com.example.librariproject.data.user.UserData
import com.example.librariproject.data.user.UserValue
import com.example.librariproject.screens.components.BarTop
import com.example.librariproject.screens.components.BoxItem
import com.example.librariproject.screens.components.CalendarWithDateSelection
import com.example.librariproject.screens.home.screens.book.ListItemData
import kotlinx.coroutines.launch
import java.time.LocalDate




@Composable

fun LoanUpdate(id: Int, navController: NavHostController ,isDrawerOpe: MutableState<Boolean>) {
    var dataLoan by remember { mutableStateOf<LoanValue?>(null) }
    val loan = LoanData()
    val scope = rememberCoroutineScope()
    LaunchedEffect(Unit) {

        isDrawerOpe.value=false
        var data: LoanQuery.Loan? = loan.getLoan(id = id)
        dataLoan = LoanValue(
            id = data?.id?.toInt()!!,
            returned = data.returned,
            startDate = data.startDate.toString().substring(0,10),
            returnedDate = data.returnDate.toString().substring(0,10)
        )
    }
    
    if (dataLoan!=null) {
        Log.d("Data uloan", dataLoan.toString())
        val pickedDateStart = remember { mutableStateOf(LocalDate.parse(dataLoan?.startDate)) }
        val pickedDateStartFlag = remember { mutableStateOf(false) }
        val pickedDateEnd = remember { mutableStateOf(LocalDate.parse(dataLoan?.returnedDate)) }
        val pickedDateEndFlag = remember { mutableStateOf(false) }
        var bookId = 0
        val showBook = remember { mutableStateOf(false) }
        var book by remember {
            mutableStateOf(emptyList<ListItemData>())
        }
        Column {

            BarTop(
                navController = navController,
                isDrawerOpe = isDrawerOpe,
                title = "Actualizar prestamo"
            )
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color(200, 230, 230))
            ) {

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

                        OutlinedTextField(
                            label = { Text(text = "Fecha inicio", color = Color.Black) },
                            value = pickedDateStart.value.toString(),
                            readOnly = true,
                            onValueChange = { pickedDateStart.value = LocalDate.parse(it) },
                            modifier = Modifier
                                .padding(20.dp)
                                .clickable {
                                    Log.d("Calendar", "asdfsdfsdfsd")
                                    pickedDateStartFlag.value = true
                                },
                            colors = TextFieldDefaults.outlinedTextFieldColors(
                                focusedBorderColor = Color.Black,
                                unfocusedBorderColor = Color.Black,
                                focusedLabelColor = Color.Black,
                                cursorColor = Color.Black,
                                textColor = Color.Black
                            ),
                            textStyle = TextStyle(fontSize = 15.sp)

                        )
                        OutlinedTextField(
                            label = { Text(text = "Fecha entrega", color = Color.Black) },
                            value = pickedDateEnd.value.toString(),
                            readOnly = true,
                            onValueChange = { pickedDateEnd.value = LocalDate.parse(it) },
                            modifier = Modifier
                                .padding(10.dp)
                                .clickable {
                                    Log.d("Calendar", "asdfsdfsdfsd")
                                    pickedDateEndFlag.value = true
                                },
                            colors = TextFieldDefaults.outlinedTextFieldColors(
                                focusedBorderColor = Color.Black,
                                unfocusedBorderColor = Color.Black,
                                focusedLabelColor = Color.Black,
                                cursorColor = Color.Black,
                                textColor = Color.Black
                            ),
                            textStyle = TextStyle(fontSize = 15.sp)

                        )

                        val listActive = listOf("SI", "NO")
                        val isActive = remember {
                            mutableStateOf("")
                        }

                        if (dataLoan!!.returned) {
                            isActive.value = listActive[0]
                        } else {
                            isActive.value = listActive[1]
                        }

                        Text(text = "Devuelto")
                        BoxItem(option = isActive, items = listActive)

                        Button(
                            onClick = {
                                showBook.value = true
                            },
                            colors = ButtonDefaults.buttonColors(
                                contentColor = Color.White,
                                backgroundColor = Color.Black
                            ),
                            modifier = Modifier
                                .padding(10.dp)

                        ) {
                            Text(text = "Libro")
                        }

                        Button(
                            onClick = {
                                book.forEach { book ->
                                    if (book.isSelected) {
                                        bookId = book.id

                                    }
                                }
                                Log.d("Book", bookId.toString())
                                Log.d("Book", bookId.toString())
                                scope.launch {
                                    val loan = LoanData()
                                    dataLoan!!.returned = isActive.value == listActive[0]
                                    Log.d("Date", pickedDateEnd.toString())
                                    val response = loan.updateLoan(
                                        id = id,
                                        bookId = bookId,
                                        returned = dataLoan!!.returned,
                                        returnDate = pickedDateStart.value.toString(),
                                        startDate = pickedDateStart.value.toString()
                                    )

                                    if (response != null) {

                                    }

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

                        if (pickedDateStartFlag.value) {
                            CalendarWithDateSelection(
                                pickedDateStart = pickedDateStart,
                                pickedDateStartFlag = pickedDateStartFlag
                            )
                        }
                        if (pickedDateEndFlag.value) {
                            CalendarWithDateSelection(
                                pickedDateStart = pickedDateEnd,
                                pickedDateStartFlag = pickedDateEndFlag
                            )
                        }
                    }

                }
            }
            var response: ApolloResponse<GetAllBookQuery.Data>? by remember {
                mutableStateOf(null)
            }
            var listbook by remember {
                mutableStateOf(emptyList<GetAllBookQuery.GetAllBook>())
            }

            LaunchedEffect(Unit) {
                response = apolloClient.query(GetAllBookQuery()).execute()
                listbook = listbook + response?.data?.getAllBook.orEmpty()

                book = listbook.map {
                    ListItemData(
                        id = it.id.toInt(),
                        title = it.title,
                        isSelected = false
                    )
                }
            }

            if (showBook.value) {
                Dialog(onDismissRequest = { showBook.value = false }) {

                    Surface(
                        modifier = Modifier
                            .size(300.dp, 400.dp)
                            .wrapContentHeight(),
                        shape = RoundedCornerShape(size = 10.dp)
                    ) {

                        Column(modifier = Modifier.fillMaxSize()) {
                            Button(
                                onClick = {
                                    showBook.value = false
                                },
                                colors = ButtonDefaults.buttonColors(
                                    contentColor = Color.White,
                                    backgroundColor = Color.Black
                                ),
                                modifier = Modifier
                                    .padding(10.dp)

                            ) {
                                Text(text = "Agregar")
                            }
                            LazyColumn(
                                modifier = Modifier
                                    .fillMaxSize()
                            ) {

                                items(book.size) { i ->
                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .clickable {
                                                book = book.mapIndexed { j, item ->
                                                    item.copy(isSelected = i == j)
                                                }
                                            }
                                            .padding(16.dp),
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Text(text = book[i].title)
                                        if (book[i].isSelected) {
                                            Icon(
                                                imageVector = Icons.Default.Check,
                                                contentDescription = "Selected",
                                                tint = Color.Red,
                                                modifier = Modifier
                                                    .size(20.dp)
                                                    .padding(5.dp)
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
    }
}



