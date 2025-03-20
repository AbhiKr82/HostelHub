package com.example.hostelhubuser.Screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.hostelhubuser.Data.HostelViewModel
import com.example.hostelhubuser.precomplain


@Composable
fun complainpage (navController: NavController,hostelViewModel: HostelViewModel){
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Button(onClick = {
            navController.navigate(com.example.hostelhubuser.complainScreen)
        }) {
            Text("File a Complain")
        }
        Button(onClick = {
            navController.navigate(precomplain)
            // new code
            hostelViewModel.getComplain()

        }) {
            Text("View Your Previous Complain")
        }

        TextButton(onClick = {navController.navigateUp()}) {
            Text("BACK")
        }

    }
}