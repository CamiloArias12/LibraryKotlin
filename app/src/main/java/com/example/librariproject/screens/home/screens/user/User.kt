package com.example.librariproject.screens.home.screens.user

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
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Surface
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.apollographql.apollo3.api.ApolloResponse
import com.example.librariproject.GetUserAllQuery
import com.example.librariproject.R
import com.example.librariproject.apollo.apolloClient
import compose.icons.FontAwesomeIcons
import compose.icons.fontawesomeicons.Solid
import compose.icons.fontawesomeicons.solid.Edit


@Composable
fun UsersLibrary(navController: NavHostController) {

    Box(
       modifier= Modifier
           .fillMaxSize()
           .background(Color(240, 240, 240))
    ) {

        var response: ApolloResponse<GetUserAllQuery.Data>? by remember {
            mutableStateOf(null)
        }
        var listUser by remember {
            mutableStateOf(emptyList<GetUserAllQuery.UsersAll>())
        }
        LaunchedEffect(Unit) {
            response = apolloClient.query(GetUserAllQuery()).execute()
            listUser = listUser + response?.data?.usersAll.orEmpty()

        }

        LazyColumn() {
            items(listUser) { launch ->
                ListItemView(user = launch, navController = navController)
            }

        }
    }
}


@Composable
fun ListItemView(user: GetUserAllQuery.UsersAll, navController: NavHostController) : Unit {

    Card(
        backgroundColor = Color.White,
        elevation = Dp(2F),
        shape = RoundedCornerShape(10.dp),
        modifier = Modifier.padding(all = 16.dp)
    ) {
        Row(modifier = Modifier
            .fillMaxWidth()
            .padding(all = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = R.drawable.profile) ,
                contentDescription =null ,
                modifier = Modifier.size(40.dp)
                )
            Spacer(modifier = Modifier.padding(horizontal = 10.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = user.firstName,
                    textAlign = TextAlign.Start,
                )
                Text(
                    text = user.email,
                    textAlign = TextAlign.Start,
                )
                Text(
                    text = user.phone,
                    textAlign = TextAlign.Start,
                )
                Text(
                    text = user.address,
                    textAlign = TextAlign.Start,
                )
            }
            IconButton(onClick = { /*TODO*/ }) {
                Icon(imageVector = FontAwesomeIcons.Solid.Edit, contentDescription = null, modifier = Modifier.size(20.dp))
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun PrevieUser(){
    UsersLibrary(navController = rememberNavController())
}