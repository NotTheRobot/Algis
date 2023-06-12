package com.example.algis

import android.app.Application
import com.example.algis.data.database.MainDatabase


class AlgisApplication: Application() {
    val database: MainDatabase by lazy { MainDatabase.getDatabase(this)}
}