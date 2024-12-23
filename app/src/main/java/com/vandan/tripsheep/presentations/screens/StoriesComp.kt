package com.vandan.tripsheep.presentations.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.vandan.tripsheep.R

@Preview(showBackground = true)
@Composable
fun StoriesComp() {

    Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {

        LazyRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            items(stories.size) { index ->

                StoryItemView(story = stories[index])

            }

        }

    }

}

@Composable
fun StoryItemView(modifier: Modifier = Modifier, story: Story) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Box(
            modifier = Modifier
                .clip(CircleShape)
                .size(95.dp)
                .background(
                    if (!story.seen) colorResource(
                        R.color.mint_green
                    ) else Color.Gray
                )
                .padding(2.dp)
        ) {
            AsyncImage(
                model = story.profile,
                modifier = Modifier
                    .fillMaxSize()
                    .clip(CircleShape).border(width = 2.dp, color = Color.White, shape = CircleShape)                   ,
                contentScale = ContentScale.Crop,
                contentDescription = "profile_image"
            )

        }
        Text(text = story.name, fontFamily = FontFamily(Font(R.font.montserrat_font)), fontWeight = FontWeight.SemiBold, softWrap = true)
    }


}

data class Story(val profile: String, val name: String, val seen: Boolean)

val stories = listOf(
    Story(
        "https://www.bing.com/th?id=OIP.GPFEY6kfgxbsja6gmrW6rwAAAA&w=155&h=103&c=8&rs=1&qlt=90&o=6&pid=3.1&rm=2",
        "Vandan",
        false
    ),
    Story(
        "https://www.bing.com/th?id=OIP.enHnJ33r9erP04V1MdL9iwHaE7&w=154&h=103&c=8&rs=1&qlt=90&o=6&pid=3.1&rm=2",
        "Nikhil",
        false
    ),
    Story(
        "https://www.bing.com/th?id=OIP.wAL5NOHwQEmPGYpjLmrczAHaE8&w=155&h=103&c=8&rs=1&qlt=90&o=6&pid=3.1&rm=2",
        "Shivam",
        true
    ),
    Story(
        "https://www.bing.com/th?id=OIP.nFUetkBOK0vuztOmwzsvfQHaFj&w=151&h=106&c=8&rs=1&qlt=90&o=6&pid=3.1&rm=2",
        "Soham",
        true
    ),
    Story("https://picsum.photos/200/300", "Shrawan", true),
)