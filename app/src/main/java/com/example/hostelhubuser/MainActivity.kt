package com.example.hostelhubuser

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.hostelhubuser.Data.HostelViewModel
import com.example.hostelhubuser.Screens.HomeScreen
import com.example.hostelhubuser.Screens.LoginScreen
import com.example.hostelhubuser.Screens.Management
import com.example.hostelhubuser.Screens.ProfileScreen
import com.example.hostelhubuser.Screens.RoomAllocationScreen
import com.example.hostelhubuser.Screens.SignupScreen
import com.example.hostelhubuser.Screens.UpdateScreen
import com.example.hostelhubuser.Screens.complainScreen
import com.example.hostelhubuser.Screens.complainpage
import com.example.hostelhubuser.Screens.preCompScreen
import com.example.hostelhubuser.ui.theme.HostelHubUserTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val hostelViewModel = ViewModelProvider(this)[HostelViewModel::class.java]
        setContent {
            HostelHubUserTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Navigation(modifier = Modifier.padding(innerPadding),hostelViewModel)
                }
            }
        }
    }
}

@Composable
fun Navigation(modifier: Modifier=Modifier,hostelViewModel: HostelViewModel){

    val navController= rememberNavController()

    NavHost(navController= navController, startDestination = LoginScreen, builder =  {
        composable(LoginScreen){
           LoginScreen(navController,hostelViewModel)
        }
        composable(SignupScreen){
            SignupScreen(navController,hostelViewModel)
        }
        composable(HomeScreen){
            HomeScreen(hostelViewModel,navController)
        }
        composable(ProfileScren){
            ProfileScreen(hostelViewModel,navController)
        }
        composable(UpdateScreen){
            UpdateScreen(hostelViewModel,navController)
        }
        composable(RoomAllocation){
            RoomAllocationScreen(hostelViewModel,navController)
        }
        composable(complainScreen){
            complainScreen(hostelViewModel,navController)
        }
        composable(managementScreen){
            Management(navController)
        }
        composable(complainPage){
            complainpage(navController,hostelViewModel)
        }
        composable(precomplain){
            preCompScreen(navController,hostelViewModel)
        }

    })


}