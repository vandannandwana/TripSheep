package com.vandan.tripsheep.presentations.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil3.compose.AsyncImage
import com.vandan.tripsheep.R
import com.vandan.tripsheep.navigations.Screens
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


@Composable
fun GetStartedScreen(navHostController: NavHostController) {

    Scaffold {
        val ip = it
        BoxWithConstraints(modifier = Modifier
            .fillMaxSize()
            .padding(ip), contentAlignment = Alignment.TopCenter){
            val maxHeight = maxHeight
            val maxWidth = maxWidth
            val pagerState = rememberPagerState { banners.size }
            var textIndex by rememberSaveable {
                mutableIntStateOf(0)
            }
            LaunchedEffect(key1 = pagerState.currentPage) {
                CoroutineScope(Dispatchers.Main).launch {
                    textIndex = pagerState.currentPage
                }

            }
            LazyColumn(modifier = Modifier
                .fillMaxSize()
                .padding(top = maxHeight / 8), horizontalAlignment = Alignment.CenterHorizontally){
                item {
                    GetStartedImages(
                        pagerState=pagerState,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(maxHeight / 3)
                            .padding(horizontal = maxWidth / 8)
                    )
                }
                item {
                    DotsIndicatorGetStarted(pagerState = pagerState)
                }
                item {
                    GetStartedTexts(pagerState = textIndex)
                }
                item { Spacer(modifier = Modifier.height(maxHeight/4)) }
                item {
                    LoginSignupButton(modifier = Modifier.padding(horizontal = maxWidth/24),text = "Login", color = colorResource(id = R.color.mint_green), textColor = colorResource(R.color.light_gray)){
                        navHostController.navigate(Screens.LoginScreen.route)
                    }
                }
                item { Spacer(modifier = Modifier.height(maxHeight/48)) }
                item {
                    LoginSignupButton(modifier = Modifier.padding(horizontal = maxWidth/24),text="Sign Up", color = colorResource(R.color.light_gray), textColor = colorResource(R.color.mint_green)){
                        navHostController.navigate(Screens.SignUpScreen.route)
                    }
                }
            }

        }


    }

}

@Composable
fun LoginSignupButton(modifier: Modifier = Modifier,text: String= "Button Text",color: Color,textColor: Color,onClick:()->Unit={}) {

    Button(
        modifier=modifier,
        colors = ButtonDefaults.buttonColors(
            containerColor = color
        ),
        elevation = ButtonDefaults.buttonElevation(
            defaultElevation = 8.dp
        )
        ,onClick = { onClick()}
    ) {

        Text(
            text = text,
            textAlign = TextAlign.Center,
            color = textColor,
            modifier= Modifier
                .fillMaxWidth()
                .padding(vertical = 12.dp))

    }

}

@Composable
fun GetStartedTexts(pagerState: Int) {

    Text(
        text = splashImages[pagerState].text,
        modifier = Modifier.fillMaxWidth(),
        textAlign = TextAlign.Center
    )

}

data class SplashImage(
    val image: Int,
    val text: String = ""
)

val splashImages = listOf(
    SplashImage(R.drawable.splash1,"Have A Fun With Whole Family"),
    SplashImage(R.drawable.splash2,"Book Your Own Vehicles"),
    SplashImage(R.drawable.splash3,"All Time Support"),
)

@Composable
fun GetStartedImages(modifier: Modifier = Modifier,pagerState: PagerState) {
    HorizontalPager(state = pagerState, modifier = modifier
        .fillMaxWidth()
        .background(Color.Transparent)) { page ->
                AsyncImage(
                    model = splashImages[page].image,
                    contentScale = ContentScale.Fit,
                    modifier = Modifier.fillMaxSize(),
                    contentDescription = "hill_stations"
                )
    }
}


@Composable
fun DotsIndicatorGetStarted(modifier: Modifier =Modifier,pagerState: PagerState) {
    Row(
        modifier
            .wrapContentHeight()
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
        repeat(pagerState.pageCount) { iteration ->
            val color = if (pagerState.currentPage == iteration) colorResource(id = R.color.mint_green) else Color.LightGray
            Box(
                modifier = Modifier
                    .padding(horizontal = 2.dp)
                    .width(25.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(color)
                    .size(8.dp)
            )
        }
    }
}