package com.vandan.tripsheep.presentations.screens

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import coil3.compose.AsyncImage
import com.vandan.tripsheep.R
import com.vandan.tripsheep.data.resource.TripPlanState
import com.vandan.tripsheep.navigations.Screens
import com.vandan.tripsheep.presentations.viewmodels.SearchScreenViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.yield

@Composable
fun SearchScreen(navHostController: NavHostController) {

    val savedStateHandle = SavedStateHandle()
    var searchText by remember { mutableStateOf("") }
    val savedPageIndex = savedStateHandle["currentPage"] ?: 0
    var bannersCount by remember {
        mutableIntStateOf(savedStateHandle["currentPage"] ?: 0)
    }
    val pagerState = rememberPagerState { bannersCount }


    DisposableEffect(Unit) {
        onDispose {
            savedStateHandle["currentPage"] = pagerState.currentPage
        }
    }

    val scrollState = rememberScrollState()
    val viewModel = hiltViewModel<SearchScreenViewModel>()
    val tripState = viewModel.trips.collectAsStateWithLifecycle()

    LaunchedEffect(key1 = tripState.value.trips) {
        Log.d("TripImages", tripState.value.trips.toString())
        bannersCount = tripState.value.trips.size

        if(bannersCount>0){
            while(true){
                yield()
                delay(3000)
                val nextPage = (pagerState.currentPage + 1) % bannersCount
                pagerState.animateScrollToPage(nextPage)
            }
        }

    }

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(500.dp)
                    .background(Color.Black)
            ) {
                SearchScreenBanner(
                    modifier = Modifier.fillMaxSize(),
                    pagerState = pagerState,
                    tripState = tripState,
                    navHostController = navHostController
                )
                SearchScreenSearchBox (
                    searchText = searchText,
                    placeholder = "Which is your next destination ?",
                    onValueChange = { searchText = it },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(24.dp)
                )

                FromToComponent(modifier = Modifier.padding(end = 24.dp).align(Alignment.Center))

                SearchScreenDotsIndicator(
                    modifier = Modifier.align(Alignment.BottomCenter),
                    pagerState = pagerState
                )
            }
            Spacer(modifier = Modifier.height(12.dp))
            SearchHeaderText(modifier = Modifier.fillMaxWidth().padding(16.dp), text = "Places Near You")
            Spacer(modifier = Modifier.height(12.dp))
            PopularPlaces()
        }
    }
}

@Composable
fun SearchScreenDotsIndicator(modifier: Modifier = Modifier, pagerState: PagerState) {
    Row(
        modifier
            .wrapContentHeight()
            .fillMaxWidth()
            .padding(bottom = 24.dp, end = 24.dp),
        horizontalArrangement = Arrangement.End
    ) {
        repeat(pagerState.pageCount) { iteration ->
            val color =
                if (pagerState.currentPage == iteration) Color.White.copy(0.9f) else Color.Gray.copy(alpha = 0.5f)
            Box(
                modifier = Modifier
                    .padding(2.dp)
                    .clip(CircleShape)
                    .background(color)
                    .size(12.dp)
            )
        }
    }
}

@Composable
fun SearchBox(
    modifier: Modifier = Modifier,
    searchText: String,
    placeholder: String = "Search",
    onValueChange: (String) -> Unit
) {
    TextField(
        value = searchText,
        onValueChange = { onValueChange(it) },
        modifier = modifier.clip(CircleShape),
        colors = TextFieldDefaults.colors(
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            unfocusedContainerColor = Color.LightGray.copy(alpha = 0.3f),
            focusedContainerColor = Color.LightGray.copy(alpha = 0.3f)
        ),
        singleLine = true,
        placeholder = {
            Text(text = placeholder,
                fontFamily = FontFamily(Font(R.font.poppins_medium)),
                fontSize = 13.sp,
                fontWeight = FontWeight.Light
            )},
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = "search_icon",
                modifier = Modifier.padding(start = 24.dp, end = 8.dp)
            )
        }
    )
}


