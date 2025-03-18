package com.example.hostelhubuser.Screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.hostelhubuser.Data.HostelDetails
import com.example.hostelhubuser.Data.details


@Composable
fun Management(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            items(details) { hostel ->
                display(hostel)
                Spacer(modifier = Modifier.height(10.dp)) // Space between hostel details
                //Divider(modifier = Modifier.size(2.dp))
            }
        }

        Spacer(Modifier.height(30.dp)) // Reduced space

        TextButton(onClick = { navController.navigate(com.example.hostelhubuser.HomeScreen) }) {
            Text("Home")
        }
    }
}
@Composable
fun display(hostelDetails: HostelDetails) {
    Box(
        modifier = Modifier
            .height(160.dp)
            .width(350.dp)
            .padding(8.dp)
            .border(1.dp, Color.Gray, shape = RoundedCornerShape(8.dp))
            .background(Color.White, shape = RoundedCornerShape(8.dp))
            .padding(10.dp),
        contentAlignment = Alignment.Center
    ) {
        SelectionContainer {
            Column {
                Text("🏨 Hostel: ${hostelDetails.hostelName}", fontWeight = FontWeight.Bold)
                Text("👨‍🏫 Warden: ${hostelDetails.hostelWarden}")
                Text("📧 Email: ${hostelDetails.hostelWardenEmail}")
                Text("📞 Contact: ${hostelDetails.hostelWardenContact}")
            }
        }
    }
}
