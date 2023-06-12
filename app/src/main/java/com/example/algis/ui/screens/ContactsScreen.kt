package com.example.algis.ui.screens

import android.content.Intent
import android.net.Uri
import android.util.Log
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.rememberNavController
import com.example.algis.MainViewModel
import com.example.algis.R
import com.example.algis.ui.theme.BackgroundColor
import com.example.algis.ui.theme.PrimaryColor
import com.example.algis.ui.theme.SecondaryTextColor
import java.util.Formatter.BigDecimalLayoutForm

@Composable
fun ContactsScreen(
    viewModel: MainViewModel
) {
    val items = listOf(
        listOf("Служба поддержки", "Круглосуточно", true, true, "tng-algis@mail.ru", "+79045568456"),
        listOf("Директор Мухамадиев Рустем Рамилевич", "Пн-Пт 9:00-17:00", true, false,"info@tngalgis.ru", ""),
        listOf("Приемная", "Пн-Пт 7:00-16:30\nПерерыв 11:00-12:30", false, true, "", "+78553372045")
    )
    Surface(color = BackgroundColor) {
        val scroll = rememberScrollState()
        var emailFrom = ""
        if(viewModel.accountItem != null) {
            emailFrom = "${viewModel.accountItem.fullName.value} ${viewModel.accountItem.jobTitle.value}"
        }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .border(2.dp, PrimaryColor)
                .verticalScroll(scroll)
        ) {
            items.forEach{ item ->
                ContactItem(modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
                    title = item[0].toString(),
                    timing = item[1].toString(),
                    isMailActive = item[2] as Boolean,
                    isPhoneActive = item[3] as Boolean,
                    mail = item[4] as String,
                    phone = item[5] as String,
                    emailFrom = emailFrom
                )
            }
        }
    }
}

@Composable
fun ContactItem(
    modifier: Modifier,
    title: String,
    timing: String,
    isMailActive: Boolean,
    isPhoneActive: Boolean,
    mail: String,
    phone: String,
    emailFrom: String
) {
    val mContext = LocalContext.current
    Log.d("checkInput", "mail:$mail phone:$phone")
    Column(modifier = modifier) {
        Text(
            modifier = Modifier,
            text = title,
            color = PrimaryColor,
            fontSize = 16.sp
        )
        Spacer(modifier = Modifier.height(8.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                modifier = Modifier
                    .weight(4f),
                text = timing,
                color = SecondaryTextColor
            )
            IconButton(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                onClick = {
                    val addresses = arrayOf(mail)
                    val intent = Intent(Intent.ACTION_VIEW).apply {
                        data = Uri.parse("mailto:")
                        putExtra(Intent.EXTRA_EMAIL, addresses)
                        putExtra(Intent.EXTRA_SUBJECT, emailFrom)
                    }
                    mContext.startActivity(Intent.createChooser(intent, "Отправка письма"))
                }
            ) {
                if(isMailActive){
                    Icon(
                        modifier = Modifier.padding(4.dp),
                        painter = painterResource(id = R.drawable.email),
                        tint = PrimaryColor,
                        contentDescription = null
                    )
                }
            }
            IconButton(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                onClick = {
                    val intent = Intent(Intent.ACTION_DIAL).apply{
                        data = Uri.parse("tel:$phone")
                    }
                    mContext.startActivity(intent)
                }
            ) {
                if(isPhoneActive){
                    Icon(
                        modifier = Modifier.padding(4.dp),
                        painter = painterResource(id = R.drawable.telephone),
                        tint = PrimaryColor,
                        contentDescription = null
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun ContactItemPreview() {
    ContactItem(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth(), title = "Центр поддержки",
        timing = "Пн-Пт 7:00-16:30\nПерерыв 11:00-12:30",
        isMailActive = true,
        isPhoneActive = true,
        mail = "",
        phone = "",
        emailFrom = ""
    )
}