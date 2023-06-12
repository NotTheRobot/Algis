package com.example.algis.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface DatabaseDao  {
    @Query("SELECT * FROM account")
    fun getAccount(): AccountDatabaseModel
    @Update
    fun updateAccount(account: AccountDatabaseModel)
    @Insert
    fun insertAcc(account: AccountDatabaseModel)
}