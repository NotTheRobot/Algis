package com.example.algis.ui.screens

import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.preference.PreferenceActivity
import android.provider.MediaStore
import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DisplayMode
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.algis.MainViewModel
import com.example.algis.data.database.getPersonalDataListWithName
import com.example.algis.ui.theme.BackgroundColor
import com.example.algis.ui.theme.PrimaryColor
import com.example.algis.ui.theme.PrimaryTextColor
import com.example.algis.ui.theme.SecondaryTextColor
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AccountScreen(
    viewModel: MainViewModel
) {
    Log.d("AccountScrren", "rewritten")
    var isEdit by viewModel.isEdit
    val scroll = rememberScrollState()
    var imageUri by remember {
        mutableStateOf<Uri?>(null)
    }
    val mContext = LocalContext.current
    var accountItem = viewModel.accountItem
    var bitmap = remember { mutableStateOf<Bitmap?>(null) }
    val launcher = rememberLauncherForActivityResult(
        contract =
        ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        if (uri != null) {
            imageUri = uri
            imageUri?.let {
                if (Build.VERSION.SDK_INT < 28) {
                    bitmap.value = MediaStore.Images
                        .Media.getBitmap(mContext.contentResolver, it)
                } else {
                    val source = ImageDecoder
                        .createSource(mContext.contentResolver, it)
                    bitmap.value = ImageDecoder.decodeBitmap(source)
                }
                val width = bitmap.value!!.width
                val height = bitmap.value!!.height
                if(height >= width){
                    val ratio = bitmap.value!!.height.toDouble() / bitmap.value!!.width.toDouble()
                    accountItem.photo.value = Bitmap.createScaledBitmap(bitmap.value!!, 200, (200 * ratio).toInt(), true).asImageBitmap()
                }
                else{
                    val ratio = bitmap.value!!.width.toDouble() / bitmap.value!!.height.toDouble()
                    accountItem.photo.value = Bitmap.createScaledBitmap(bitmap.value!!, (200 * ratio).toInt(), 200 , true).asImageBitmap()
                }

            }
        }
    }
    var datePickerState = rememberDatePickerState(initialDisplayMode = DisplayMode.Input)
    var isDatePickerActive by remember { mutableStateOf(false) }
    val sdf = SimpleDateFormat("dd.MM.yyyy", Locale.GERMAN)

    Surface(color = BackgroundColor) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 16.dp, end = 16.dp)
                .background(color = BackgroundColor)
                .verticalScroll(scroll)
        ) {

            Header(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally),
                photo = accountItem.photo.value,
                name = accountItem.fullName.value,
                date = accountItem.birthDate.value,
                isEdit = isEdit,
                onChosePhotoCLick = { launcher.launch(it) }
            )
            Row() {
                Text(
                    modifier = Modifier.padding(top = 16.dp),
                    text = "Персональные данные",
                    fontSize = 20.sp,
                    color = PrimaryTextColor
                )

            }

            Spacer(modifier = Modifier.height(8.dp))
            accountItem.getPersonalDataListWithName().forEach {
                if(it[0].value != "Дата рождения"){
                    InfoItem(
                        modifier = Modifier.padding(top = 8.dp, bottom = 8.dp),
                        title = it[0].value,
                        text = it[1],
                        isEdit = isEdit
                    )
                }


            }
            TextField(
                modifier = Modifier.clickable {
                    if(isEdit){
                        isDatePickerActive = true
                    }
                },
                value = accountItem.birthDate.value,
                onValueChange = { accountItem.birthDate.value = it },
                colors = TextFieldDefaults.colors(
                    focusedTextColor = PrimaryTextColor,
                    disabledTextColor = PrimaryTextColor,
                    focusedContainerColor = BackgroundColor,
                    unfocusedContainerColor = BackgroundColor,
                    disabledContainerColor = BackgroundColor,
                    focusedIndicatorColor = PrimaryColor,
                    unfocusedIndicatorColor = PrimaryColor,
                    disabledIndicatorColor = if (isEdit) PrimaryColor else BackgroundColor,
                    focusedLabelColor = PrimaryColor
                ),
                label = { Text(text = "Дата рождения", color = SecondaryTextColor) },
                enabled = false
            )
            if (isDatePickerActive) {
                DatePickerDialog(
                    onDismissRequest = { },
                    confirmButton = {
                        Text(
                            modifier = Modifier
                                .padding(16.dp)
                                .clickable {
                                    if (datePickerState.selectedDateMillis != null) {
                                        accountItem.birthDate.value =
                                            sdf.format(Date(datePickerState.selectedDateMillis!!))
                                        isDatePickerActive = false
                                    }
                                },
                            text = "Сохранить",
                            color = PrimaryColor,
                            fontSize = 20.sp
                        )
                    },
                    colors = DatePickerDefaults.colors(
                        containerColor = BackgroundColor
                    )
                ) {
                    DatePicker(
                        modifier = Modifier.fillMaxHeight(0.75f),
                        title = {
                            Text(
                                modifier = Modifier
                                    .padding(8.dp)
                                    .fillMaxWidth(),
                                text = "Дата рождения",
                                color = PrimaryColor,
                                fontSize = 20.sp,
                                textAlign = TextAlign.Center
                            )
                        },
                        state = datePickerState,
                        showModeToggle = false,
                        headline = {
                            Text(
                                modifier = Modifier
                                    .fillMaxWidth(),
                                text = sdf.format(Date(datePickerState.selectedDateMillis ?: 0L)),
                                textAlign = TextAlign.Center
                            )
                        }
                    )
                }
            }
        }
    }
    BackHandler(isEdit) {
        isEdit = false
    }
}

