package com.tube_hunter.frontend.ui.screen.spotdetails

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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.tube_hunter.frontend.R
import com.tube_hunter.frontend.ui.component.BrandTitle
import com.tube_hunter.frontend.ui.component.SpotDetailsUi
import com.tube_hunter.frontend.ui.navigation.Screen
import com.tube_hunter.frontend.ui.screen.spotlist.IconDifficulty
import com.tube_hunter.frontend.ui.screen.spotlist.SpotListViewModel
import com.tube_hunter.frontend.ui.theme.DeepBlue
import com.tube_hunter.frontend.ui.theme.LagoonBlue
import com.tube_hunter.frontend.ui.theme.WhiteFoam
import com.tube_hunter.frontend.ui.theme.quicksand
import java.util.Locale

@Composable
fun SpotDetailsScreen(spotId: String, onNavigate: (String) -> Unit, viewModel: SpotListViewModel = viewModel()) {
    val spots by viewModel.spots.collectAsState()
    val spot = spots.find { it.id == spotId }

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
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            BrandTitle()

            Spacer(modifier = Modifier.weight(1f))

            spot?.let {
                SpotDetailsCard(it)
            } ?: Text("Spot not found", color = LagoonBlue)

            Spacer(modifier = Modifier.weight(1f))

            Button(
                onClick = {
                    onNavigate(Screen.SpotList.route)
                },
                colors = ButtonDefaults.buttonColors(WhiteFoam, DeepBlue),
                modifier = Modifier.padding(bottom = 48.dp)
            ) {
                Text(
                    text = "Back",
                    fontFamily = quicksand,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@Composable
fun SpotDetailsCard(spot: SpotDetailsUi) {
    Card(
        modifier = Modifier
            .padding(horizontal = 24.dp)
            .fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = WhiteFoam
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 8.dp
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.Start
        ) {
            AsyncImage(
                model = spot.photoUrl,
                contentDescription = spot.name,
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(8.dp))
                    .height(180.dp),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = spot.name,
                fontSize = 32.sp,
                fontFamily = quicksand,
                fontWeight = FontWeight.Bold,
                color = DeepBlue
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = "${spot.city}, ${spot.country}",
                fontSize = 16.sp,
                fontStyle = FontStyle.Italic,
                fontFamily = quicksand,
                color = DeepBlue
            )

            Spacer(modifier = Modifier.height(48.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "DIFFICULTY",
                    fontSize = 16.sp,
                    color = DeepBlue,
                    fontWeight = FontWeight.Bold,
                    fontFamily = quicksand
                )
                IconDifficulty(spot.difficulty)
            }

            Spacer(modifier = Modifier.height(24.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "SURF BREAKS",
                    fontSize = 16.sp,
                    color = DeepBlue,
                    fontWeight = FontWeight.Bold,
                    fontFamily = quicksand
                )
                Text(
                    text = spot.surfBreaks,
                    fontSize = 16.sp,
                    color = DeepBlue,
                    fontFamily = quicksand
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.Top,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "SEASON",
                    fontSize = 16.sp,
                    color = DeepBlue,
                    fontWeight = FontWeight.Bold,
                    fontFamily = quicksand
                )

                Row(
                    modifier = Modifier.width(184.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Surface(
                        shape = RoundedCornerShape(12.dp),
                        color = LagoonBlue
                    ) {
                        Text(
                            modifier = Modifier
                                .padding(12.dp),
                            text = formatDate(spot.seasonStart),
                            fontSize = 16.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = WhiteFoam,
                            fontFamily = quicksand
                        )
                    }
                    Image(
                        painter = painterResource(id = R.drawable.arrow_right_bold),
                        contentDescription = "Right Arrow",
                        modifier = Modifier
                            .height(16.dp),
                    )
                    Surface(
                        shape = RoundedCornerShape(12.dp),
                        color = LagoonBlue
                    ) {
                        Text(
                            modifier = Modifier
                                .padding(12.dp),
                            text = formatDate(spot.seasonEnd),
                            fontSize = 16.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = WhiteFoam,
                            fontFamily = quicksand
                        )
                    }
                }
            }
        }
    }
}

// ----------- A METTRE DANS VIEWMODEL ? -----------

fun formatDate(dateString: String): String {
    return try {
        val parser = java.text.SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)
        val formatter = java.text.SimpleDateFormat("dd MMM", Locale.ENGLISH)
        val date = parser.parse(dateString)
        formatter.format(date!!)
    } catch (e: Exception) {
        dateString
    }
}