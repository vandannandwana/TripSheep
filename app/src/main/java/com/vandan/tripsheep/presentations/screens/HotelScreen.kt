package com.vandan.tripsheep.presentations.screens

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Air
import androidx.compose.material.icons.filled.LocalParking
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Restaurant
import androidx.compose.material.icons.filled.StarRate
import androidx.compose.material.icons.filled.Wifi
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import coil3.compose.AsyncImage
import com.vandan.tripsheep.R
import com.vandan.tripsheep.presentations.viewmodels.HotelScreenViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView


data class Amenities(
    val name: String,
    val icon: ImageVector,
    val available: Boolean
)


@Composable
fun HotelScreen(
    hotelId: String,
    navHostController: NavHostController
) {
    val context = LocalContext.current
    val hotelViewModel = hiltViewModel<HotelScreenViewModel>()
    val hotelState = hotelViewModel.hotelInfo.collectAsStateWithLifecycle().value
    val scrollState = rememberScrollState()
    LaunchedEffect(hotelId) {
        CoroutineScope(Dispatchers.IO).launch {
            hotelViewModel.getHotelInfo(hotelId)
        }
    }


    Scaffold(modifier = Modifier.fillMaxSize()) {
        BoxWithConstraints(
            modifier = Modifier
                .verticalScroll(scrollState)
                .fillMaxSize()
                .padding(it),
            contentAlignment = Alignment.Center
        ) {
            val maxHeight = maxHeight
            val maxWidth = maxWidth

            if (hotelState.data == null) {
                CircularProgressIndicator()
                Log.d("HotelInfo", hotelState.error.toString())
            } else {

                val pagerState = rememberPagerState { hotelState.data.imageUrls.size }

                val amenities = listOf(
                    Amenities("Wifi", Icons.Default.Wifi, hotelState.data.wifi),
                    Amenities("Parking", Icons.Default.LocalParking, hotelState.data.parking),
                    Amenities("Restaurent", Icons.Default.Restaurant, hotelState.data.restaurent),
                    Amenities("Ac", Icons.Default.Air, hotelState.data.ac)

                )

                Log.d("HotelInfo", hotelState.data.toString())
                Column(modifier = Modifier.fillMaxSize()) {

                    Box{
                        HorizontalPager(pagerState) {index->
                            AsyncImage(
                                model = hotelState.data.imageUrls[index],
                                contentDescription = "",
                                contentScale = ContentScale.Crop,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 12.dp)
                                    .height(300.dp)
                                    .clip(
                                        RoundedCornerShape(
                                            topStart = 12.dp,
                                            topEnd = 12.dp,
                                            bottomStart = 24.dp,
                                            bottomEnd = 24.dp
                                        )
                                    )
                            )
                        }


                    }

                    HotelScreenDotsIndicator(modifier = Modifier.align(Alignment.CenterHorizontally), pagerState=pagerState)


                    Row(
                        modifier = Modifier.fillMaxWidth().padding(horizontal = 12.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        Column(
                            verticalArrangement = Arrangement.spacedBy(4.dp)
                        ){

                            Text(
                                text = hotelState.data.hotelName,
                                fontFamily = FontFamily(Font(R.font.poppins_medium)),
                                color = colorResource(R.color.sky_black),
                                fontSize = 24.sp
                            )
                            Row {
                                Icon(imageVector = Icons.Default.LocationOn, contentDescription = "location_icon", tint = colorResource(R.color.sky_black))
                                Text(
                                    color = colorResource(R.color.sky_grey),
                                    text = hotelState.data.address,
                                    fontFamily = FontFamily(Font(R.font.poppins_medium))
                                )
                            }


                        }

                        Column(
                            verticalArrangement = Arrangement.spacedBy(4.dp)
                        ){
                            Text(
                                text = hotelState.data.roomPrice + "/night",
                                color = colorResource(R.color.sky_black),
                                fontFamily = FontFamily(Font(R.font.poppins_medium))
                            )
                            Row {

                                Icon(imageVector = Icons.Default.StarRate, contentDescription = "")

                                Text(
                                    softWrap = true,
                                    text = hotelState.data.stars.toString() + " Star",
                                    color = colorResource(R.color.sky_black),
                                    fontFamily = FontFamily(Font(R.font.poppins_medium))
                                )

                            }

                        }

                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    Text(
                        text = "Amenities",
                        fontFamily = FontFamily(Font(R.font.poppins_medium)),
                        color = colorResource(R.color.sky_black),
                        fontSize = 18.sp,
                        modifier = Modifier.padding(start = 16.dp)
                    )
                    Spacer(modifier = Modifier.height(24.dp))

                    LazyRow(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        items(amenities.size) { index ->
                            if (amenities[index].available) {
                                AmenityItem(amenities[index])
                            }
                        }
                    }
                    Spacer(modifier = Modifier.height(24.dp))
                    Text(
                        text = "Located At:",
                        fontFamily = FontFamily(Font(R.font.poppins_medium)),
                        color = colorResource(R.color.sky_black),
                        fontSize = 18.sp,
                        modifier = Modifier.padding(start = 16.dp)
                    )
                    Spacer(modifier = Modifier.height(24.dp))

                    Box(modifier = Modifier.size(300.dp).padding(start = 16.dp)){
                        AndroidView(
                            modifier = Modifier.size(300.dp).clipToBounds(),
                            factory = {context->
                                MapView(context).apply {
                                    setMultiTouchControls(true)
                                    setTileSource(TileSourceFactory.OpenTopo)
                                    minimumHeight = 200.dp.value.toInt()
                                    minimumWidth = 200.dp.value.toInt()
                                    controller.setZoom(15.0)
                                    controller.setCenter(GeoPoint(22.307159, 73.181221))

                                }
                            }
                        )
                    }

                    Button(
                        onClick = {
                            openGoogleMaps(context = context, latitude = 22.307159, longitude = 73.181221)
                                  },
                        modifier = Modifier.height(96.dp).fillMaxWidth().padding(horizontal = 16.dp, vertical = 12.dp),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text(
                            text = "Go to Google Maps",
                            fontFamily = FontFamily(Font(R.font.poppins_medium)),
                            color = colorResource(R.color.white),
                            fontSize = 24.sp,
                            modifier = Modifier.padding(start = 16.dp)
                        )
                    }



                }
            }
        }
    }
}

fun openGoogleMaps(context: Context, latitude: Double, longitude: Double) {
    val gmmIntentUri = Uri.parse("geo:$latitude,$longitude?q=$latitude,$longitude")
    val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
    mapIntent.setPackage("com.google.android.apps.maps")
        context.startActivity(mapIntent)

}


@Composable
fun AmenityItem(amenities: Amenities) {

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceAround,
        modifier = Modifier
            .padding(8.dp)
            .clip(RoundedCornerShape(7.dp))
            .background(colorResource(R.color.sky_blue).copy(0.4f))
            .padding(horizontal = 12.dp, vertical = 8.dp)

    ) {
        Box {

            Icon(imageVector = amenities.icon, contentDescription = "")

        }
        Text(amenities.name)
    }

}

@Composable
fun HotelScreenDotsIndicator(modifier: Modifier = Modifier, pagerState: PagerState) {
    Row(
        modifier
            .wrapContentHeight()
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
        repeat(pagerState.pageCount) { iteration ->
            val color =
                if (pagerState.currentPage == iteration) colorResource(R.color.sky_blue) else Color.Black
            Box(
                modifier = Modifier
                    .size(16.dp)
                    .padding(2.dp)
                    .clip(CircleShape)
                    .background(color)
//                    .border(1.dp, color = colorResource(R.color.sky_grey), shape = CircleShape)
            )
        }
    }
}
