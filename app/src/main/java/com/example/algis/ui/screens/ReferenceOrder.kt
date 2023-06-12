package com.example.algis.ui.screens

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.provider.MediaStore
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DateRangePicker
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDateRangePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asAndroidBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.algis.MainViewModel
import com.example.algis.data.database.getPersonalDataListWithName
import com.example.algis.data.database.toMessage
import com.example.algis.ui.theme.BackgroundColor
import com.example.algis.ui.theme.PrimaryColor
import com.example.algis.ui.theme.PrimaryTextColor
import com.example.algis.ui.theme.SecondaryTextColor
import java.io.ByteArrayOutputStream
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReferenceOrder(
    navigateUp: () -> Unit,
    refName: String,
    viewModel: MainViewModel
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundColor)
    ) {
        val state = rememberDateRangePickerState()
        var referencesCount = remember { mutableStateOf(1) }
        var emailSubject = "Запрос на справку"
        var emailText = remember { mutableStateOf( "refName\n" + viewModel.accountItem.toMessage()) }
        val sdf = SimpleDateFormat("dd.MM.yyyy", Locale.GERMAN)
        var date = remember{mutableStateOf("")}
        val isButtonEnabled by viewModel.isAccountFilled
        val mContext = LocalContext.current

        IconButton(
            modifier = Modifier
                .padding(end = 24.dp, bottom = 24.dp)
                .size(64.dp)
                .align(Alignment.BottomEnd),
            onClick = {
                val intent = Intent(Intent.ACTION_VIEW).apply {
                    data = Uri.parse("mailto:")
                    putExtra(Intent.EXTRA_EMAIL, arrayOf("tng-hr@algis.ru"))
                    putExtra(Intent.EXTRA_SUBJECT, emailSubject)
                    putExtra(Intent.EXTRA_TEXT, viewModel.accountItem.toMessage())
                    viewModel.historyReference.add(0,
                        mutableStateListOf(refName, sdf.format(Date()), "В работе", referencesCount.value.toString(), viewModel.getNextReferenceId().toString())
                    )
                }
                mContext.startActivity(intent)
            },
            colors = IconButtonDefaults.iconButtonColors(
                containerColor = PrimaryColor
            ),
            enabled = isButtonEnabled
        ) {
            Icon(
                modifier = Modifier.fillMaxSize(0.8f),
                imageVector = Icons.Default.Done,
                contentDescription = null,
                tint = BackgroundColor
            )
        }
        Column(modifier = Modifier.padding(8.dp)) {
            Spacer(modifier = Modifier.height(48.dp))
            Text(text = refName, color = PrimaryTextColor, fontSize = 20.sp)
            Spacer(modifier = Modifier.height(16.dp))
            Text(text = date.value, color = SecondaryTextColor, fontSize = 16.sp)
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(modifier = Modifier.weight(3f))
                Text(modifier = Modifier.weight(3f), text = "Количество", color = SecondaryTextColor, fontSize = 18.sp)
                IconButton(modifier = Modifier.weight(1f), onClick = { referencesCount.value-- }, colors = IconButtonDefaults.iconButtonColors(containerColor = PrimaryColor)) {
                    Icon(imageVector = Icons.Default.KeyboardArrowLeft, contentDescription = null, tint = BackgroundColor)
                }
                Text(modifier = Modifier.weight(1f), text = referencesCount.value.toString(), color = PrimaryColor, fontSize = 20.sp, textAlign = TextAlign.Center)
                IconButton(modifier = Modifier.weight(1f), onClick = { referencesCount.value++ }, colors = IconButtonDefaults.iconButtonColors(containerColor = PrimaryColor)) {
                    Icon(imageVector = Icons.Default.KeyboardArrowRight, contentDescription = null, tint = BackgroundColor)
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Внимание! после нажатия кнопки \"Отправить запрос\", вы будете перенаправлены в приложение для отправки почты. Править текст не требуется, " +
                        "он будет сгенерирован автоматически, необходимо лишь отправить письмо от имени вашего почтового ящика. \nЕщё раз проверьте свои данные." +
                        "\nСправка будет готова в течение 3 рабочих дней",
                color = SecondaryTextColor,
                letterSpacing = 0.sp,
            )
            if (refName == "Справка о доходах 2-НДФЛ" && date.value == "") {
                DatePickerDialog(
                    onDismissRequest = { navigateUp() },
                    confirmButton = {
                        Text(
                            modifier = Modifier
                                .padding(16.dp)
                                .clickable {
                                    if (state.selectedStartDateMillis != null && state.selectedEndDateMillis != null) {
                                        val startDate =
                                            sdf.format(Date(state.selectedStartDateMillis!!))
                                        val endDate =
                                            sdf.format(Date(state.selectedEndDateMillis!!))
                                        date.value = "Период: с $startDate по $endDate\n"
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
                    DateRangePicker(
                        modifier = Modifier.fillMaxHeight(0.75f),
                        title = {
                            Text(
                                modifier = Modifier
                                    .padding(8.dp)
                                    .fillMaxWidth(),
                                text = "Выберите период",
                                color = PrimaryColor,
                                fontSize = 20.sp,
                                textAlign = TextAlign.Center
                            )
                        },
                        state = state,
                        showModeToggle = true,
                        headline = {
                            Text(
                                modifier = Modifier
                                    .fillMaxWidth(),
                                text =
                                if (state.selectedStartDateMillis == null && state.selectedEndDateMillis == null) {
                                    "Начало - Конец"
                                } else if (state.selectedStartDateMillis == null) {
                                    "Начало" + " - " + sdf.format(
                                        Date(
                                            state.selectedEndDateMillis ?: 0L
                                        )
                                    )
                                } else if (state.selectedEndDateMillis == null) {
                                    sdf.format(
                                        Date(
                                            state.selectedStartDateMillis ?: 0L
                                        )
                                    ) + " - " + "Конец"
                                } else {
                                    sdf.format(
                                        Date(
                                            state.selectedStartDateMillis ?: 0L
                                        )
                                    ) + " - " + sdf.format(Date(state.selectedEndDateMillis ?: 0L))
                                },
                                textAlign = TextAlign.Center
                            )
                        }
                    )
                }
            }
        }
    }
}