@Composable
fun SearchScreenSearchBox(
    modifier: Modifier = Modifier,
    searchText: String,
    placeholder: String = "Search",
    onValueChange: (String) -> Unit
) {
    TextField(
        value = searchText,
        onValueChange = { onValueChange(it) },
        modifier = modifier.clip(CircleShape),
        colors = TextFieldDefaults.colors(
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            unfocusedContainerColor = colorResource(R.color.white).copy(alpha = 0.4f),
            focusedContainerColor = colorResource(R.color.white).copy(alpha = 0.9f)
        ),
        singleLine = true,
        placeholder = {
            Text(text = placeholder,
                fontFamily = FontFamily(Font(R.font.poppins_medium)),
                fontSize = 13.sp,
                fontWeight = FontWeight.Light
            )},
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = "search_icon",
                modifier = Modifier.padding(start = 24.dp, end = 8.dp)
            )
        }
    )
}

@Composable
fun SearchScreenBanner(
    modifier: Modifier = Modifier,
    pagerState: PagerState,
    tripState: State<TripPlanState>,
    navHostController: NavHostController
) {
    Box(modifier = modifier) {
        HorizontalPager(
            state = pagerState,
            userScrollEnabled = false
        ) {
            if (tripState.value.isLoading) {
                Log.d("TripImages", "Loading")
                AsyncImage(
                    model = banners[it].imageUrl,
                    contentDescription = "banner_image",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
            } else if (tripState.value.error != null) {
                Log.d("TripImages", tripState.value.error.toString())
                Text(text = tripState.value.error.toString())
            }
            if (tripState.value.trips.isNotEmpty()) {
                Log.d("TripImages", tripState.value.trips[it].tripImages.toString())
                AsyncImage(
                    model = tripState.value.trips[it].tripImages[0],
                    contentDescription = "banner_image",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxSize()
                        .clickable {
                            navHostController.navigate(Screens.TripPlanScreen.route + "/${tripState.value.trips[it].trip_id}")
                        }
                )
            }
        }
    }

}

@Composable
fun PopularPlaces(modifier: Modifier = Modifier) {
    LazyRow(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(2.dp)
    ) {
        items(banners.size) { index ->

            PopularPlacesItem(place = banners[index])

        }
    }
}

@Composable
fun PopularPlacesItem(modifier: Modifier = Modifier, place: BannerModel) {
    Card(
        Modifier
            .height(245.dp)
            .padding(8.dp)
            .width(265.dp)
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            AsyncImage(
                model = place.imageUrl,
                contentDescription = "banner_image",
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
            PopularPlaceNameText()
        }
    }

}

@Composable
fun PopularPlaceNameText(text: String = "Place Name") {
    Text(
        text = text,
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, top = 16.dp),
        color = Color.White,
        fontWeight = FontWeight.ExtraBold,
        fontSize = 24.sp,
        textAlign = TextAlign.Start,
    )
}

data class BannerModel(
    val imageUrl: String,
)

val banners = listOf(
    BannerModel("https://th.bing.com/th/id/R.5c428f543372c2c3e8e4a61fc9a4244e?rik=GIpVXh2Ou0UIIQ&riu=http%3a%2f%2fgetwallpapers.com%2fwallpaper%2ffull%2f2%2f3%2f0%2f1085211-download-free-world-beautiful-places-wallpapers-2880x1800-cell-phone.jpg&ehk=FiL3UiTL2QPgE9rbpOL9D%2bDWgzi21XStB0Rr550yI%2fE%3d&risl=1&pid=ImgRaw&r=0"),
    BannerModel("https://th.bing.com/th/id/R.e6fc04abc90f1302c2695d0a25466ea6?rik=IeDPtDDwpym3%2bA&riu=http%3a%2f%2fgetwallpapers.com%2fwallpaper%2ffull%2fe%2f1%2f0%2f1085296-new-world-beautiful-places-wallpapers-1920x1200-samsung-galaxy.jpg&ehk=FLnrflt%2fvYRPWPecpZbM6bwbAg9k%2bdriRqQoPmF8gKY%3d&risl=&pid=ImgRaw&r=0"),
    BannerModel("https://th.bing.com/th/id/R.c7e89b930f8aa73cf72d5f167d1d16ed?rik=3obl%2fhI6QdNIMA&riu=http%3a%2f%2fthewowstyle.com%2fwp-content%2fuploads%2f2015%2f01%2ffree-beautiful-place-wallpaper-hd-173.jpg&ehk=92RRpT4hrYheMDBZkK0HhLLXx9%2fGDjnafeDmbgjE1K8%3d&risl=&pid=ImgRaw&r=0")
)