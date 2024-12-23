package com.vandan.tripsheep.presentations.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Help
import androidx.compose.material.icons.filled.CreditCard
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.PersonAdd
import androidx.compose.material.icons.filled.PowerSettingsNew
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.vandan.tripsheep.R
import com.vandan.tripsheep.data.resource.UserState
import com.vandan.tripsheep.navigations.Screens
import com.vandan.tripsheep.presentations.viewmodels.ProfileViewModel

@Composable
fun ProfileScreen(navHostController: NavHostController) {

    val viewModel = hiltViewModel<ProfileViewModel>()

    val userState = viewModel.user.collectAsStateWithLifecycle().value

    val settingItems = listOf(
        SettingItem("Change Password",Icons.Default.Lock),
        SettingItem("Invite Friends",Icons.Default.PersonAdd),
        SettingItem("Help Center", Icons.AutoMirrored.Filled.Help),
        SettingItem("Payments",Icons.Default.CreditCard),
        SettingItem("LogOut",Icons.Default.PowerSettingsNew, onClickAction = {viewModel.logoutUser();navHostController.popBackStack();navHostController.navigate(
            Screens.SplashScreen.route)})
    )

    Scaffold {
        val innerPadding = it
        BoxWithConstraints(modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding), contentAlignment = Alignment.TopCenter){
            val maxHeight = maxHeight
            val maxWidth = maxWidth
            val spacerHeight = maxHeight/34
            val topPadding = maxHeight/18
            LazyColumn(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Top){
                item {
                    Spacer(modifier = Modifier.height(topPadding))
                    UserInfo(
                        modifier = Modifier,
                        maxWidth = maxWidth,
                        userState = userState
                    )
                }
                item { Spacer(modifier = Modifier.height(spacerHeight)) }
                items(settingItems.size){index->
                    ProfileSettingListItem(modifier = Modifier
                        .height(maxHeight / 8.5f)
                        .padding(12.dp),item = settingItems[index]
                    )
                }

            }
        }
    }
}

data class SettingItem(
    val name:String,
    val icon:ImageVector,
    val onClickAction:()->Unit = {}
)



@Composable
fun ProfileSettingListItem(modifier: Modifier = Modifier,item: SettingItem) {

    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable { item.onClickAction() },
        elevation = CardDefaults.cardElevation(
            defaultElevation = 7.dp
        )
        ){
        Row(
            modifier= Modifier
                .fillMaxSize()
                .background(colorResource(id = R.color.white))
                .padding(8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ){

            Text(text = item.name)
            Icon(imageVector = item.icon, contentDescription = "settings")

        }
    }

}

@Composable
fun UserInfo(modifier: Modifier = Modifier, maxWidth: Dp, userState: UserState) {
    Box(modifier = modifier, contentAlignment = Alignment.TopCenter){
        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Center) {
            if(userState.isLoading){
                Text(text = "Loading...")
            }else if(userState.error != null){
                Text(text = userState.error.toString())
            }
            if(userState.user != null){
                Column (modifier.width(maxWidth/1.5f)){
                    Text(
                        text = userState.user.name,
                        fontSize = 34.sp,
                        softWrap = true,
                        color = colorResource(id = R.color.charcoal_gray),
                        fontWeight = FontWeight.Bold,
                    )
                    Text(
                        text = "View and edit profile",
                        color = colorResource(id = R.color.cool_gray),
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }
            Image(
                painter = painterResource(id = R.drawable.tripsheepsplash),
                contentDescription = "user_image",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .clip(CircleShape)
                    .size(maxWidth / 3.5f),
            )
        }

    }
}

@Preview
@Composable
private fun ProfileScreenPreview() {

    ProfileScreen(navHostController = rememberNavController())
    
}
