package com.example.librariproject.screens.mainscreen
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.librariproject.R
import com.example.librariproject.routes.AuthScreen
import kotlinx.coroutines.delay

@Composable
fun ScreenSplash( navController: NavController){

    LaunchedEffect(key1 =true){
        delay(3000)
        navController.popBackStack()
        navController.navigate(AuthScreen.Login.route)
    }
    Splash()
}
@Composable
fun Splash( ){
    Surface (modifier = Modifier.fillMaxSize()){
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(painter = painterResource(id = R.drawable.logo ) , contentDescription =null,
                modifier = Modifier.size(300.dp) )
            Text(
                text = "Biblioteca",
                fontSize = 40.sp ,
                modifier=Modifier.padding(50.dp),
                fontFamily = FontFamily.Cursive,
                color = Color.Black


            )
        }
    }
}


@Preview
@Composable
fun ViewSplash(){
    Splash()

}