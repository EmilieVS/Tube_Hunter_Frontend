package com.tube_hunter.frontend.ui.screen.spotlist

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.tube_hunter.frontend.R
import com.tube_hunter.frontend.ui.component.BrandTitle
import com.tube_hunter.frontend.ui.component.SpotDetailsUi
import com.tube_hunter.frontend.ui.navigation.Screen
import com.tube_hunter.frontend.ui.theme.DeepBlue
import com.tube_hunter.frontend.ui.theme.LagoonBlue
import com.tube_hunter.frontend.ui.theme.WhiteFoam
import com.tube_hunter.frontend.ui.theme.quicksand
import kotlinx.coroutines.launch

@Composable
fun SpotListScreen(onNavigate: (String) -> Unit, snackbarMessage: String = "", viewModel: SpotListViewModel = viewModel()) {
    val spots by viewModel.spots.collectAsState()

    val selectedDifficulty by viewModel.selectedDifficulty.collectAsState(initial = null)
    val selectedSurfBreak by viewModel.selectedSurfBreak.collectAsState(initial = null)
    val filteredSpots = remember(spots, selectedDifficulty, selectedSurfBreak) {
        spots.filter { spot ->
            val difficultyMatch = selectedDifficulty == null || spot.difficulty == selectedDifficulty
            val surfBreakMatch = selectedSurfBreak.isNullOrBlank() || spot.surfBreaks.contains(selectedSurfBreak ?: "", ignoreCase = true)
            difficultyMatch && surfBreakMatch
        }
    }

    var showFilterDialog by remember { mutableStateOf(false) }
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(snackbarMessage) {
        if (snackbarMessage.isNotBlank()) {
            coroutineScope.launch {
                snackbarHostState.showSnackbar(snackbarMessage)
                onNavigate(Screen.SpotList.route)
            }
        }
    }

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
            BrandTitle()

            Spacer(modifier = Modifier.weight(1f))

            if (filteredSpots.isEmpty()) {
                Box(
                    modifier = Modifier
                        .background(color = WhiteFoam, shape = RoundedCornerShape(12.dp))
                        .padding(16.dp)
                        .fillMaxWidth(0.8f),
                    contentAlignment = Alignment.Center
                ){
                    Text(text = "No result",
                        color = DeepBlue,
                        fontFamily = quicksand,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold)
                }
            }

            SpotList(filteredSpots, onNavigate)

            Spacer(modifier = Modifier.weight(1f))

            Row {
                Button(
                    onClick = { showFilterDialog = true },
                    colors = ButtonDefaults.buttonColors(WhiteFoam, DeepBlue),
                    modifier = Modifier
                        .padding(bottom = 48.dp)
                        .defaultMinSize(120.dp)
                ) {
                    Text(
                        text = "Filters",
                        fontFamily = quicksand,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                if (showFilterDialog) {
                    FilterDialog(
                        selectedDifficulty = selectedDifficulty,
                        selectedSurfBreak = selectedSurfBreak,
                        onDifficultyChange = { viewModel.setDifficulty(it) },
                        onSurfBreakChange = { viewModel.setSurfBreak(it) },
                        onDismiss = { showFilterDialog = false },
                        onConfirm = { difficulty, surfBreak ->
                            viewModel.setDifficulty(difficulty)
                            viewModel.setSurfBreak(surfBreak)
                            showFilterDialog = false
                        },
                        onClear = {
                            viewModel.clearFilters()
                            showFilterDialog = false
                        }
                    )
                }

                Spacer(modifier = Modifier.width(16.dp))

                Button(
                    onClick = {
                        onNavigate(Screen.NewSpot.route)
                    },
                    colors = ButtonDefaults.buttonColors(WhiteFoam, DeepBlue),
                    modifier = Modifier
                        .padding(bottom = 48.dp)
                        .defaultMinSize(120.dp)
                ) {
                    Text(
                        text = "Add spot",
                        fontFamily = quicksand,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }

        SnackbarHost(
            hostState = snackbarHostState,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(16.dp)
        )
    }
}

@Composable
fun SpotList(spots: List<SpotDetailsUi>, onNavigate: (String) -> Unit) {
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.80f),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(spots) { spot ->
            SpotCard(spot) {
                onNavigate(Screen.SpotDetails.createRoute(spot.id))
            }
        }
    }
}

@Composable
fun SpotCard(spot: SpotDetailsUi, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp)
            .clickable {
                onClick()
            },
        colors = CardDefaults.cardColors(
            containerColor = WhiteFoam,
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
                    text = "${spot.city}, ${spot.country}",
                    fontFamily = quicksand,
                    fontStyle = FontStyle.Italic,
                    fontSize = 16.sp,
                    color = DeepBlue,
                )
                Row {
                    IconDifficulty(spot.difficulty)
                }
            }
        }
    }
}

@Composable
fun FilterDialog(
    selectedDifficulty: Int?,
    selectedSurfBreak: String?,
    onDifficultyChange: (Int?) -> Unit,
    onSurfBreakChange: (String?) -> Unit,
    onDismiss: () -> Unit,
    onConfirm: (Int?, String?) -> Unit,
    onClear: () -> Unit
) {
    AlertDialog(
        containerColor = WhiteFoam,
        textContentColor = DeepBlue,
        titleContentColor = DeepBlue,
        onDismissRequest = onDismiss,
        title = { Text("Filter Spots") },
        text = {
            Column {
                Text("Difficulty")
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    (1..5).forEach { level ->
                        FilterChip(
                            selected = selectedDifficulty == level,
                            onClick = {
                                if (selectedDifficulty == level) onDifficultyChange(null)
                                else onDifficultyChange(level)
                            },
                            label = { Text(level.toString()) },
                            colors = FilterChipDefaults.filterChipColors(
                                selectedContainerColor = LagoonBlue,
                                selectedLabelColor = WhiteFoam
                            )
                        )
                    }
                }

                Spacer(Modifier.height(16.dp))

                Text("Surf Break")
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    listOf("Point", "Beach", "Reef").forEach { type ->
                        FilterChip(
                            selected = selectedSurfBreak == type,
                            onClick = {
                                if (selectedSurfBreak == type) onSurfBreakChange(null)
                                else onSurfBreakChange(type)
                            },
                            label = { Text(type) },
                            colors = FilterChipDefaults.filterChipColors(
                                selectedContainerColor = LagoonBlue,
                                selectedLabelColor = WhiteFoam
                            )
                        )
                    }
                }
            }
        },
        confirmButton = {
            Button(
                onClick = { onConfirm(selectedDifficulty, selectedSurfBreak) },
                colors = ButtonDefaults.buttonColors(LagoonBlue, WhiteFoam)
            ) {
                Text("Confirm")
            }
        },
        dismissButton = {
            Button(
                onClick = { onClear() },
                colors = ButtonDefaults.buttonColors(LagoonBlue, WhiteFoam)
            ) {
                Text("Clear")
            }
        }
    )
}



@Composable
fun IconDifficulty(rating: Int) {
    Row {
        repeat(rating) {
            Icon(
                painter = painterResource(id = R.drawable.skull_fill),
                contentDescription = null,
                modifier = Modifier.size(18.dp),
                tint = Color(0xFF07373D)
            )
        }

        repeat(5 - rating) {
            Icon(
                painter = painterResource(id = R.drawable.skull_bold),
                contentDescription = null,
                modifier = Modifier.size(18.dp),
                tint = Color(0xFF07373D)
            )
        }
    }
}

