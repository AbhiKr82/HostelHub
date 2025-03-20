package com.example.hostelhubuser.Screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.hostelhubuser.Data.Complain
import com.example.hostelhubuser.Data.HostelViewModel


@Composable
fun preCompScreen(navController: NavController,hostelViewModel: HostelViewModel) {
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
                Text("Previous Complain", fontSize = 25.sp, fontWeight = FontWeight.SemiBold)

            }
        }
    ) { innerPadding->

        val complaints by hostelViewModel.complaints.observeAsState(emptyList())

        LazyColumn(
            modifier = Modifier.padding(innerPadding).padding(15.dp)
        ) {
            items(complaints.reversed()) { complain ->
                ComplainItem(complain)
            }
        }

    }
}

@Composable
fun ComplainItem(complain: Complain) {
    val resolved = if (complain.resolved == true) "Issue resolved" else "Issue Not resolved"
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),

    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = "Complaint Name: ${complain.name}", fontWeight = FontWeight.Bold)
            Row {
                Text("Topic :" , fontWeight = FontWeight.SemiBold)
                Text(text = complain.topic)
            }
            Row {
                Text("Description :" , fontWeight = FontWeight.SemiBold)
                Text(text = complain.desc)
            }
            Row {
                Text("Resolved :" , fontWeight = FontWeight.SemiBold)
                Text(text = resolved)
            }

        }
    }
}