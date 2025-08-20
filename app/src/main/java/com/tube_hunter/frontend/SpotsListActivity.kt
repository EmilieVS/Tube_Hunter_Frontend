package com.tube_hunter.frontend

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
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
            Column {
                SpotCard()
                Spacer(modifier = Modifier.height(24.dp))
                SpotCard()
                Spacer(modifier = Modifier.height(24.dp))
            }
            val context = LocalContext.current
            Button(
                onClick = {
                    val intent = Intent(context, MainActivity::class.java)
                    context.startActivity(intent)
                },
                colors = ButtonDefaults.buttonColors(WhiteFoam, Color.Black),
            ) {
                Text("Add spot", fontFamily = quicksand, fontWeight = FontWeight.Bold)
            }
        }
    }
}

@Composable
fun SpotCard() {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = WhiteFoam,
        ),
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 40.dp),
    ) {
        Column {
            Image(
                painter = painterResource(id = R.drawable.sunset_wave),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(8.dp))
                    .height(180.dp)
                    .padding(18.dp)
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
            Row(
                modifier = Modifier.fillMaxWidth()
                    .padding(horizontal = 18.dp)
                    .offset(0.dp, (-12).dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ){
                Text(
                    text = "Uluwatu, Bali",
                    textAlign = TextAlign.Center,
                    fontFamily = quicksand,
                    fontWeight = FontWeight.Medium,
                    fontSize = 16.sp,
                )
                Row {
                    DifficultyFilledImage()
                    DifficultyFilledImage()
                    DifficultyFilledImage()
                    DifficultyFilledImage()
                    DifficultyImage()
                }
            }
        }
    }
}

@Composable
fun DifficultyImage() {
    Image(
        painter = painterResource(id = R.drawable.person_simple_snowboard_bold),
        contentDescription = null,
        modifier = Modifier.size(width = 18.dp, height = 18.dp)
    )
}

@Composable
fun DifficultyFilledImage() {
    Image(
        painter = painterResource(id = R.drawable.person_simple_snowboard_fill),
        contentDescription = null,
        modifier = Modifier.size(width = 18.dp, height = 18.dp)
    )
}