@file:OptIn(ExperimentalMaterial3Api::class)

package com.tube_hunter.frontend.ui.screen.newspot

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import com.tube_hunter.frontend.ui.navigation.Screen
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.PhotoCamera
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.lightColorScheme
import androidx.compose.material3.rememberDatePickerState
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.tube_hunter.frontend.R
import com.tube_hunter.frontend.ui.component.BrandTitle
import com.tube_hunter.frontend.ui.screen.spotlist.SpotListViewModel
import com.tube_hunter.frontend.ui.theme.DeepBlue
import com.tube_hunter.frontend.ui.theme.LagoonBlue
import com.tube_hunter.frontend.ui.theme.WhiteFoam
import com.tube_hunter.frontend.ui.theme.quicksand
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun NewSpotScreen(onNavigate: (String) -> Unit, viewModel: NewSpotViewModel = viewModel()) {
    var formState by remember { mutableStateOf(SpotFormState()) }
    val uiMessage by viewModel.uiMessage.collectAsState()
    val isSuccess by viewModel.isSuccess.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(uiMessage) {
        uiMessage?.let { msg ->
            coroutineScope.launch {
                snackbarHostState.showSnackbar(msg)
            }
        }
    }
    LaunchedEffect(isSuccess) {
        if (isSuccess) {
            onNavigate(Screen.SpotList.route + "?message=✅ Spot added successfully")
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

            NewSpotCard(
                formState = formState,
                onFormChange = { formState = it }
            )

            Spacer(modifier = Modifier.weight(1f))

            Row {
                Button(
                    onClick = {
                        onNavigate(Screen.SpotList.route)
                    },
                    colors = ButtonDefaults.buttonColors(WhiteFoam, DeepBlue),
                    modifier = Modifier.padding(bottom = 48.dp)
                ) {
                    Text(
                        text = "Cancel",
                        fontFamily = quicksand,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                Spacer(modifier = Modifier.width(16.dp))
                val context =   LocalContext.current
                Button(
                    onClick = {
                        viewModel.sendSpot(context,formState)
                    },
                    enabled = formState.isValid(),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = WhiteFoam,
                        disabledContainerColor = WhiteFoam.copy(alpha = 0.5f),
                        contentColor = DeepBlue,
                        disabledContentColor = WhiteFoam
                    ),
                    modifier = Modifier.padding(bottom = 48.dp)
                ) {
                    Text(
                        text = "Confirm",
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
fun NewSpotCard(
    formState: SpotFormState,
    onFormChange: (SpotFormState) -> Unit
) {
    Card(
        modifier = Modifier
            .padding(horizontal = 24.dp)
            .fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = WhiteFoam),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.Start
        ) {
            AddImage(
                imageUri = formState.imageUri,
                onImageChange = { onFormChange(formState.copy(imageUri = it)) }
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = formState.spotName,
                onValueChange = { onFormChange(formState.copy(spotName = it)) },
                placeholder = {
                    Text(
                        "Spot Name",
                        color = WhiteFoam,
                        fontSize = 16.sp,
                    )
                },
                modifier = Modifier.fillMaxWidth(),

                shape = RoundedCornerShape(12.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = DeepBlue,
                    unfocusedBorderColor = LagoonBlue,
                    focusedLabelColor = WhiteFoam,
                    unfocusedLabelColor = WhiteFoam,
                    focusedContainerColor = LagoonBlue,
                    unfocusedContainerColor = LagoonBlue,
                    focusedTextColor = WhiteFoam,
                    unfocusedTextColor = WhiteFoam,
                    cursorColor = DeepBlue
                )
            )

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = formState.city,
                onValueChange = { onFormChange(formState.copy(city = it)) },
                placeholder = {
                    Text(
                        "City",
                        color = WhiteFoam,
                        fontSize = 16.sp
                    )
                },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = DeepBlue,
                    unfocusedBorderColor = LagoonBlue,
                    focusedLabelColor = WhiteFoam,
                    unfocusedLabelColor = WhiteFoam,
                    focusedContainerColor = LagoonBlue,
                    unfocusedContainerColor = LagoonBlue,
                    focusedTextColor = WhiteFoam,
                    unfocusedTextColor = WhiteFoam,
                    cursorColor = DeepBlue
                )
            )

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = formState.country,
                onValueChange = { onFormChange(formState.copy(country = it)) },
                placeholder = {
                    Text(
                        "Country",
                        color = WhiteFoam,
                        fontSize = 16.sp
                    )
                },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = DeepBlue,
                    unfocusedBorderColor = LagoonBlue,
                    focusedLabelColor = WhiteFoam,
                    unfocusedLabelColor = WhiteFoam,
                    focusedContainerColor = LagoonBlue,
                    unfocusedContainerColor = LagoonBlue,
                    focusedTextColor = WhiteFoam,
                    unfocusedTextColor = WhiteFoam,
                    cursorColor = DeepBlue
                )
            )

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = "DIFFICULTY",
                    fontSize = 16.sp,
                    color = DeepBlue,
                    fontWeight = FontWeight.Bold,
                    fontFamily = quicksand
                )
                Surface(
                    shape = RoundedCornerShape(12.dp),
                    color = LagoonBlue
                ) {
                    DifficultyDropdown(
                        selected = formState.difficulty,
                        onValueChange = { onFormChange(formState.copy(difficulty = it)) }
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Column(
                modifier = Modifier.fillMaxWidth(),
            ) {
                Text(
                    text = "SURF BREAKS",
                    fontSize = 16.sp,
                    color = DeepBlue,
                    fontWeight = FontWeight.Bold,
                    fontFamily = quicksand
                )
                Checkboxes(
                    selected = formState.surfBreaks,
                    onValueChange = { onFormChange(formState.copy(surfBreaks = it)) }
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "SEASON",
                    fontSize = 16.sp,
                    color = DeepBlue,
                    fontWeight = FontWeight.Bold,
                    fontFamily = quicksand
                )
                SeasonDatePicker(
                    startDate = formState.seasonStart,
                    endDate = formState.seasonEnd,
                    onValueChange = { start, end ->
                        onFormChange(formState.copy(seasonStart = start, seasonEnd = end))
                    }
                )
            }
        }
    }
}

@Composable
fun AddImage(
    imageUri: Uri?,
    onImageChange: (Uri?) -> Unit
) {
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        onImageChange(uri)
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(180.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(LagoonBlue)
            .clickable { launcher.launch("image/*") },
        contentAlignment = Alignment.Center
    ) {
        if (imageUri != null) {
            AsyncImage(
                model = imageUri,
                contentDescription = "Spot image",
                modifier = Modifier.fillMaxSize().clip(RoundedCornerShape(8.dp)),
                contentScale = ContentScale.Crop
            )
        } else {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Icon(
                    imageVector = Icons.Default.PhotoCamera,
                    contentDescription = "Add Image",
                    tint = WhiteFoam,
                    modifier = Modifier.size(48.dp)
                )
                Text(
                    text = "Add image",
                    color = WhiteFoam
                )
            }
        }
    }
}

@Composable
fun Checkboxes(
    selected: String,
    onValueChange: (String) -> Unit
) {
    val surfBreaks = listOf("Beach", "Reef", "Point")

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        surfBreaks.forEach { surfBreak ->
            Row(verticalAlignment = Alignment.CenterVertically) {
                Checkbox(
                    checked = selected == surfBreak,
                    onCheckedChange = { isChecked ->
                        val newValue = if (isChecked) surfBreak else ""
                        onValueChange(newValue)
                    },
                    colors = CheckboxDefaults.colors(
                        checkedColor = LagoonBlue,
                        uncheckedColor = LagoonBlue,
                        checkmarkColor = WhiteFoam
                    ),
                )

                Text(
                    surfBreak,
                    fontSize = 16.sp
                )
            }
        }
    }
}


