package com.tube_hunter.frontend

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp

@Composable
fun IconDifficulty(rating: Int) {
    Row {
        repeat(rating) {
            Image(
                painter = painterResource(id = R.drawable.skull_fill),
                contentDescription = null,
                modifier = Modifier.size(width = 18.dp, height = 18.dp)
            )
        }

        repeat(5 - rating) {
            Image(
                painter = painterResource(id = R.drawable.skull_bold),
                contentDescription = null,
                modifier = Modifier.size(width = 18.dp, height = 18.dp)
            )
        }
    }
}