package com.example.algis.data.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "account")
data class AccountDatabaseModel(
    @ColumnInfo(name = "id")@PrimaryKey(autoGenerate = true)var id: Int,
    @ColumnInfo(name = "fullName")var fullName: String = "Отсутствует",
    @ColumnInfo(name = "jobPlace")var jobPlace: String = "Отсутствует",
    @ColumnInfo(name = "jobTitle")var jobTitle: String = "Отсутствует",
    @ColumnInfo(name = "divisionCode")var divisionCode: String = "Отсутствует",
    @ColumnInfo(name = "phone")var phone: String = "Отсутствует",
    @ColumnInfo(name = "bossFullName")var bossFullName: String = "Отсутствует",
    @ColumnInfo(name = "INN")var INN: String = "Отсутствует",
    @ColumnInfo(name = "SNILS")var SNILS: String = "Отсутствует",
    @ColumnInfo(name = "personalNumber")var personalNumber: String = "Отсутствует",
    @ColumnInfo(name = "birthDate")var birthDate: String = "00.00.0000",
    @ColumnInfo(name = "photo", typeAffinity = ColumnInfo.BLOB)var photo: ByteArray = ByteArray(2)
)
/*
@Entity(tableName = "messages")
data class MessageModel(
    @ColumnInfo(name = "id")@PrimaryKey(autoGenerate = true)var id: Int,
    @ColumnInfo(name = "date") var date: String,
    @ColumnInfo(name = "message") var message: String
)
*/