package com.example.algis.ui.screens

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.algis.MainViewModel
import com.example.algis.ui.theme.BackgroundColor
import com.example.algis.ui.theme.PrimaryColor

val TAG = "authorization"

@Composable
fun AuthenticationScreen(
    viewModel: MainViewModel,
    activityContext: ComponentActivity
) {
    val mContext = LocalContext.current
    var isLogging by remember{
        mutableStateOf(true)
    }
    var email by remember {
        mutableStateOf("")
    }
    var password by remember {
        mutableStateOf("")
    }
    
    Surface(color = BackgroundColor) {
        Column(
            modifier = Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = if(isLogging) "Вход" else "Регистрация",
                color = PrimaryColor,
                fontSize = 24.sp,
                textAlign = TextAlign.Center
            )
            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = {
                    Text(
                        text = "E-mail"
                    )
                },
                colors = OutlinedTextFieldDefaults.colors(
                    disabledContainerColor = BackgroundColor,
                    unfocusedContainerColor = BackgroundColor,
                    focusedContainerColor = BackgroundColor,
                    disabledBorderColor = PrimaryColor,
                    focusedBorderColor = PrimaryColor,
                    unfocusedBorderColor = PrimaryColor
                )
            )
            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = {
                    Text(
                        text = "Пароль"
                    )
                },
                colors = OutlinedTextFieldDefaults.colors(
                    disabledContainerColor = BackgroundColor,
                    unfocusedContainerColor = BackgroundColor,
                    focusedContainerColor = BackgroundColor,
                    disabledBorderColor = PrimaryColor,
                    focusedBorderColor = PrimaryColor,
                    unfocusedBorderColor = PrimaryColor
                )
            )
            Text(
                modifier = Modifier
                    .padding(16.dp)
                    .clickable {
                    isLogging = !isLogging
                },
                text = if(isLogging) "Регистрация" else "Вход",
                fontSize = 12.sp,
                color = Color(0xFF3366BB)
            )
            Button(
                colors = ButtonDefaults.outlinedButtonColors(
                    containerColor = PrimaryColor,
                ),
                onClick = {
                    if(isLogging){
                        viewModel.auth.signInWithEmailAndPassword(email, password)
                            .addOnCompleteListener(activityContext) { task ->
                                if (task.isSuccessful) {
                                    Log.d(TAG, "signInWithEmail:success")
                                    viewModel.isSignedIn.value = true
                                } else {
                                    // If sign in fails, display a message to the user.
                                    Log.w(TAG, "signInWithEmail:failure", task.exception)
                                    Toast.makeText(
                                        activityContext.baseContext,
                                        "Authentication failed.",
                                        Toast.LENGTH_SHORT,
                                    ).show()
                                }
                            }
                    }else{
                        viewModel.auth.createUserWithEmailAndPassword(email, password)
                            .addOnCompleteListener(activityContext) { task ->
                                if (task.isSuccessful) {
                                    // Sign in success, update UI with the signed-in user's information
                                    Log.d(TAG, "createUserWithEmail:success")
                                    isLogging = true
                                } else {
                                    // If sign in fails, display a message to the user.
                                    Log.w(TAG, "createUserWithEmail:failure", task.exception)
                                    Toast.makeText(
                                        activityContext.baseContext,
                                        "Authentication failed.",
                                        Toast.LENGTH_SHORT,
                                    ).show()
                                }
                            }
                    }
                }
            ) {
                Text(text = if(isLogging)"Вход" else "Регистрация", color = BackgroundColor, fontSize = 24.sp)
            }
        }
    }
}