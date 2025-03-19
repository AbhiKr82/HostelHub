package com.example.hostelhubuser.Screens

import android.util.Log
import androidx.compose.foundation.Image
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Divider
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.hostelhubuser.Data.AuthState
import com.example.hostelhubuser.Data.HostelViewModel
import com.example.hostelhubuser.ProfileScren
import com.example.hostelhubuser.R
import com.example.hostelhubuser.RoomAllocation
import com.example.hostelhubuser.managementScreen
import kotlinx.coroutines.launch
import java.time.LocalTime

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    hostelViewModel: HostelViewModel,
    navController: NavController
) {

    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    val uid = hostelViewModel.userid
    // Observe student data using LiveData in Compose
    val studentData by hostelViewModel.studentData.observeAsState()

    // Fetch user data when HomeScreen loads
    LaunchedEffect(uid) {
        if (uid != null) {
            hostelViewModel.getUserData(uid)
        }
    }
    val authState = hostelViewModel.authState.observeAsState()

    LaunchedEffect(authState.value) {
        when(authState.value){
            is AuthState.UnAuthenticated -> navController.navigate(com.example.hostelhubuser.LoginScreen)
            else -> Unit
        }
    }


    val currentTime = LocalTime.now() // Get current time


    // Determine the greeting message based on the hour of the day
    val greeting = when (currentTime.hour) {
        in 5..11 -> "Good Morning"
        in 12..19 -> "Good Afternoon"
        in 20..23 -> "Good Night"
        in 0..4 -> "Soo ja maaderchod"
        else -> "Good Evening"
    }



    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet(
                modifier = Modifier.width(320.dp)
            ) {
                // Drawer Header

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // Profile Section
                    if (studentData?.gender == "Male") {
                        Image(
                            painterResource(R.drawable.male1),
                            contentDescription = "Male",
                            modifier = Modifier.size(80.dp)
                        )
                    } else if (studentData?.gender == "Female") {
                        Image(
                            painterResource(R.drawable.female1),
                            contentDescription = "Male",
                            modifier = Modifier.size(80.dp)
                        )
                    } else {
                        Icon(
                            imageVector = Icons.Default.Person,
                            contentDescription = "Profile",
                            modifier = Modifier
                                .size(80.dp)
                                .padding(bottom = 8.dp)
                        )
                    }
                }

                Divider()


                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                ) {
                    // Profile Item
                    NavigationDrawerItem(
                        icon = { Icon(Icons.Default.Person, contentDescription = "Profile") },
                        label = { Text("Profile") },
                        selected = false,
                        onClick = {
                            scope.launch { drawerState.close() }
                            navController.navigate(ProfileScren)
                        },
                        modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
                    )

                    // hostel change request
                    NavigationDrawerItem(
                        icon = {
                            Icon(
                                Icons.Default.Home,
                                contentDescription = "Hostel Change"
                            )
                        },
                        label = { Text("Hostel Room Allocation") },
                        selected = false,
                        onClick = {
                            scope.launch { drawerState.close() }
                            navController.navigate(RoomAllocation)
                        },
                        modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
                    )

                    // Room change request
                    NavigationDrawerItem(
                        icon = {
                            Icon(
                                painterResource(R.drawable.baseline_bedroom_child_24),
                                contentDescription = "Bed"
                            )
                        },
                        label = { Text("Room Change Request") },
                        selected = false,
                        onClick = {
                            scope.launch { drawerState.close() }


                        },
                        modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
                    )

                    // Sign Out
                    NavigationDrawerItem(
                        icon = {
                            Icon(
                                Icons.Default.ExitToApp,
                                contentDescription = "Sign Out"
                            )
                        },
                        label = { Text("Sign Out") },
                        selected = false,
                        onClick = {
                            scope.launch { drawerState.close() }
                            hostelViewModel.SignOut()
                        },
                        modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
                    )
                }
            }
        }
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("Home") },
                    navigationIcon = {
                        IconButton(onClick = {
                            scope.launch { drawerState.open() }
                        }) {
                            Icon(
                                imageVector = Icons.Default.Menu,
                                contentDescription = "Menu"
                            )
                        }
                    }
                )
            }
        ) { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(10.dp),
            ) {
                Text(
                    text = "$greeting,",
                    fontSize = 20.sp
                )
                Text(
                    text = "${studentData?.name.toString()} !",
                    fontSize = 30.sp,
                    fontWeight = FontWeight.SemiBold
                )


                Spacer(Modifier.height(100.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 10.dp, end = 10.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Box(
                        modifier = Modifier
                            .height(250.dp)
                            .width(180.dp)
                            .clickable {
                                navController.navigate(com.example.hostelhubuser.complainScreen)
                                studentData?.id?.let { Log.d("Login", it) }
                            }
                            .background(
                                color = Color.LightGray,
                                shape = RoundedCornerShape(10.dp)
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Column {
                            Image(
                                painter = painterResource(R.drawable.complaint),
                                contentDescription = "null", modifier = Modifier.size(100.dp)
                            )
                            Text("Post a")
                            Spacer(Modifier.height(10.dp))
                            Text(
                                "Complaint",
                                fontSize = 25.sp,
                                fontWeight = FontWeight.SemiBold
                            )
                        }
                    }
                    Spacer(Modifier.width(5.dp))
                    Box(
                        modifier = Modifier
                            .height(200.dp)
                            .width(180.dp)
                            .clickable { navController.navigate(managementScreen) }
                            .background(
                                color = Color.LightGray,
                                shape = RoundedCornerShape(10.dp)
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Column {
                            Image(
                                painter = painterResource(R.drawable.contact),
                                contentDescription = "null", modifier = Modifier.size(100.dp)
                            )
                            Text("Contact")
                            Spacer(Modifier.height(10.dp))
                            Text(
                                "Management",
                                fontSize = 20.sp,
                                fontWeight = FontWeight.SemiBold
                            )
                        }
                    }
                }
            }
        }
    }
}