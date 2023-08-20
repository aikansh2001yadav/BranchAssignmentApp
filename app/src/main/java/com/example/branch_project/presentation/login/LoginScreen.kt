package com.example.branch_project.presentation.login

import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.branch_project.R
import com.example.branch_project.presentation.navigation.Screen
import com.example.branch_project.ui.theme.Purple700

@Composable
fun LoginScreen(
    viewModel: LoginViewModel = hiltViewModel(),
    navController: NavHostController,
) {

    val state = viewModel.state
    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .background(Purple700),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_login),
                    contentDescription = "App Logo",
                    modifier = Modifier.size(100.dp),
                    tint = Color.White
                )
            }

            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(10.dp)
            )

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp)
            ) {
                val context = LocalContext.current
                OutlinedTextField(
                    value = viewModel.state.email,
                    label = { Text(text = "Email") },
                    onValueChange = {
                        viewModel.state = state.copy(email = it)
                    }, modifier = Modifier.fillMaxWidth()
                )

                Spacer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(4.dp)
                )

                OutlinedTextField(
                    value = viewModel.state.password,
                    label = { Text(text = "Password") },
                    onValueChange = {
                        viewModel.state = state.copy(password = it)
                    },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(10.dp)
                )

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Purple700)
                        .padding(vertical = 14.dp)
                        .clickable {
                            viewModel.login(
                                onLoginSuccess = { navController.navigate(Screen.HomeScreen.route) },
                                onError = {
                                    Log.d("BranchApp", "LoginScreen: $it")
                                    Toast
                                        .makeText(
                                            context,
                                            "Error : $it",
                                            Toast.LENGTH_SHORT
                                        )
                                        .show()
                                })
                        },
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = "Login", color = Color.White, fontSize = 18.sp)
                }

                Spacer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(10.dp)
                )

                if (viewModel.state.isLoggingIn) {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
                }
            }
        }
    }
}