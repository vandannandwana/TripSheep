package com.vandan.tripsheep.presentations.screens

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import coil3.compose.AsyncImage
import com.vandan.tripsheep.R
import com.vandan.tripsheep.data.local.Hotel
import com.vandan.tripsheep.data.local.TripPackage
import com.vandan.tripsheep.navigations.Screens
import com.vandan.tripsheep.presentations.viewmodels.HomeScreenViewModel

@Composable
fun TripPackageScreen(
    modifier: Modifier = Modifier,
    tripPackageId: String,
    navhostController: NavHostController
) {
    Log.d("tripPackageId", tripPackageId)
    val HomeScreenViewModel = hiltViewModel<HomeScreenViewModel>()
    val tripPackageState = HomeScreenViewModel.tripPackageState.collectAsStateWithLifecycle()

    Scaffold { ip ->
        val innerpadding = ip
        BoxWithConstraints(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerpadding),
            contentAlignment = Alignment.TopCenter
        ) {
            val maxHeight = maxHeight
            val maxWidth = maxWidth
            val trip =
                tripPackageState.value.tripPackages?.find { it.tripPackageId == tripPackageId }
            if (trip != null) {
                val pagerState = rememberPagerState {
                    trip.imageUrls.size
                }
                Text(text = trip.packageName)

                LazyColumn {

                    item {
                        Banner(
                            modifier = Modifier.height(maxHeight/2.5f),
                            pagerState = pagerState,
                            tripPackage = trip
                        )
                        Spacer(Modifier.height(12.dp))
                    }
                    item {
                        DotsIndicator(pagerState = pagerState);
                        Spacer(Modifier.height(12.dp))
                    }

                    item {

                        HeaderText(text = trip.packageName)
                        Spacer(Modifier.height(12.dp))
                    }
                    item {
                        Text(
                            text = "About: ${trip.about}",
                            textAlign = TextAlign.Justify,
                            modifier = Modifier.padding(8.dp)
                        )
                        Spacer(Modifier.height(12.dp))
                    }

                    item {

                        HeaderText(text = "Hotels To Stay")
                        Spacer(Modifier.height(12.dp))

                    }


                    item {
                        HotelList(trip, navHostController = navhostController)
                        Spacer(Modifier.height(12.dp))

                    }

                }


            }

        }

    }

}

@Composable
fun Banner(modifier: Modifier = Modifier, pagerState: PagerState, tripPackage: TripPackage) {
    Box(modifier = modifier) {
        HorizontalPager(state = pagerState) {
            Log.d("TripImages", tripPackage.imageUrls.toString())
            AsyncImage(
                model = tripPackage.imageUrls[it],
                contentDescription = "banner_image",
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize(),
                placeholder = painterResource(R.drawable.tripsheepsplash)
            )
        }
    }
}

@Composable
fun DotsIndicator(modifier: Modifier =Modifier,pagerState: PagerState) {
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

@Composable
fun HeaderText(modifier: Modifier = Modifier, text: String = "") {
    Text(
        text = text,
        fontSize = 24.sp,
        modifier = modifier.fillMaxWidth().padding(start = 4.dp),
        fontFamily = FontFamily(Font(R.font.montserrat_font)),
        fontWeight = FontWeight.Bold,
    )
}

@Composable
fun HotelList(tripPackage: TripPackage, navHostController: NavHostController) {
        LazyRow {
            items(tripPackage.hotels.size) { index ->
                HotelItem(hotel = tripPackage.hotels[index], navHostController = navHostController)
            }
        }
}


@Composable
fun HotelItem(hotel:Hotel,navHostController: NavHostController) {
    Card(
        Modifier
            .height(245.dp)
            .padding(8.dp)
            .width(265.dp)
    ){
        Box(
            modifier = Modifier.fillMaxSize()
                .clickable {
                    navHostController.navigate(Screens.HotelScreen.route+"/${hotel._id}")
                }
        ){
            AsyncImage(
                model = hotel.imageUrls[0],
                contentDescription = "banner_image",
                contentScale = ContentScale.Crop,
                modifier=Modifier.fillMaxSize(),
                placeholder = painterResource(R.drawable.tripsheepsplash)
            )
            PopularPlaceNameText(text = hotel.hotelName)
        }
    }
}