@Composable
fun Header(
    modifier: Modifier,
    photo: ImageBitmap,
    name: String,
    date: String,
    isEdit: Boolean,
    onChosePhotoCLick: (String) -> Unit
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            modifier = Modifier
                .size(128.dp),
            bitmap = photo,
            contentDescription = null,
            contentScale = ContentScale.None
        )
        if (isEdit) {
            Text(
                modifier = Modifier.clickable {
                    onChosePhotoCLick("image/*")
                },
                text = "Выбрать фото",
                color = PrimaryColor,
                fontSize = 20.sp
            )
        } else {
            Column(
                modifier = Modifier
                    .padding(8.dp),
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = name,
                    color = PrimaryColor,
                    fontSize = 18.sp
                )
                Text(
                    text = date,
                    color = PrimaryTextColor
                )
            }

        }
    }
}

@Composable
fun InfoItem(
    modifier: Modifier,
    title: String,
    text: MutableState<String>,
    isEdit: Boolean
) {

    TextField(
        value = text.value,
        onValueChange = { text.value = it },
        colors = TextFieldDefaults.colors(
            focusedTextColor = PrimaryTextColor,
            disabledTextColor = PrimaryTextColor,
            focusedContainerColor = BackgroundColor,
            unfocusedContainerColor = BackgroundColor,
            disabledContainerColor = BackgroundColor,
            focusedIndicatorColor = PrimaryColor,
            unfocusedIndicatorColor = PrimaryColor,
            disabledIndicatorColor = if (isEdit) PrimaryColor else BackgroundColor,
            focusedLabelColor = PrimaryColor
        ),
        label = { Text(text = title, color = SecondaryTextColor) },
        enabled = isEdit
    )
}
/*
@Composable
@Preview
fun HeaderPreview(){
    Column() {
        Header(
            modifier = Modifier
                .align(Alignment.CenterHorizontally),
            photo = ImageBitmap.imageResource(id = R.drawable.people),
            name = "Капустин Кряк Иванович",
            date = Date(101, 1, 9),
            true,

        )
    }
}
*/
@Composable
@Preview
fun InfoItemPreview(){
    InfoItem(modifier = Modifier, title = "Должность", text = remember{mutableStateOf("Пидарас года")}, false)
}