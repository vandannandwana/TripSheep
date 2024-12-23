package com.vandan.tripsheep.presentations.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FlightTakeoff
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.vandan.tripsheep.R

@PreviewLightDark
@Composable
fun FromToComponent(modifier: Modifier = Modifier) {

    var from by rememberSaveable {
        mutableStateOf("")
    }

    var to by rememberSaveable {
        mutableStateOf("")
    }
    Box(
        modifier = modifier, contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier,
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            TextField(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 34.dp),
                value = from,
                onValueChange = { from = it },
                singleLine = true,
                placeholder = {
                    Text(text = "From",
                        fontFamily = FontFamily(Font(R.font.poppins_medium)),
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Light
                    )
                },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Home,
                        contentDescription = "search_icon",
                        modifier = Modifier.padding(start = 14.dp, end = 8.dp)
                    )
                },
                shape = RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp),
                colors = TextFieldDefaults.colors(
                    unfocusedContainerColor = Color.White.copy(alpha = 0.8f),
                    focusedContainerColor = Color.White.copy(alpha = 0.9f),
                    unfocusedIndicatorColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent
                )
            )
            Spacer(modifier = Modifier.height(16.dp))
            TextField(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 34.dp),
                value = to,
                onValueChange = { to = it },
                singleLine = true,
                placeholder = {
                    Text(text = "To",
                        fontFamily = FontFamily(Font(R.font.poppins_medium)),
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Light
                    )},
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.FlightTakeoff,
                        contentDescription = "search_icon",
                        modifier = Modifier.padding(start = 14.dp, end = 8.dp)
                    )
                },
                shape = RoundedCornerShape(bottomStart = 12.dp, bottomEnd = 12.dp),
                colors = TextFieldDefaults.colors(
                    unfocusedContainerColor = Color.White.copy(alpha = 0.8f),
                    focusedContainerColor = Color.White.copy(alpha = 0.9f),
                    unfocusedIndicatorColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent
                )
            )
        }
        Box(
            modifier = Modifier
                .size(56.dp)
                .align(Alignment.CenterEnd)
                .clip(CircleShape)
                .background(colorResource(R.color.sky_black))
                .clickable {  },
            contentAlignment = Alignment.Center,
        ){
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = "search",
                tint = Color.White,
            )
        }

    }

}