@Composable
fun DifficultyDropdown(
    selected: Int,
    onValueChange: (Int) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    Box {
        Row(
            modifier = Modifier
                .clickable { expanded = !expanded }
                .padding(horizontal = 16.dp, vertical = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Text(
                text = if (selected > 0) selected.toString() else "Select",
                color = WhiteFoam,
                fontSize = 16.sp
            )

            Icon(
                Icons.Default.KeyboardArrowDown,
                tint = WhiteFoam,
                contentDescription = "Difficulty"
            )
        }

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier.background(WhiteFoam),
            offset =  DpOffset(x = 20.dp, y = 0.dp)
        ) {
            (1..5).forEach { value ->
                DropdownMenuItem(
                    text = {
                        Box(
                            modifier = Modifier.fillMaxWidth(),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                value.toString(),
                                fontSize = 16.sp,
                                modifier = Modifier.padding(4.dp),
                            )
                        }
                    },
                    onClick = {
                        onValueChange(value)
                        expanded = false
                    },
                    modifier = Modifier
                        .height(30.dp)
                        .width(56.dp)
                )
            }
        }
    }
}

@Composable
fun DatePickerModal(onDateSelected: (Long?) -> Unit, onDismiss: () -> Unit) {
    val datePickerState = rememberDatePickerState()

    DatePickerDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(onClick = {
                onDateSelected(datePickerState.selectedDateMillis)
                onDismiss()
            }) {
                Text("OK", color = DeepBlue)
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel", color = DeepBlue)
            }
        }
    ) {
        MaterialTheme(
            colorScheme = lightColorScheme(
                primary = LagoonBlue,     // Day selected
                onPrimary = WhiteFoam,    // Text selected
                surface = WhiteFoam,      // Background modal
                onSurface = DeepBlue,     // Text modal
                onSurfaceVariant = DeepBlue
            )
        ) {
            DatePicker(state = datePickerState)
        }
    }
}

