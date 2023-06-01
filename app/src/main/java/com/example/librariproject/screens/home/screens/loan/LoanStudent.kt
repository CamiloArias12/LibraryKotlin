package com.example.librariproject.screens.home.screens.loan


import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import com.example.librariproject.GetAllLoanQuery
import com.example.librariproject.GetAllLoanUserQuery
import com.example.librariproject.R
import com.example.librariproject.apollo.apolloClient
import com.example.librariproject.data.author.AuthorData
import com.example.librariproject.data.loan.LoanData
import com.example.librariproject.routes.LoanCrud
import com.example.librariproject.routes.UserCrud
import com.example.librariproject.screens.components.DeleteConfirmationScreen
import compose.icons.FontAwesomeIcons
import compose.icons.fontawesomeicons.Solid
import compose.icons.fontawesomeicons.solid.Edit
import compose.icons.fontawesomeicons.solid.Trash
import kotlinx.coroutines.launch


@Composable
fun LoanStudent(navController: NavHostController, id:Int) {


    Box(
        modifier= Modifier
            .fillMaxSize()
            .background(Color(240, 240, 240))
    ) {

        var response: ApolloResponse<GetAllLoanUserQuery.Data>? by remember {
            mutableStateOf(null)
        }
        var listLoan by remember {
            mutableStateOf(emptyList<GetAllLoanUserQuery.GetAllLoanUser>())
        }
        LaunchedEffect(Unit) {
            response = apolloClient.query(GetAllLoanUserQuery(getAllLoanUserId = id.toDouble())).execute()
            listLoan = listLoan + response?.data?.getAllLoanUser.orEmpty()

        }

        LazyColumn() {
            Log.d("Loan",listLoan.toString())
            items(listLoan) { launch ->
                ListItemViewStudent(loan = launch, navController = navController)
            }

        }
    }
}

@Composable
fun ListItemViewStudent(loan: GetAllLoanUserQuery.GetAllLoanUser, navController: NavHostController) : Unit {
    val scope = rememberCoroutineScope()
    val deleteState= remember {
        mutableStateOf(false)
    }
    val deleteMessage= "Desea eliminar el prestamo ?"

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
                        text = loan.id.toInt().toString(),
                        textAlign = TextAlign.Start,
                    )

                    Text(

                        text = loan.startDate.toString().substring(0,10),

                        textAlign = TextAlign.Start,
                    )
                    Text(
                        text = loan.returnDate.toString().substring(0,10),
                        textAlign = TextAlign.Start,
                    )

                    Text(
                        text = loan.returned.toString(),
                        textAlign = TextAlign.Start,
                    )
                }
                IconButton(onClick = {

                    navController.navigate(LoanCrud.Update.route + "/${loan.id.toInt()}")
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
                if (deleteState.value){
                    DeleteConfirmationScreen(message = deleteMessage, onDeleteConfirmed = {
                        val loanData = LoanData()
                        scope.launch {
                            val response =loanData.deleteLoan(loan.id.toInt())
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


