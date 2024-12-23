package com.vandan.tripsheep.presentations.screens

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.exyte.animatednavbar.AnimatedNavigationBar
import com.exyte.animatednavbar.animation.indendshape.ShapeCornerRadius
import com.exyte.animatednavbar.utils.noRippleClickable
import com.vandan.tripsheep.R
import kotlinx.coroutines.launch

@Composable
fun MainScreen(uid:String?,navHostController: NavHostController) {

    val pagerState = rememberPagerState(pageCount = { 3 })
    val navBarItems = remember { NavigationBarItems.entries.toTypedArray() }
    var currentPage by rememberSaveable  { mutableIntStateOf(0) }
    val coroutineScope = rememberCoroutineScope()
//    Toast.makeText(LocalContext.current,uid,Toast.LENGTH_SHORT).show()
    LaunchedEffect(currentPage) {
        coroutineScope.launch {
            pagerState.animateScrollToPage(currentPage, animationSpec = tween(durationMillis = 700, easing = FastOutSlowInEasing))
        }
    }

    Scaffold(
        bottomBar = {
            AnimatedNavigation(
                currentPage=currentPage,
                navBarItems = navBarItems,
                onItemClick = {currentPage = it}
            )
        }
    ) { ip ->
        BoxWithConstraints(
            modifier = Modifier
                .fillMaxSize()
                .padding(ip),
            contentAlignment = Alignment.Center,
        ) {
            val boxWidth = maxWidth
            val boxHeight = maxHeight

            HorizontalPager(
                state = pagerState,
                modifier = Modifier
                    .height(boxHeight)
                    .width(boxWidth),
                userScrollEnabled = false
            ) { pages ->

                when (pages) {
                    0 -> HomeScreen(navHostController = navHostController)
                    1 -> SearchScreen(navHostController = navHostController)
                    2 -> ProfileScreen(navHostController)
                }

            }


        }
    }


}

@Composable
fun AnimatedNavigation(
    modifier: Modifier = Modifier,
    currentPage: Int,
    navBarItems: Array<NavigationBarItems>,
    onItemClick: (Int) -> Unit,
) {
    AnimatedNavigationBar(
        selectedIndex = currentPage, modifier = Modifier
            .padding(24.dp),
        barColor = colorResource(id = R.color.sky_blue).copy(alpha = 0.7f),
        ballColor = colorResource(id = R.color.sky_orange).copy(alpha = 0.7f),
        cornerRadius = ShapeCornerRadius(60f, 60f, 60f, 60f)
    ) {

        navBarItems.forEach { item ->

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .noRippleClickable { onItemClick(item.ordinal) },
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = item.icon,
                    contentDescription = null,
                    tint = if (currentPage == item.ordinal) colorResource(R.color.white) else colorResource(R.color.sky_grey)
                )
            }

        }

    }
}


enum class NavigationBarItems(val icon: ImageVector) {

    Person(icon = Icons.Default.Home),
    Search(icon = Icons.Default.Search),
    Profile(icon = Icons.Default.Person)

}