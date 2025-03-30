package com.example.hostelhubuser.Screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.hostelhubuser.Data.HostelViewModel
import com.example.hostelhubuser.R


@Composable
fun complainScreen(hostelViewModel: HostelViewModel,navController: NavController) {


    var uid = hostelViewModel.getId();
    val studentData by hostelViewModel.studentData.observeAsState()
    val name = studentData?.name
    val number=studentData?.number


    var topic by remember { mutableStateOf("") }
    var desc by remember { mutableStateOf("") }

    Column (
        modifier = Modifier.fillMaxSize().padding(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ){

        Text("Speak Up", fontWeight = FontWeight.SemiBold, fontSize = 25.sp)
        Image(painter = painterResource(R.drawable.com), contentDescription = "Complaint")
        Spacer(Modifier.height(100.dp))
        OutlinedTextField(
            value = topic,
            onValueChange = {topic=it},
            label = { Text("Enter topic") }
        )

        OutlinedTextField(
            value =desc ,
            onValueChange = {desc=it},
            label = { Text("Describe it") }
        )

        Spacer(Modifier.height(20.dp))
        Button(
            onClick = {
                if (topic.isNotEmpty() && desc.isNotEmpty()) {
                    hostelViewModel.addComplain(
                        name = name.toString(),
                        number.toString(),
                        topic = topic,
                        description = desc
                    )
                    navController.navigate(com.example.hostelhubuser.HomeScreen){
                        popUpTo(0)
                    }
                }


            },
            modifier = Modifier.height(40.dp).width(150.dp),
            shape = RoundedCornerShape(1.dp)
        ) {
            Text("Submit")
        }
        Spacer(Modifier.height(10.dp))
        TextButton(
            onClick = {navController.navigateUp()},

        ) {
            Text("CANCEL")
        }

    }
}