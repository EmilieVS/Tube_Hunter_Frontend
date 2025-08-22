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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.tube_hunter.frontend.ui.theme.DeepBlue
import com.tube_hunter.frontend.ui.theme.LagoonBlue
import com.tube_hunter.frontend.ui.theme.WhiteFoam
import com.tube_hunter.frontend.ui.theme.chewy
import com.tube_hunter.frontend.ui.theme.quicksand
import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale

class SpotDetailsActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val spot = intent.getParcelableExtra<Spot>("spot")
        setContent {
            spot?.let {
                SpotDetails(it)
            }
        }
    }
}

@Composable
fun SpotDetails(spot: Spot) {
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

            SpotDetailsCard(spot)

            val context = LocalContext.current
            Button(
                onClick = {
                    val intent = Intent(context, SpotsListActivity::class.java)
                    context.startActivity(intent)
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

@Parcelize
data class Spot(
    val photoUrl: String,
    val name: String,
    val location: String,
    val difficulty: Int,
    val surfBreak: String,
    val seasonBegins: String,
    val seasonEnds: String,
) : Parcelable

@Composable
fun BrandTitle() {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.padding(top = 48.dp)
    ) {
        Text(
            text = "TUBE HUNTER",
            fontSize = 56.sp,
            fontFamily = chewy,
            color = DeepBlue,
            style = androidx.compose.ui.text.TextStyle(
                shadow = Shadow(
                    color = DeepBlue,
                    offset = Offset(0f, 0f),
                    blurRadius = 16f
                )
            )
        )
        Text(
            text = "TUBE HUNTER",
            fontSize = 56.sp,
            fontFamily = chewy,
            color = WhiteFoam
        )
    }
}

@Composable
fun SpotDetailsCard(spot: Spot) {
    Card(
        modifier = Modifier
            .padding(horizontal = 40.dp)
            .fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = WhiteFoam
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp
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
                text = spot.location,
                fontSize = 16.sp,
                fontStyle = FontStyle.Italic,
                fontFamily = quicksand,
                color = DeepBlue
            )

            Spacer(modifier = Modifier.height(48.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "DIFFICULTY",
                    fontSize = 16.sp,
                    color = DeepBlue,
                    fontWeight = FontWeight.Bold,
                    fontFamily = quicksand
                )
                Text(
                    text = "${spot.difficulty}/5",
                    fontSize = 16.sp,
                    color = DeepBlue,
                    fontFamily = quicksand
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "SURF BREAK",
                    fontSize = 16.sp,
                    color = DeepBlue,
                    fontWeight = FontWeight.Bold,
                    fontFamily = quicksand
                )
                Text(
                    text = spot.surfBreak,
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
                    modifier = Modifier.width(170.dp),
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
                            text = formatDate(spot.seasonBegins),
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
                            text = formatDate(spot.seasonEnds),
                            fontSize = 16.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = WhiteFoam,
                            fontFamily = quicksand
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

fun formatDate(dateString: String): String {
    return try {
        val parser = DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.ENGLISH)
        val formatter = DateTimeFormatter.ofPattern("dd MMM", Locale.ENGLISH)
        LocalDate.parse(dateString, parser).format(formatter)
    } catch (e: Exception) {
        dateString
    }
}