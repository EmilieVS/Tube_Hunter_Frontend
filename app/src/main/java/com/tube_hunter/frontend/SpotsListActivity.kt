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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
            fontSize = 48.sp,
            fontFamily = chewy,
            color = WhiteFoam,
            modifier = Modifier.padding(top = 48.dp, bottom = 8.dp)
        )
            SpotCard()
        }
    }
}

@Preview
@Composable
fun SpotCard() {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant,
        ),
        modifier = Modifier
            .size(width = 240.dp, height = 300.dp)
            .padding(vertical = 0.dp),
    ) {
        Image(
            painter = painterResource(id = R.drawable.sunset_wave),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.size(width = 240.dp, height = 140.dp)
                .padding(24.dp),
        )
        Text(
            text = "Name of the spot",
            modifier = Modifier
                .padding(start = 24.dp),
            textAlign = TextAlign.Center,
            fontFamily = quicksand,
            fontWeight = FontWeight.Bold,
        )
    }
}