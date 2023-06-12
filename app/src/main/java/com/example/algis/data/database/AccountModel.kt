package com.example.algis.data.database

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asAndroidBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.toPixelMap
import androidx.room.ColumnInfo
import androidx.room.Entity
import java.io.ByteArrayOutputStream
import java.util.Date


data class AccountModel(
    var fullName: MutableState<String> = mutableStateOf("Отсутствует"),
    var jobPlace : MutableState<String> = mutableStateOf("Отсутствует"),
    var jobTitle : MutableState<String> = mutableStateOf("Отсутствует"),
    var divisionCode : MutableState<String> = mutableStateOf("Отсутствует"),
    var phone : MutableState<String> = mutableStateOf("Отсутствует"),
    var bossFullName : MutableState<String> = mutableStateOf("Отсутствует"),
    var INN : MutableState<String> = mutableStateOf("Отсутствует"),
    var SNILS : MutableState<String> = mutableStateOf("Отсутствует"),
    var personalNumber : MutableState<String> = mutableStateOf("Отсутствует"),
    var birthDate: MutableState<String> = mutableStateOf("Отсутствует"),
    var photo: MutableState<ImageBitmap> = mutableStateOf(ImageBitmap(1,1))
)

fun AccountModel.getPersonalDataListWithName(): List<List<MutableState<String>>> {
    return listOf(
        listOf(mutableStateOf("ФИО"), this.fullName),
        listOf(mutableStateOf("Место работы"), this.jobPlace),
        listOf(mutableStateOf("Должность"), this.jobTitle),
        listOf(mutableStateOf("Код подразделения"), this.divisionCode),
        listOf(mutableStateOf("Номер телефона"), this.phone),
        listOf(mutableStateOf("Руководитель"), this.bossFullName),
        listOf(mutableStateOf("ИНН"), this.INN),
        listOf(mutableStateOf("СНИЛС"), this.SNILS),
        listOf(mutableStateOf("Табельный номер"), this.personalNumber),
        listOf(mutableStateOf("Дата рождения"), this.birthDate)
    )
}
fun AccountDatabaseModel.toAccountModel(): AccountModel{
    return AccountModel(
        fullName = mutableStateOf(this.fullName),
        jobPlace = mutableStateOf(this.jobPlace),
        jobTitle = mutableStateOf(this.jobTitle),
        divisionCode = mutableStateOf(this.divisionCode),
        phone = mutableStateOf(this.phone),
        bossFullName = mutableStateOf(this.bossFullName),
        INN = mutableStateOf(this.INN),
        SNILS = mutableStateOf(this.SNILS),
        personalNumber = mutableStateOf(this.personalNumber),
        birthDate = mutableStateOf(this.birthDate),
        photo = mutableStateOf(BitmapFactory.decodeByteArray(this.photo, 0, this.photo.size).asImageBitmap())
    )
}
fun AccountModel.toAccountDatabaseModel(): AccountDatabaseModel{
    val stream = ByteArrayOutputStream()
    this.photo.value.asAndroidBitmap().compress(Bitmap.CompressFormat.PNG,100,stream)
    val array = stream.toByteArray()
    return AccountDatabaseModel(
        id = 1,
        fullName = (this.fullName.value),
        jobPlace = (this.jobPlace.value),
        jobTitle = (this.jobTitle.value),
        divisionCode = (this.divisionCode.value),
        phone = (this.phone.value),
        bossFullName = (this.bossFullName.value),
        INN = (this.INN.value),
        SNILS = (this.SNILS.value),
        personalNumber = (this.personalNumber.value),
        birthDate = (this.birthDate.value),
        photo = array
    )
}

fun AccountModel.toMessage(): String {
    return  """
            "ФИО" ${this.fullName.value}
            "Место работы" ${this.jobPlace.value}
            "Должность" ${this.jobTitle.value}
            "Код подразделения" ${this.divisionCode.value}
            "Номер телефона" ${this.phone.value}
            "Руководитель" ${this.bossFullName.value}
            "ИНН" ${this.INN.value}
            "СНИЛС" ${this.SNILS.value}
            "Табельный номер" ${this.personalNumber.value}
            "Дата рождения" ${this.birthDate.value}
            """
}