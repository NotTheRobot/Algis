package com.example.algis.data

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.algis.AlgisApplication
import com.example.algis.MainViewModel

object AppViewModelProvider {
    val Factory = viewModelFactory {
        initializer {
            MainViewModel(
                algisApplication(),
                algisApplication().database.databaseDao()
            )
        }
    }
}

fun CreationExtras.algisApplication(): AlgisApplication =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as AlgisApplication)