@Composable
fun SeasonDatePicker(
    startDate: Long?,
    endDate: Long?,
    onValueChange: (Long?, Long?) -> Unit
) {
    var startDatePicker by remember { mutableStateOf(false) }
    var endDatePicker by remember { mutableStateOf(false) }

    Row(
        modifier = Modifier.width(184.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Surface(
            shape = RoundedCornerShape(12.dp),
            color = LagoonBlue,
            modifier = Modifier
                .defaultMinSize(72.dp)
                .clickable { startDatePicker = true }
        ) {
            Text(
                modifier = Modifier.padding(12.dp),
                textAlign = TextAlign.Center,
                text = startDate?.let { formatDateFromMillis(it) } ?: "Start",
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
                color = WhiteFoam,
                fontFamily = quicksand,
                maxLines = 1
            )
        }

        Image(
            painter = painterResource(id = R.drawable.arrow_right_bold),
            contentDescription = "Right Arrow",
            modifier = Modifier.height(16.dp)
        )

        Surface(
            shape = RoundedCornerShape(12.dp),
            color = LagoonBlue,
            modifier = Modifier
                .defaultMinSize(72.dp)
                .clickable { endDatePicker = true }
        ) {
            Text(
                modifier = Modifier.padding(12.dp),
                textAlign = TextAlign.Center,
                text = endDate?.let { formatDateFromMillis(it) } ?: "End",
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
                color = WhiteFoam,
                fontFamily = quicksand,
                maxLines = 1
            )
        }
    }

    if (startDatePicker) {
        DatePickerModal(
            onDateSelected = {
                onValueChange(it, endDate)
                startDatePicker = false
            },
            onDismiss = { startDatePicker = false }
        )
    }

    if (endDatePicker) {
        DatePickerModal(
            onDateSelected = {
                onValueChange(startDate, it)
                endDatePicker = false
            },
            onDismiss = { endDatePicker = false }
        )
    }
}


// ----------- A METTRE DANS VIEWMODEL ? -----------

fun formatDateFromMillis(millis: Long): String {
    val date = Date(millis)
    val formatter = SimpleDateFormat("dd MMM", Locale.getDefault())
    return formatter.format(date)
}