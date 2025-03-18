package com.example.hostelhubuser.Screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.hostelhubuser.Data.HostelViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RoomAllocationScreen(
    hostelViewModel: HostelViewModel, navController: NavController
) {

    var expand1 by remember { mutableStateOf(false) }
    var hostelName by remember { mutableStateOf("Select Hostel") }

    var expand2 by remember { mutableStateOf(false) }
    var floor by remember { mutableStateOf("Select Floor") }


    var result by remember { mutableStateOf("Allocated") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {Text("Get Your Room")},


            )
        }
    ) {innerPadding ->

        Column(
            modifier = Modifier.fillMaxSize().padding(innerPadding).padding(10.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            ) {

            Spacer(Modifier.height(100.dp))

            Box(
                modifier = Modifier.height(50.dp).width(250.dp)
                    .background(color = Color.LightGray, shape = RoundedCornerShape(2.dp))
                    .clickable { expand1=true },
                contentAlignment = Alignment.Center

            ) {
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.padding(10.dp)
                ) {
                    Icon(Icons.Default.ArrowDropDown,
                        contentDescription = "DropDown",
                        modifier = Modifier.size(40.dp)
                    )
                    Text(text = hostelName, fontSize = 25.sp, fontWeight = FontWeight.SemiBold)
                    DropdownMenu(
                        expanded = expand1,
                        onDismissRequest = {expand1=false}
                    ){
                        DropdownMenuItem(
                            onClick = {
                                hostelName="BOYS"
                                expand1=false
                            },
                            text = { Text("BOYS") }
                        )
                        DropdownMenuItem(
                            onClick = {
                                hostelName="GIRLS"
                                expand1=false
                            },
                            text = { Text("GIRLS") }
                        )
                    }
                }
            }

            Spacer(Modifier.height(20.dp))
            Box(
                modifier = Modifier.height(50.dp).width(250.dp)
                    .background(color = Color.LightGray, shape = RoundedCornerShape(2.dp))
                    .clickable { expand2=true },
                contentAlignment = Alignment.Center

            ) {
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.padding(10.dp)
                ) {
                    Icon(Icons.Default.ArrowDropDown,
                        contentDescription = "DropDown",
                        modifier = Modifier.size(40.dp)
                    )
                    Text(text = floor, fontSize = 25.sp, fontWeight = FontWeight.SemiBold)
                    DropdownMenu(
                        expanded = expand2,
                        onDismissRequest = {expand2=false}
                    ){
                        DropdownMenuItem(
                            onClick = {
                                floor="0"
                                expand2=false
                            },
                            text = { Text("0") }
                        )
                        DropdownMenuItem(
                            onClick = {
                                floor="1"
                                expand2=false
                            },
                            text = { Text("1") }
                        )
                        DropdownMenuItem(
                            onClick = {
                                floor="2"
                                expand2=false
                            },
                            text = { Text("2") }
                        )
                        DropdownMenuItem(
                            onClick = {
                                floor="3"
                                expand2=false
                            },
                            text = { Text("3") }
                        )
                        DropdownMenuItem(
                            onClick = {
                                floor="4"
                                expand2=false
                            },
                            text = { Text("4") }
                        )
                        DropdownMenuItem(
                            onClick = {
                                floor="5"
                                expand2=false
                            },
                            text = { Text("5") }
                        )
                    }
                }
            }

            Spacer(Modifier.height(20.dp))

            Spacer(Modifier.height(30.dp))
            Button(
                onClick = {result="Not found"},
                modifier = Modifier.height(40.dp).width(250.dp),
                shape = RoundedCornerShape(2.dp)
            ) {
                Text("SEARCH")
            }

            Spacer(Modifier.height(20.dp))
            Text(text = "Result --> ${result}")

            Spacer(Modifier.height(20.dp))
            TextButton(
                onClick = {navController.navigate(com.example.hostelhubuser.HomeScreen)}
            ) {
                Text("Home")
            }
        }
    }
}