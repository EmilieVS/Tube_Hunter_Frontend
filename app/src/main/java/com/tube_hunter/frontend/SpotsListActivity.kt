package com.tube_hunter.frontend

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tube_hunter.frontend.ui.theme.WhiteFoam
import com.tube_hunter.frontend.ui.theme.chewy
import com.tube_hunter.frontend.ui.theme.quicksand

class SpotsListActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BackgroundLoading()
        }
    }
}
@Preview
@Composable
fun BackgroundLoading() {
    Box(
        modifier = Modifier.fillMaxSize(),
    ) {
        Image(
            painter = painterResource(id = R.drawable.background_tube_hunter),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.matchParentSize()
        )
        Column(
            modifier = Modifier
                .align(Alignment.Center)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ){
        Text(
            text = "TUBE HUNTER",
            textAlign = TextAlign.Center,
            fontSize = 56.sp,
            fontFamily = chewy,
            color = WhiteFoam,
            modifier = Modifier.padding(top = 48.dp, bottom = 8.dp)
        )
            SpotCard()
            SpotCard()
        }
    }
}

@Preview
@Composable
fun SpotCard() {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = WhiteFoam,
        ),
        modifier = Modifier
            .size(width = 300.dp, height = 280.dp)
            .padding(vertical = 8.dp),
    ) {
        Column {
            Image(
                painter = painterResource(id = R.drawable.sunset_wave),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.size(width = 300.dp, height = 180.dp)
                    .padding(18.dp)
                    .clip(RoundedCornerShape(8.dp))
            )
            Text(
                text = "Cawabonga",
                modifier = Modifier.padding(start = 18.dp)
                    .offset(0.dp, (-12).dp),
                textAlign = TextAlign.Center,
                fontFamily = quicksand,
                fontWeight = FontWeight.SemiBold,
                fontSize = 28.sp,
            )
            Text(
                text = "Uluwatu, Bali",
                modifier = Modifier.padding(start = 18.dp)
                    .offset(0.dp, (-12).dp),
                textAlign = TextAlign.Center,
                fontFamily = quicksand,
                fontWeight = FontWeight.Medium,
                fontSize = 16.sp,
            )
            Image(
                painter = painterResource(id = R.drawable.person_simple_snowboard_bold),
                contentDescription = null,
                modifier = Modifier.size(width = 30.dp, height = 18.dp)
            )
        }
    }
}