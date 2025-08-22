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
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tube_hunter.frontend.ui.theme.DeepBlue
import com.tube_hunter.frontend.ui.theme.LagoonBlue
import com.tube_hunter.frontend.ui.theme.WhiteFoam
import com.tube_hunter.frontend.ui.theme.quicksand


class AddSpotActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
           Background()
        }
    }
}

@Preview
@Composable
fun Background(){
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
            AddSpotCard()
            val context = LocalContext.current
            Row{
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
                    colors = ButtonDefaults.buttonColors(WhiteFoam, DeepBlue),
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
fun AddSpotCard() {
    var spotName by remember { mutableStateOf("") }
    var location by remember { mutableStateOf("") }

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
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.Start
        ) {
            Button(
                onClick = {},
                colors = ButtonDefaults.buttonColors(LagoonBlue, WhiteFoam),

            ) {
                Text(
                    text = "Add Image",
                    fontFamily = quicksand,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = spotName,
                onValueChange = { spotName = it },
                label = { Text("Spot Name", color = WhiteFoam) },
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

            // TextField pour la localisation
            OutlinedTextField(
                value = location,
                onValueChange = { location = it },
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

            Spacer(modifier = Modifier.height(48.dp))

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
                Surface (
                    shape = RoundedCornerShape(12.dp),
                    color = LagoonBlue
                ){
                    DropdownMenu()
                }
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
                    text = "SurfBreak",
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
                            text = "start",
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
                            text = "end",
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

@Composable
fun DropdownMenu() {
    var expanded by remember { mutableStateOf(false) }
    var choice by remember { mutableStateOf("Select") }
    Box(
        modifier = Modifier
            .padding(start = 16.dp)
    ) {
        Row (
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.End,
        ){
            Text(choice, color = WhiteFoam)
            IconButton(onClick = { expanded = !expanded }) {
                Icon(Icons.Default.KeyboardArrowDown, tint = WhiteFoam, contentDescription = "Difficulty")
            }
        }
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            DropdownMenuItem(
                text = { Text("1")  },
                onClick = {
                    choice = "1"
                    expanded = false }
            )
            DropdownMenuItem(
                text = { Text("2") },
                onClick = {
                    choice = "2"
                    expanded = false }
            )
            DropdownMenuItem(
                text = { Text("3") },
                onClick = {
                    choice = "3"
                    expanded = false }
            )
            DropdownMenuItem(
                text = { Text("4") },
                onClick = {
                    choice = "4"
                    expanded = false }
            )
            DropdownMenuItem(
                text = { Text("5") },
                onClick = {
                    choice = "5"
                    expanded = false }
            )
        }
    }
}


