package com.vandan.tripsheep.presentations.screens

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import coil3.compose.AsyncImage
import com.exyte.animatednavbar.utils.lerp
import com.vandan.tripsheep.R
import com.vandan.tripsheep.data.local.HillStation
import com.vandan.tripsheep.data.local.TripPackage
import com.vandan.tripsheep.data.resource.TripPackageState
import com.vandan.tripsheep.navigations.Screens
import com.vandan.tripsheep.presentations.viewmodels.HomeScreenViewModel
import kotlin.math.absoluteValue

@Composable
fun HomeScreen(navHostController: NavHostController) {

    var searchText by remember { mutableStateOf("") }
    var bannerSize by remember {
        mutableIntStateOf(0)
    }

    val homeScreenViewModel = hiltViewModel<HomeScreenViewModel>()

    val hillStationsState = homeScreenViewModel.hillStationsState.collectAsStateWithLifecycle().value
    val tripPackageState = homeScreenViewModel.tripPackageState.collectAsStateWithLifecycle()

    LaunchedEffect(hillStationsState.hillStations.size) {
        bannerSize = hillStationsState.hillStations.size
    }

    val pagerState = rememberPagerState { bannerSize }

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
        ) {
            item {
                Spacer(modifier = Modifier.height(12.dp))
                SearchBox(
                    searchText = searchText,
                    placeholder = "What's in your mind today ?",
                    onValueChange = { searchText = it },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp)
                )
            }
//            Spacer(modifier = Modifier.height(12.dp))
//            HeaderText(text = "Explored Now", modifier = Modifier.padding(start = 8.dp))
//            Spacer(modifier = Modifier.height(12.dp))
//            StoriesComp()
            item {
                Spacer(modifier = Modifier.height(12.dp))
                HomeScreenHeaderText(text = "Hill Stations", modifier = Modifier.padding(start = 16.dp))
                Spacer(modifier = Modifier.height(12.dp))
                if (hillStationsState.isLoading){
                    CircularProgressIndicator()
                }
                else if(hillStationsState.error.isNotEmpty()){
                    Text(text = hillStationsState.error)
                }
                if(hillStationsState.hillStations.isNotEmpty()){
                    HillStations(pagerState = pagerState,hillStations = hillStationsState.hillStations)
                }
            }
            item {
                Spacer(modifier = Modifier.height(12.dp))
                DotsIndicatorHomeHillStation(pagerState = pagerState)
            }

            item {
                Spacer(modifier = Modifier.height(12.dp))
                HomeScreenHeaderText(text = "Top Trip Packages", modifier = Modifier.padding(start = 16.dp))
                Spacer(modifier = Modifier.height(12.dp))
                TopTripPlans(tripPackageState.value, navHostController = navHostController)
            }


        }
    }
}

@Composable
fun TopTripPlans(tripPackageState:TripPackageState,navHostController: NavHostController) {
    if(tripPackageState.isLoading){
        CircularProgressIndicator()
    }
    else if(tripPackageState.error != null){
        Text(text = tripPackageState.error.toString())
    }
    if(tripPackageState.tripPackages != null) {
        LazyRow {
            items(tripPackageState.tripPackages.size) { index ->
                Log.d("tripPackages",tripPackageState.tripPackages.size.toString())
                TopTripPlansItem(tripPackage = tripPackageState.tripPackages[index], navHostController = navHostController)
            }
        }
    }
}

@Composable
fun TopTripPlansItem(tripPackage:TripPackage,navHostController: NavHostController) {
    Card(
        Modifier
            .height(245.dp)
            .padding(8.dp)
            .width(265.dp)
    ){
        Box(
            modifier = Modifier.fillMaxSize()
                .clickable {
                    navHostController.navigate(Screens.TripPackageScreen.route+"/${tripPackage.tripPackageId}")
                }
        ){
            AsyncImage(
                model = tripPackage.imageUrls[1],
                contentDescription = "banner_image",
                contentScale = ContentScale.Crop,
                modifier=Modifier.fillMaxSize())
            PopularPlaceNameText(text = tripPackage.packageName)
        }
    }
}

@Composable
fun HomeScreenHeaderText(modifier: Modifier = Modifier, text: String = "") {
    Text(
        text = text,
        fontSize = 24.sp,
        modifier = modifier.fillMaxWidth(),
        color =  colorResource(R.color.sky_black),
        fontFamily = FontFamily(Font(R.font.poppins_black)),
        fontWeight = FontWeight.Bold,
        letterSpacing = TextUnit(1f, TextUnitType.Sp),
        textAlign = TextAlign.Center
    )
}

@Composable
fun SearchHeaderText(modifier: Modifier = Modifier, text: String = "") {
    Text(
        text = text,
        fontSize = 24.sp,
        modifier = modifier.fillMaxWidth(),
        color =  colorResource(R.color.sky_black),
        fontFamily = FontFamily(Font(R.font.poppins_medium)),
        fontWeight = FontWeight.Bold,
        letterSpacing = TextUnit(1f, TextUnitType.Sp)
    )
}

@Composable
fun HillStations(
    modifier: Modifier = Modifier,
    pagerState: PagerState,
    hillStations: List<HillStation>
) {
    HorizontalPager(state = pagerState, modifier = modifier.fillMaxWidth()) { page ->
        Box(
            Modifier
                .height(400.dp)
                .fillMaxWidth()
                .graphicsLayer {
                    val pageOffset = (
                            (pagerState.currentPage - page) + pagerState
                                .currentPageOffsetFraction
                            ).absoluteValue
                    alpha = lerp(
                        start = 0.5f,
                        stop = 1f,
                        fraction = 1f - pageOffset.coerceIn(0f, 1f)
                    )
                }
        ) {
            Card(modifier = Modifier
                .width(300.dp)
                .align(Alignment.Center)) {
                AsyncImage(
                    model = hillStations[page].images[0],
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize(),
                    contentDescription = "hill_stations"
                )
            }
        }
    }
}

@Composable
fun DotsIndicatorHomeHillStation(modifier: Modifier =Modifier,pagerState: PagerState) {
    Row(
        modifier
            .wrapContentHeight()
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
        repeat(pagerState.pageCount) { iteration ->
            val color = if (pagerState.currentPage == iteration) colorResource(id = R.color.sky_blue) else Color.LightGray
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