package com.example.hostelhubuser.Screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.hostelhubuser.Data.HostelViewModel
import com.example.hostelhubuser.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(hostelViewModel: HostelViewModel, navController: NavController) {


    // Observe student data using LiveData in Compose
    val studentData by hostelViewModel.studentData.observeAsState()

    Scaffold (
        topBar = { TopAppBar(title = { Text("Profile") }) }
    )

    {innerPadding ->


        Column(
            modifier = Modifier.fillMaxSize().padding(innerPadding),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Column {
                studentData?.let {
                    Row(
                        modifier = Modifier.padding(start = 25.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        if (it.gender == "Male") {
                            Image(
                                painterResource(R.drawable.male1),
                                contentDescription = "Male",
                                modifier = Modifier.size(40.dp)
                            )
                        }
                        if (it.gender == "Female") {
                            Image(
                                painterResource(R.drawable.female1),
                                contentDescription = "Male",
                                modifier = Modifier.size(40.dp)
                            )
                        }
                        Spacer(Modifier.width(10.dp))
                        Text(
                            text = "${it.name}",
                            fontSize = 35.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                    }


                    Spacer(Modifier.height(50.dp))
                    Text(text = "Email: ${it.email}")
                    Text(text = "Phone: ${it.number}")
                    Text(text = "Department: ${it.department}")
                    Text(text = "Year: ${it.year}")
                    Text(text = "Degree: ${it.degree}")
                    Text(text = "Parent Name: ${it.parentName}")
                    Text(text = "Parent Number: ${it.parentNumber}")
                    Text(text = "Hostel Name: ${it.hostelName}")
                    Text(text = "Room No: ${it.roomNo}")
                    Text(text = "Gender: ${it.gender}")
                }
            }

            Spacer(Modifier.height(70.dp))
            Button(
                onClick = { navController.navigate(com.example.hostelhubuser.UpdateScreen) },
                shape = RoundedCornerShape(10.dp)
            ) {
                Text("Update Your Profile")
            }
            Spacer(Modifier.height(20.dp))
            TextButton(onClick = { navController.navigateUp() }) {
                Text("Home")
            }


        }

    }
}
