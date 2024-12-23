package com.vandan.tripsheep

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.compose.rememberNavController
import com.vandan.tripsheep.navigations.Navigation
import com.vandan.tripsheep.ui.theme.TripSheepTheme
import com.vandan.tripsheep.utils.Constants.Companion.USER_PREFERENCE
import dagger.hilt.android.AndroidEntryPoint
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TripSheepTheme {
                val navHostController = rememberNavController()
                val userPreferences = getSharedPreferences(USER_PREFERENCE, MODE_PRIVATE)
                val token = userPreferences.getString("token", null)
                token.let {
                    Navigation(navHostController = navHostController, token = token)
                }



//                TripPlanScreenPreview()

//                val service = Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build().create(TripService::class.java)
//                LaunchedEffect(Unit) {
//                    val response = service.getUser(LoginUserModel("nandviky4@gmail.com", ""))
//                    if (response != null) {
//                        val user = response
//                        Log.d("Vandanji", user.toString() ?: "User not found")
//                    } else {
//                        Log.e("Vandanji", "Error:")
//                    }
//                }


            }
        }
    }
}
//
//@Preview(showSystemUi = true)
//@Composable
//private fun TripPlanScreenPreview() {
//    val itemsList = listOf(
//        Items(
//            icon = Icons.Default.ChildCare,
//            name = "Kid",
//            price = "₹ 11,200"
//        ),
//        Items(
//            icon = Icons.Default.Person,
//            name = "Adult",
//            price = "₹ 11,500"
//        ),
//        Items(
//            icon = Icons.Default.LocalOffer,
//            name = "Offer",
//            price = "₹ 11,000"
//        ),
//        Items(
//            icon = Icons.Default.WbSunny,
//            name = "Days",
//            price = "5"
//        )
//    )
//
//    Scaffold {
//        val ip = it
//
//        BoxWithConstraints(modifier = Modifier.padding(ip)) {
//            val maxHeight = maxHeight
//            val maxWidth = maxWidth
//            LazyColumn {
//
//                item {
//                    Box(modifier = Modifier.height(maxHeight / 2.5f)) {
//
//                        Image(
//                            painter = painterResource(R.drawable.tripsheepsplash),
//                            contentDescription = "",
//                            contentScale = ContentScale.Crop
//                        )
//
//                    }
//                }
//
//                item {
//
//                    Spacer(Modifier.height(12.dp))
//                    TripPlanHeaderText(text = "Trip Plan Name")
//                    Spacer(Modifier.padding(12.dp))
//
//                }
//
//                item{
//                    Spacer(Modifier.height(4.dp))
//                    Text("Description: Here the description of place will be shown")
//                    Spacer(Modifier.padding(12.dp))
//                }
//
//                item{
//                    Spacer(Modifier.height(12.dp))
//                    TripPlanHeaderText(text = "How Much It Will Cost")
//                    Spacer(Modifier.padding(12.dp))
//                }
//
//                item {
//
//                    Box(
//                        modifier = Modifier
//                            .fillMaxWidth()
//                            .height(270.dp),
//                        contentAlignment = Alignment.Center
//                    ) {
//                        GridItemBox(itemsList)
//                    }
//
//                }
//
//                item{
//                    Spacer(Modifier.height(12.dp))
//                    TripPlanHeaderText(text = "Your Trip Schedule   ")
//                    Spacer(Modifier.padding(12.dp))
//                }
//
//                item {
//
//                    ItineraryView()
//
//                }
//
//            }
//
//        }
//
//
//    }
//
//}
//
//@Composable
//fun GridItemBox(itemsList: List<Items>) {
//
//    LazyVerticalGrid(
//        columns = GridCells.Fixed(3),
//        horizontalArrangement = Arrangement.spacedBy(8.dp),
//        verticalArrangement = Arrangement.Center,
//        modifier = Modifier.fillMaxSize()
//    ) {
//        items(itemsList.size) {
//            ItemBox(
//                icon = itemsList[it].icon,
//                name = itemsList[it].name,
//                price = itemsList[it].price
//            )
//        }
//    }
//
//}
//
//@Composable
//fun ItemBox(icon: ImageVector, name: String, price: String) {
//    Column(
//        horizontalAlignment = Alignment.CenterHorizontally,
//        verticalArrangement = Arrangement.Center
//    ) {
//        Box(
//            modifier = Modifier
//                .wrapContentSize()
//                .padding(8.dp)
//                .clip(RoundedCornerShape(7.dp))
//                .background(Color.Black.copy(alpha = 0.1f)),
//            contentAlignment = Alignment.Center
//        ) {
//            Column(
//                modifier = Modifier
//                    .size(80.dp)
//                    .padding(8.dp)
//                    .clip(CircleShape)
//                    .background(Color.Black.copy(alpha = 0.2f)),
//                horizontalAlignment = Alignment.CenterHorizontally,
//                verticalArrangement = Arrangement.Center
//            ) {
//                Icon(
//                    imageVector = icon,
//                    modifier = Modifier
//                        .padding(vertical = 2.dp)
//                        .size(30.dp),
//                    contentDescription = name
//                )
//                Text(name, fontSize = 10.sp)
//            }
//        }
//
//        Text("$price", modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.Center)
//
//    }
//
//}
//
//@Composable
//fun ItineraryView() {
//
//    val itineraryList: List<String> =
//        "Day 1: Jaipur, Day 2: Amer Fort, Day 3: Jodhpur, Day 4: Udaipur, Day 5: Jaisalmer, Day 6: Departure".split(
//            ", "
//        )
//
//    LazyRow {
//        items(itineraryList.size) {
//            Row(
//                horizontalArrangement = Arrangement.spacedBy(8.dp),
//                verticalAlignment = Alignment.CenterVertically
//
//            ) {
//                Column(
//                    modifier = Modifier
//                        .size(120.dp)
//                        .padding(12.dp)
//                        .clip(RoundedCornerShape(12.dp))
//                        .background(Color.Blue.copy(alpha = 0.2f)),
//                    horizontalAlignment = Alignment.CenterHorizontally,
//                    verticalArrangement = Arrangement.Center
//                ) {
//                    val p1 = itineraryList[it].split(": ")
//                    Text(p1[0])
//                    Text(p1[1])
//                }
//                Spacer(modifier = Modifier.width(12.dp))
//                Icon(imageVector = Icons.Default.SwipeRightAlt, contentDescription = "")
//                Spacer(modifier = Modifier.width(12.dp))
//
//            }
//        }
//        item {
//            Column(
//                modifier = Modifier
//                    .size(120.dp)
//                    .padding(12.dp)
//                    .clip(RoundedCornerShape(12.dp))
//                    .background(Color.Blue.copy(alpha = 0.2f)),
//                horizontalAlignment = Alignment.CenterHorizontally,
//                verticalArrangement = Arrangement.Center
//            ) {
//                Text("Home", textAlign = TextAlign.Center)
//            }
//        }
//    }
//
//}
