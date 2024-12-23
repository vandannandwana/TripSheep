package com.vandan.tripsheep.presentations.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.vandan.tripsheep.R
import com.vandan.tripsheep.navigations.Screens

@Composable
fun SplashScreen(navHostController: NavHostController) {

    BoxWithConstraints {
        val height = maxHeight
        val width = maxWidth
        Image(
            painter = painterResource(id = R.drawable.tripsheepsplash),
            contentDescription = "splash_screen",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )
        Column(
            modifier = Modifier
                .align(Alignment.Center)
                .padding(bottom = height / 2),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.tripsheeplogo),
                contentScale = ContentScale.Crop,
                contentDescription = "logo"
            )
            Row(horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment.Bottom){
                Text(
                    text = "Trip",
                    color = colorResource(id = R.color.mint_green),
                    fontWeight = FontWeight.Bold,
                    fontSize = 45.sp
                )
                Text(
                    text = "Sheep",
                    color = colorResource(id = R.color.sunset_orange),
                    fontWeight = FontWeight.SemiBold
                )
            }
        }


        Column(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = height / 12),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            GetStartedButton(
                modifier = Modifier,
                textModifier = Modifier.padding(horizontal = width / 12, vertical = height / 100)
            ) {
//                navHostController.popBackStack()
                navHostController.navigate(Screens.GetStartedScreen.route)
            }
            Text(text = "Made with ❤️ by Vandan", color = colorResource(id = R.color.light_gray))
        }


    }

}

@Composable
fun GetStartedButton(modifier: Modifier, textModifier: Modifier, onClick: () -> Unit = {}) {
    Button(
        modifier = modifier,
        onClick = { onClick() },
        elevation = ButtonDefaults.buttonElevation(24.dp),
        shape = CircleShape,
        colors = ButtonDefaults.buttonColors(
            containerColor = colorResource(id = R.color.mint_green).copy(alpha = 0.8f),
            contentColor = colorResource(id = R.color.black)
        )
    ) {
        Text(
            text = "Make Your First Trip Memorial",
            modifier = textModifier,
            color = Color.DarkGray,
            textAlign = TextAlign.Start
        )
    }
}