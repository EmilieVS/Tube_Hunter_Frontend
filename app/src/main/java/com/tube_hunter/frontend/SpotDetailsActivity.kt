package com.tube_hunter.frontend

import android.R.attr.onClick
import android.app.Activity
import android.content.Intent
import android.icu.lang.UCharacter
import android.os.Bundle
import android.widget.Button
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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.view.WindowCompat.enableEdgeToEdge
import com.tube_hunter.frontend.ui.theme.BackButton
import com.tube_hunter.frontend.ui.theme.WhiteFoam
import com.tube_hunter.frontend.ui.theme.chewy
import com.tube_hunter.frontend.ui.theme.quicksand

class SpotDetailsActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SpotDetails()
        }
    }

    @Preview
    @Composable
    fun SpotDetails() {
        Box(
            modifier = Modifier.fillMaxSize()
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
            ) {
                Text(
                    text = "TUBE HUNTER",
                    textAlign = TextAlign.Center,
                    fontSize = 48.sp,
                    fontFamily = chewy,
                    color = WhiteFoam,
                    modifier = Modifier.padding(top = 48.dp)
                )

                SpotCard(

                )

                BackButton {
                    val intent = Intent(this@SpotDetailsActivity, SpotsListActivity::class.java)
                    startActivity(intent)
                }
            }
        }
    }
}
@Preview
@Composable
fun SpotCard() {
    Card(
        modifier = Modifier
            .padding(32.dp)
            .fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = WhiteFoam
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp // petite ombre
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.Start
        ) {
            Image(
                painter = painterResource(id = R.drawable.background_tube_hunter),
                contentDescription = "Spot photo",
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp))
                    .height(180.dp),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = "Cawabunga",
                fontSize = 32.sp,
                fontFamily = quicksand,
                fontWeight = FontWeight.Bold,
                color = Color.Black //mettre bleu marine
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Biscarrosse, France",
                fontSize = 14.sp,
                fontFamily = quicksand,
                color = Color.Black // bleu marine leger
            )

            Spacer(modifier = Modifier.height(32.dp))

            Row (
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ){
                Text(text = "DIFFICULTY", fontSize = 14.sp, color = Color.Black, fontWeight = FontWeight.Bold, fontFamily = quicksand)
                Text(text = "1/5", fontSize = 14.sp, color = Color.Black, fontFamily = quicksand) // mettre icones
            }

            Spacer(modifier = Modifier.height(8.dp))

            Row (
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ){
                Text(text = "SURF BREAK", fontSize = 14.sp, color = Color.Black, fontWeight = FontWeight.Bold, fontFamily = quicksand)
                Text(text = "Reef Break", fontSize = 14.sp, color = Color.Black, fontFamily = quicksand)
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(text = "SEASON", fontSize = 14.sp, color = Color.Black, fontWeight = FontWeight.Bold, fontFamily = quicksand)

            Spacer(modifier = Modifier.height(4.dp))

            Row (
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ){
                Text(text = "03 Jul", fontSize = 14.sp, color = Color.Black, fontFamily = quicksand)
                Image(
                    painter = painterResource(id = R.drawable.logo_light_tube_hunter),
                    contentDescription = "Right Arrow",
                    modifier = Modifier
                        .height(16.dp),
                )
                Text(text = "10 Oct", fontSize = 14.sp, color = Color.Black, fontFamily = quicksand)
            }
        }
    }
}