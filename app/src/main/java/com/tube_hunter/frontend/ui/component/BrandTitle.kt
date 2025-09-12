package com.tube_hunter.frontend.ui.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tube_hunter.frontend.ui.theme.DeepBlue
import com.tube_hunter.frontend.ui.theme.WhiteFoam
import com.tube_hunter.frontend.ui.theme.chewy

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
            style = TextStyle(
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