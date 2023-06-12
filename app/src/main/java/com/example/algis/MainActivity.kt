package com.example.algis

import android.content.Intent
import android.content.IntentSender
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.algis.data.AppViewModelProvider
import com.example.algis.ui.screens.AuthenticationScreen
import com.example.algis.ui.theme.AlgisTheme
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.Identity
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.CommonStatusCodes
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase

class MainActivity : ComponentActivity() {



    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FirebaseApp.initializeApp(this)
            viewModel = viewModel(factory = AppViewModelProvider.Factory)
            if (viewModel.isSignedIn.value) {
                AlgisTheme {
                    AlgisApp(viewModel = viewModel)
                }
            } else {
                AlgisTheme() {
                    AuthenticationScreen(viewModel = viewModel, activityContext = this)
                }
            }

        }
    }
}
