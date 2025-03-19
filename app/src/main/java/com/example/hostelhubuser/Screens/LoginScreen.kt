package com.example.hostelhubuser.Screens

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.hostelhubuser.Data.AuthState
import com.example.hostelhubuser.Data.HostelViewModel
import com.example.hostelhubuser.R


@Composable
fun LoginScreen(navController: NavController,hostelViewModel: HostelViewModel) {
    var email by remember {
        mutableStateOf("")
    }
    var password by remember {
        mutableStateOf("")
    }

    val authState = hostelViewModel.authState.observeAsState()
    val context = LocalContext.current

    LaunchedEffect(authState.value) {
        when (authState.value) {
            is AuthState.Authenticated -> navController.navigate(com.example.hostelhubuser.HomeScreen)
            is AuthState.Error -> Toast.makeText(
                context,
                (authState.value as AuthState.Error).message, Toast.LENGTH_SHORT
            ).show()

            else -> Unit
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Image(
            painter = painterResource(R.drawable.login),
            contentDescription = "Login Background",
            contentScale = ContentScale.Fit,
            modifier = Modifier.fillMaxSize()

        )

        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(Modifier.height(400.dp))


            OutlinedTextField(
                value = email,
                onValueChange = {
                    email = it
                },
                label = { Text("Enter e-mail") }
            )
            Spacer(Modifier.height(10.dp))
            OutlinedTextField(
                value = password,
                onValueChange = {
                    password = it
                },
                label = { Text("Enter Password") }
            )

            Spacer(Modifier.height(20.dp))
            Button(
                onClick = {
                    hostelViewModel.login(email, password)
                },
                enabled = authState.value != AuthState.Loading

            ) {
                Text("Login")
            }

            Spacer(Modifier.height(20.dp))
            TextButton(onClick = {
                navController.navigate(com.example.hostelhubuser.SignupScreen)
            }) {
                Text("Don't have an account , SignUp")
            }


        }
    }
}