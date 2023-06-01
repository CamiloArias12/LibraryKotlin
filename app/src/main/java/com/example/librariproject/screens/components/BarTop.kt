package com.example.librariproject.screens.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import compose.icons.FontAwesomeIcons
import compose.icons.fontawesomeicons.Solid
import compose.icons.fontawesomeicons.solid.ArrowLeft

@Composable
fun BarTop(navController:NavHostController,isDrawerOpe:MutableState<Boolean>,title:String){
    TopAppBar(
        backgroundColor = Color(2, 101, 157),
        ) {
        IconButton(onClick = {
            isDrawerOpe.value = true
            navController.popBackStack()
        }) {
            Icon(
                imageVector = FontAwesomeIcons.Solid.ArrowLeft,
                contentDescription = null,
                modifier = Modifier.size(20.dp),
                 tint = Color.White
            )

        }
        Column (
            modifier=Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ){
            Text(text = title, style = TextStyle( color = Color.White , fontSize = 20.sp))
        }
    }

}

@Preview(showBackground = true)
@Composable
fun PreviwBar(){
    val isDrawerOpe= remember {
        mutableStateOf(false)
    }
    BarTop(navController = rememberNavController(), isDrawerOpe =isDrawerOpe, title = "asfsadfsad")
}