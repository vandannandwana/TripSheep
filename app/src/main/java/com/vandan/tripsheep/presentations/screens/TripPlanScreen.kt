package com.vandan.tripsheep.presentations.screens

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChildCare
import androidx.compose.material.icons.filled.LocalOffer
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.SwipeRightAlt
import androidx.compose.material.icons.filled.WbSunny
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
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
import com.vandan.tripsheep.data.local.TripPlan
import com.vandan.tripsheep.presentations.viewmodels.SearchScreenViewModel


@Composable
fun TripPlanScreen(tripPlanId: String, navHostController: NavHostController) {

    val SearchScreenViewModel = hiltViewModel<SearchScreenViewModel>()

    val tripPlanState = SearchScreenViewModel.trips.collectAsStateWithLifecycle().value

    val tripPlan = tripPlanState.trips.find { it.trip_id == tripPlanId }

    Text(tripPlanId.toString())

    Scaffold {
        val ip = it

        BoxWithConstraints(modifier = Modifier.padding(ip)) {
            val maxHeight = maxHeight;
            val maxWidth = maxWidth
            if (tripPlan != null) {
                val itineraryList = tripPlan.itinerary.split(", ")
                val pagerState = rememberPagerState {
                    tripPlan.tripImages.size
                }
                LazyColumn {

                    item {

                        TripPlanBanner(
                            modifier = Modifier.height(maxHeight / 2.5f),
                            pagerState = pagerState,
                            tripPlan = tripPlan
                        )

                    }

                    item {
                        Spacer(Modifier.height(12.dp))
                        TripPlanDotsIndicator(pagerState = pagerState)

                    }

                    item {

                        Spacer(Modifier.height(12.dp))
                        HomeScreenHeaderText(text = tripPlan.name, modifier = Modifier.padding(start = 8.dp))

                    }

                    item{
                        Spacer(Modifier.height(4.dp))
                        Text(tripPlan.description)
                        Spacer(Modifier.padding(12.dp))
                    }

                    item{
                        Spacer(Modifier.height(12.dp))
                        HomeScreenHeaderText(text = "How Much It Will Cost", modifier = Modifier.padding(start = 8.dp))
                        Spacer(Modifier.padding(12.dp))
                    }

                    item {

                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(270.dp),
                            contentAlignment = Alignment.Center
                        ) {

                            val itemsList = listOf(
                                Items(
                                    icon = Icons.Default.ChildCare,
                                    name = "Kid",
                                    price = "₹ "+tripPlan.priceChild.toInt()+" "
                                ),
                                Items(
                                    icon = Icons.Default.Person,
                                    name = "Adult",
                                    price = "₹ "+tripPlan.priceAdult.toInt()+" "
                                ),
                                Items(
                                    icon = Icons.Default.LocalOffer,
                                    name = "Offer",
                                    price = "₹ "+tripPlan.priceOffer.toInt()+" "
                                ),
                                Items(
                                    icon = Icons.Default.WbSunny,
                                    name = "Days",
                                    price = tripPlan.days.toString()
                                )
                            )

                            GridItemBox(itemsList= itemsList)
                        }

                    }

                    item{
                        Spacer(Modifier.height(12.dp))
                        HomeScreenHeaderText(text = "Your Trip Schedule   ", modifier = Modifier.padding(start = 8.dp))
                        Spacer(Modifier.padding(12.dp))
                    }

                    item {
                        ItineraryView(itineraryList= itineraryList)
                    }

                }
            }
        }

    }

}

@Composable
fun TripPlanHeaderText(modifier: Modifier = Modifier, text: String) {
    Text(
        text = text,
        color = colorResource(R.color.mint_green),
        fontSize = 24.sp,
        modifier = modifier
            .fillMaxWidth()
            .padding(start = 4.dp),
        fontFamily = FontFamily(Font(R.font.montserrat_font)),
        fontWeight = FontWeight.Bold,
    )
}


@Composable
fun TripPlanBanner(modifier: Modifier = Modifier, pagerState: PagerState, tripPlan: TripPlan) {
    Box(modifier = modifier) {
        HorizontalPager(state = pagerState) {
            Log.d("TripImages", tripPlan.tripImages.toString())
            AsyncImage(
                model = tripPlan.tripImages[it],
                contentDescription = "banner_image",
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize(),
                placeholder = painterResource(R.drawable.tripsheepsplash)
            )
        }
    }
}

@Composable
fun TripPlanDotsIndicator(modifier: Modifier = Modifier, pagerState: PagerState) {
    Row(
        modifier
            .wrapContentHeight()
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
        repeat(pagerState.pageCount) { iteration ->
            val color =
                if (pagerState.currentPage == iteration) colorResource(id = R.color.mint_green) else Color.LightGray
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


data class Items(
    val icon: ImageVector,
    val name: String,
    val price: String
)

@Composable
fun GridItemBox(itemsList:List<Items>) {

    LazyVerticalGrid(
        columns = GridCells.Fixed(3),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        items(itemsList.size) {
            ItemBox(
                icon = itemsList[it].icon,
                name = itemsList[it].name,
                price = itemsList[it].price
            )
        }
    }

}

@Composable
fun ItemBox(icon: ImageVector, name: String, price: String) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Box(
            modifier = Modifier
                .wrapContentSize()
                .padding(8.dp)
                .clip(RoundedCornerShape(7.dp))
                .background(colorResource(R.color.mint_green).copy(alpha = 0.3f)),
            contentAlignment = Alignment.Center
        ) {
            Column(
                modifier = Modifier
                    .size(80.dp)
                    .padding(8.dp)
                    .clip(CircleShape)
                    .background(colorResource(R.color.mint_green).copy(alpha = 0.6f)),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Bottom
            ) {
                Icon(
                    imageVector = icon,
                    modifier = Modifier
                        .size(30.dp),
                    contentDescription = name
                )
                Text(name, fontSize = 10.sp)
            }
        }

        Text("$price", modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.Center)

    }

}

@Composable
fun ItineraryView(itineraryList:List<String>) {

    LazyRow {
        items(itineraryList.size) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically

            ) {
                Column(
                    modifier = Modifier
                        .size(120.dp)
                        .padding(12.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(colorResource(R.color.mint_green).copy(alpha = 0.6f)),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    val p1 = itineraryList[it].split(": ")
                    Text(p1[0], textAlign = TextAlign.Center)
                    Text(p1[1], textAlign = TextAlign.Center)
                }
                Spacer(modifier = Modifier.width(12.dp))
                Icon(imageVector = Icons.Default.SwipeRightAlt, contentDescription = "")
                Spacer(modifier = Modifier.width(12.dp))

            }
        }
        item {
            Column(
                modifier = Modifier
                    .size(120.dp)
                    .padding(12.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background( colorResource(R.color.mint_green).copy(alpha = 0.7f)),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    color = colorResource(R.color.white),
                    text = "Home",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )
            }
        }
    }

}


