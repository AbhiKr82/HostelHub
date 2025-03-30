package com.example.hostelhubuser.Screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.hostelhubuser.Data.HostelViewModel
import com.example.hostelhubuser.Data.Notification


@Composable
fun NotificationScreen(navController: NavController,hostelViewModel: HostelViewModel) {
    val notifications by hostelViewModel.notifications.observeAsState(emptyList())

    // Fetch notifications when the screen loads
    LaunchedEffect(Unit) {
        hostelViewModel.getNotifications()
    }

    Scaffold(
        topBar = {
            Row(
                modifier = Modifier.fillMaxWidth().padding(top=60.dp),

                ) {
                Spacer(Modifier.width(15.dp))
                Icon(
                    imageVector = Icons.Default.ArrowBack, contentDescription = "Back",
                    modifier = Modifier.clickable {navController.navigateUp()  }
                        .size(30.dp)
                )
                Spacer(Modifier.width(35.dp))
                Text("Announcements", fontSize = 25.sp, fontWeight = FontWeight.SemiBold)

            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
        ) {
            if (notifications.isEmpty()) {
                Text(
                    text = "No notifications available",
                    modifier = Modifier.fillMaxSize(),
                    textAlign = TextAlign.Center,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium
                )
            } else {
                LazyColumn {
                    items(notifications.reversed()) { notification ->
                        NotificationItem(notification)
                    }
                }
            }
        }
    }
}

@Composable
fun NotificationItem(notification: Notification) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),

    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(text = notification.message, fontWeight = FontWeight.Bold, fontSize = 18.sp)
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = "Hostel: ${notification.hostels}", fontSize = 14.sp, color = Color.Gray)
            Text(text = "Date: ${notification.date}", fontSize = 14.sp, color = Color.Gray)
        }
    }
}


