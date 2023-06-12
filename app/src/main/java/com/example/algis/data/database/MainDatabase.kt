package com.example.algis.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(version = 1, entities = [AccountDatabaseModel::class/*, MessageModel::class*/])
abstract class MainDatabase: RoomDatabase() {
    abstract fun databaseDao(): DatabaseDao

    companion object{
        @Volatile
        private var Instance: MainDatabase? = null

        fun getDatabase(context: Context): MainDatabase{
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(context.applicationContext, MainDatabase::class.java, "main_database.db")
                    .createFromAsset("database/main_database.db")
                    .allowMainThreadQueries()
                    .build()
                    .also { Instance = it }
            }
        }
    }
}