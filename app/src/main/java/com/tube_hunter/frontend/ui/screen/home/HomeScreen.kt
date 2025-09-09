package com.tube_hunter.frontend.ui.screen.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tube_hunter.frontend.R
import com.tube_hunter.frontend.ui.navigation.Screen
import com.tube_hunter.frontend.ui.theme.DeepBlue
import com.tube_hunter.frontend.ui.theme.WhiteFoam
import com.tube_hunter.frontend.ui.theme.chewy
import kotlinx.coroutines.delay

@Composable
fun HomeScreen(onNavigate: (String) -> Unit) {
    LaunchedEffect(Unit) {
        delay(3000)
        onNavigate(Screen.SpotList.route)
    }

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
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            Image(
                painter = painterResource(id = R.drawable.logo_light_tube_hunter),
                contentDescription = "Logo",
                modifier = Modifier
                    .size(200.dp)
                    .clip(CircleShape)
                    .border(width = 1.dp, color = DeepBlue, shape = CircleShape)
            )
            HomeBrandTitle()
        }
    }
}

@Composable
fun HomeBrandTitle() {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.padding(top = 48.dp)
    ) {
        Text(
            text = "TUBE HUNTER",
            fontSize = 104.sp,
            textAlign = TextAlign.Center,
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
            fontSize = 104.sp,
            fontFamily = chewy,
            textAlign = TextAlign.Center,
            color = WhiteFoam
        )
    }
}