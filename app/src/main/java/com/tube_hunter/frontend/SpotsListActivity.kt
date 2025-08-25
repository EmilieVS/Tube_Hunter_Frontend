package com.tube_hunter.frontend

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.tube_hunter.frontend.model.Welcome
import com.tube_hunter.frontend.ui.theme.DeepBlue
import com.tube_hunter.frontend.ui.theme.WhiteFoam
import com.tube_hunter.frontend.ui.theme.quicksand
import kotlinx.serialization.json.Json

class SpotsListActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
                SpotsList()
            }
        }
    }

@Preview
@Composable
fun SpotsList() {

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
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            val context = LocalContext.current
            val spots = parseSpots(context)

            BrandTitle()

            Spacer(modifier = Modifier.weight(1f))

            ShowCards(spots)

            Spacer(modifier = Modifier.weight(1f))

            Button(
                onClick = {
                    val intent = Intent(context, AddSpotActivity::class.java)
                    context.startActivity(intent)
                },
                colors = ButtonDefaults.buttonColors(WhiteFoam, Color.Black),
                modifier = Modifier.padding(bottom = 48.dp),
            ) {
                Text(
                    "Add spot",
                    fontFamily = quicksand,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@Composable
fun SpotCard(spot: Spot) {
    val context = LocalContext.current
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp)
            .clickable {
                val intent = Intent(context, SpotDetailsActivity::class.java)
                intent.putExtra("spot", spot)
                context.startActivity(intent)
    },
        colors = CardDefaults.cardColors(
            containerColor = WhiteFoam,
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 8.dp
        )
    ) {
        Column (
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.Start
        ) {
            AsyncImage(
                model = spot.photoUrl,
                contentDescription = spot.name,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(8.dp))
                    .height(180.dp)
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = spot.name,
                textAlign = TextAlign.Center,
                fontFamily = quicksand,
                fontWeight = FontWeight.Bold,
                color = DeepBlue,
                fontSize = 32.sp,
            )

            Spacer(modifier = Modifier.height(4.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = spot.location,
                    fontFamily = quicksand,
                    fontStyle = FontStyle.Italic,
                    fontSize = 16.sp,
                    color = DeepBlue,

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

@Composable
fun ShowCards(spots: List<Spot>) {
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.80f),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(spots) { spot ->
                SpotCard(spot)
            }
        }
}

fun readJsonFromRaw(context: Context, rawResId: Int): String {
    val inputStream = context.resources.openRawResource(rawResId)
    return inputStream.bufferedReader().use { it.readText() }
}

fun parseSpots(context: Context): List<Spot> {
    val jsonString = readJsonFromRaw(context, R.raw.spots)

    val response = Json { ignoreUnknownKeys = true }
        .decodeFromString<Welcome>(jsonString)

    return response.records.map { rec ->
        val f = rec.fields
        Spot(
            photoUrl = f.photos.firstOrNull()?.url ?: "",
            name = f.destination,
            location = f.destinationStateCountry,
            difficulty = f.difficultyLevel.toInt(),
            surfBreak = f.surfBreak.firstOrNull() ?: "",
            seasonBegins = f.peakSurfSeasonBegins,
            seasonEnds = f.peakSurfSeasonEnds
        )
    }
}


