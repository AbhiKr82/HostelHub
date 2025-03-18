package com.example.hostelhubuser.Screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
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



@Composable
fun UpdateScreen(hostelViewModel: HostelViewModel, navController: NavController) {
    val uid = hostelViewModel.getId()
    // Observe student data using LiveData in Compose
    val studentData by hostelViewModel.studentData.observeAsState()

    // Fetch user data when HomeScreen loads
    LaunchedEffect(uid) {
        if (uid != null) {
            hostelViewModel.getUserData(uid)
        }
    }

    // Create mutable state for each field to hold updated values
    var name by remember { mutableStateOf(studentData?.name ?: "") }
    var number by remember { mutableStateOf(studentData?.number ?: "") }
    var department by remember { mutableStateOf(studentData?.department ?: "") }
    var year by remember { mutableStateOf(studentData?.year ?: "") }
    var degree by remember { mutableStateOf(studentData?.degree ?: "") }
    var parentName by remember { mutableStateOf(studentData?.parentName ?: "") }
    var parentNumber by remember { mutableStateOf(studentData?.parentNumber ?: "") }
    var hostelName by remember { mutableStateOf(studentData?.hostelName ?: "") }
    var gender by remember { mutableStateOf(studentData?.gender ?: "") }
    var expand by remember { mutableStateOf(false) }
    var expandText by remember { mutableStateOf("Select Gender") }

    // Function to save updated data to Firebase
    fun saveUpdatedData() {
        // Check if all fields are non-empty
        if (name.isEmpty() || number.isEmpty() || department.isEmpty() || year.isEmpty() ||
            degree.isEmpty() || parentName.isEmpty() || parentNumber.isEmpty() ||
            hostelName.isEmpty() || gender.isEmpty()) {
            // Show a message to the user (you can use Toast or any UI component for this)
            // For now, just a simple text log
            //println("All fields must be filled.")
            return
        }

        // Create an updated student object with new values
        val updatedStudent = studentData?.copy(
            name = name,
            number = number,
            department = department,
            year = year,
            degree = degree,
            parentName = parentName,
            parentNumber = parentNumber,
            hostelName = hostelName,
            gender = gender
        )


        // Save the updated student data to Firebase
        updatedStudent?.let {
            hostelViewModel.updateStudentData(it) // Assuming this method handles the update in your ViewModel

        }
    }

    Column(modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Text(text = "Update Details", fontSize = 25.sp, fontWeight = FontWeight.SemiBold)
        Column (
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.padding(30.dp)
        ){


            // OutlinedTextFields for each student field
            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                label = { Text("Name") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = number,
                onValueChange = { number = it },
                label = { Text("Phone") },
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = department,
                onValueChange = { department = it },
                label = { Text("Department") },
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = year,
                onValueChange = { year = it },
                label = { Text("Year") },
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = degree,
                onValueChange = { degree = it },
                label = { Text("Degree") },
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = parentName,
                onValueChange = { parentName = it },
                label = { Text("Parent Name") },
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = parentNumber,
                onValueChange = { parentNumber = it },
                label = { Text("Parent Number") },
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = hostelName,
                onValueChange = { hostelName = it },
                label = { Text("Hostel Name") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(Modifier.height(15.dp))
            Box(
                modifier = Modifier.clickable { expand = true; }
                    .width(150.dp).height(40.dp)
                    .background(color = Color.LightGray, shape = RoundedCornerShape(5.dp)),
                contentAlignment = Alignment.Center
            ) {
                if (gender == "") {
                    Text(text = expandText)
                } else {
                    Text(text = gender)
                }

                DropdownMenu(
                    expanded = expand,
                    onDismissRequest = { expand = false }
                ) {
                    DropdownMenuItem(
                        text = { Text("Male") },
                        onClick = {
                            gender = "Male"
                            expandText = "Male"
                            expand = false
                        }
                    )
                    DropdownMenuItem(
                        text = { Text("Female") },
                        onClick = {
                            gender = "Female"
                            expandText = "Female"
                            expand = false
                        }
                    )
                }
            }

        }
            // Save button to update the information
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 30.dp, end = 30.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                TextButton(onClick = { navController.navigateUp() }) {
                    Text("Cancel")
                }
                TextButton(
                    onClick = {
                        saveUpdatedData()
                        navController.navigate(com.example.hostelhubuser.HomeScreen)}
                ) {
                    Text("Save Changes")
                }
            }
        }
    }
