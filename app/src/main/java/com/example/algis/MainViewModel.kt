package com.example.algis

import android.app.Application
import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.AndroidViewModel
import com.example.algis.data.database.AccountModel
import com.example.algis.data.database.DatabaseDao
import com.example.algis.data.database.toAccountDatabaseModel
import com.example.algis.data.database.toAccountModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import java.util.Date

class MainViewModel(
    app: Application,
    val dao: DatabaseDao
): AndroidViewModel(app) {

    val TAG = "viewModel"
    var messages = mutableStateListOf<Pair<Long,String>>()
    var isFirstStart = true
    var auth: FirebaseAuth = Firebase.auth
    val dbReference = Firebase.database.getReference()
    var accountItem = dao.getAccount().toAccountModel()
    val mApp = app
    val titleState = mutableStateOf("Главная")
    var isAccountFilled = mutableStateOf(accountItem.fullName.value != "Отсутствует")
    var isSignedIn = mutableStateOf(auth.currentUser != null)
    var isEdit = mutableStateOf(false)
    val references = listOf(
        "Справка о доходах 2-НДФЛ",
        "Копия приказа о приеме на работу",
        "Справка о периоде работы",
        "Отчет о прохождении специальной аттестации"
    )
    val historyReference = mutableStateListOf(
        mutableStateListOf(references[0], "07.06.2023", "Отменено", "1", "4"),
        mutableStateListOf(references[1], "04.06.2023", "В работе", "2", "3"),
        mutableStateListOf(references[2], "25.05.2023", "Получено", "2", "2"),
        mutableStateListOf(references[3], "23.05.2023", "Готово", "1", "1")
    )
    var referenceId = 4

    init{
        dbReference.child("Messages").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if(isFirstStart){
                    val temp = dataSnapshot.getValue<HashMap<String, String>>() ?: hashMapOf()
                    temp.forEach { (key, value) ->
                        messages.add(key.toLong() to value)
                    }
                    isFirstStart = false
                    messages.sortBy { it.first }
                }else{
                    val temp = dataSnapshot.getValue<HashMap<String, String>>() ?: hashMapOf()
                    val oneMore = mutableListOf<Pair<Long, String>>()
                    temp.forEach { (key, value) ->
                        oneMore.add(key.toLong() to value)
                    }
                    oneMore.sortBy { it.first }
                    messages.add(oneMore[oneMore.size - 1])
                }
            }
            override fun onCancelled(error: DatabaseError) {
                Log.w(TAG, "Failed to read value.", error.toException())
            }
        })
    }

    fun onEditClick(){
        isEdit.value = true
    }
    fun onLogOutClick(){
        isSignedIn.value = false
        Firebase.auth.signOut()
    }
    fun saveAccountData(){
        isEdit.value = false
        dao.updateAccount(
            accountItem.toAccountDatabaseModel()
        )
        isAccountFilled.value = accountItem.fullName.value != "Отсутствует"
    }
    fun getNextReferenceId(): Int {
        referenceId++
        return referenceId
    }

    fun sendMessage(message: String){
        Log.d(TAG, "sendMessage started")
        dbReference.child("Messages").child("${Date().time}").setValue(message)/*
            .addOnFailureListener{
                Log.d(TAG, it.message!!)
            }
            .addOnSuccessListener{
                Log.d(TAG, "send is successful")
            }
            .addOnCanceledListener {
                Log.d(TAG, "send canceled")
            }
            .addOnCompleteListener{
                Log.d(TAG, "send is complete")
            }*/
    }
}