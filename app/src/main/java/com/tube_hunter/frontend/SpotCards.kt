@file:OptIn(ExperimentalMaterial3Api::class)

package com.tube_hunter.frontend

import android.content.Intent
import android.net.Uri
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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.tube_hunter.frontend.ui.theme.DeepBlue
import com.tube_hunter.frontend.ui.theme.LagoonBlue
import com.tube_hunter.frontend.ui.theme.WhiteFoam
import com.tube_hunter.frontend.ui.theme.quicksand
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


data class SpotCards(
    val spotName: String = "",
    val location: String = "",
    val imageUri: Uri? = null,
    val difficulty: String? = null,
    val surfBreaks: List<String> = emptyList(),
    val seasonStart: Long? = null,
    val seasonEnd: Long? = null,
) {
    fun isValid(): Boolean {
        return spotName.isNotBlank()
                && location.isNotBlank()
                && difficulty != null
                && surfBreaks.isNotEmpty()
                && seasonStart != null
                && seasonEnd != null
    }
}


@Composable
fun Background() {
    var formState by remember { mutableStateOf(SpotCards()) }

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


            AddSpotCard(
                formState = formState,
                onFormChange = { formState = it }
            )

            Spacer(modifier = Modifier.weight(1f))

            val context = LocalContext.current
            Row {
                Button(
                    onClick = {
                        val intent = Intent(context, SpotsListActivity::class.java)
                        context.startActivity(intent)
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

                Button(
                    onClick = {
                        val intent = Intent(context, SpotsListActivity::class.java)
                        context.startActivity(intent)
                    },
                    enabled = formState.isValid(),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = WhiteFoam,
                        disabledContainerColor = WhiteFoam.copy(alpha = 0.5f),
                        contentColor = DeepBlue,
                        disabledContentColor = WhiteFoam
                    ),
//
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
    }
}


@Composable
fun Checkboxes(
    selected: List<String>,
    onValueChange: (List<String>) -> Unit
) {
    val labels = listOf("Beach", "Reef", "Point")

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        labels.forEach { label ->
            val checked = selected.contains(label)
            Row(verticalAlignment = Alignment.CenterVertically) {
                Checkbox(
                    checked = checked,
                    onCheckedChange = { isChecked ->
                        val newList = if (isChecked) {
                            selected + label
                        } else {
                            selected - label
                        }
                        onValueChange(newList)
                    },
                    colors = CheckboxDefaults.colors(
                        checkedColor = LagoonBlue,
                        uncheckedColor = LagoonBlue,
                        checkmarkColor = WhiteFoam
                    ),
                )
                Text(label)
            }
        }
    }
}


@Composable
fun DifficultyDropdown(
    selected: String?,
    onValueChange: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }


        Box(modifier = Modifier.padding(start = 16.dp)) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.End,
            ) {
                Text(selected ?: "Select", color = WhiteFoam)
                IconButton(onClick = { expanded = !expanded }) {
                    Icon(Icons.Default.KeyboardArrowDown, tint = WhiteFoam, contentDescription = "Difficulty")
                }
            }

            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
            ) {
                listOf("1", "2", "3", "4", "5").forEach { value ->
                    DropdownMenuItem(
                        text = { Text(value, fontSize = 14.sp, modifier = Modifier.padding(4.dp)) },
                        onClick = {
                            onValueChange(value)
                            expanded = false
                        },
                        modifier = Modifier
                            .height(32.dp)
                            .width(60.dp)
                    )
                }
            }
        }
    }
//}


@Composable
fun DatePickerModal(
    onDateSelected: (Long?) -> Unit,
    onDismiss: () -> Unit
) {
    val datePickerState = rememberDatePickerState()

    DatePickerDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(onClick = {
                onDateSelected(datePickerState.selectedDateMillis)
                onDismiss()
            }) {
                Text("OK")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    ) {
        DatePicker(state = datePickerState)
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


@Composable
fun AddSpotCard(
    formState: SpotCards,
    onFormChange: (SpotCards) -> Unit
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
//
            AddImage(
                imageUri = formState.imageUri,
                onClick = { }
            )




            Spacer(modifier = Modifier.height(8.dp))


            OutlinedTextField(
                value = formState.spotName,
                onValueChange = { onFormChange(formState.copy(spotName = it)) },
                label = { Text("Spot Name", color = WhiteFoam, fontSize = 20.sp) },
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
                )
            )

            Spacer(modifier = Modifier.height(8.dp))


            OutlinedTextField(
                value = formState.location,
                onValueChange = { onFormChange(formState.copy(location = it)) },
                label = { Text("Location", color = WhiteFoam) },
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
                    text = "SURF BREAK",
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
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(180.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(LagoonBlue)
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        if (imageUri != null) {
            Image(
                painter = rememberAsyncImagePainter(imageUri),
                contentDescription = "Selected Image",
                modifier = Modifier.fillMaxWidth(),
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
                    color = WhiteFoam,
                    fontFamily = quicksand,
                    fontSize = 16.sp
                )
            }
        }
    }
}

@Composable
fun AddUrl (URL : String) {

}

fun formatDateFromMillis(millis: Long): String {
    val date = Date(millis)
    val formatter = SimpleDateFormat("dd MMM", Locale.getDefault())
    return formatter.format(date)
}
