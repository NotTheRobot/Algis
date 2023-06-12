package com.example.algis.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.algis.R
import com.example.algis.ui.theme.BackgroundColor
import com.example.algis.ui.theme.PrimaryColor
import com.example.algis.ui.theme.SecondaryTextColor

@Composable
fun ReferenceManagerScreen(
    onReferenceListClick: () -> Unit,
    onHistoryClick: () -> Unit
) {
    Surface(color = BackgroundColor) {
        Image(
            modifier = Modifier.fillMaxSize(),
            painter = painterResource(id = R.drawable.background),
            contentDescription = null,
            contentScale = ContentScale.Crop)
        Column(
        ) {
            AnotherItemCard(iconId = R.drawable.audit, title = "Заказать справку", description = "Заказ справок по кадровому администрированию и оплате труда" ) {
                onReferenceListClick()
            }
            Spacer(modifier = Modifier.height(16.dp))
            AnotherItemCard(iconId = R.drawable.references_history, title = "История заявок", description = "Активные заказы на справки\nОтмена активных заказов") {
                onHistoryClick()
            }
        }
    }
}

@Composable
fun AnotherItemCard(
    iconId: Int,
    title: String,
    description: String,
    onClick: () -> Unit
){
    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
            .clickable { onClick() },
        colors = CardDefaults.cardColors(
            containerColor = BackgroundColor
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier,
            verticalAlignment = Alignment.Bottom
        ){
            Icon(
                modifier = Modifier
                    .width(72.dp)
                    .height(72.dp)
                    .padding(8.dp),
                painter = painterResource(id = iconId),
                contentDescription = null,
                tint = PrimaryColor
            )
            Text(
                modifier = Modifier
                    .padding(8.dp),
                text = title,
                fontSize = 24.sp,
                color = PrimaryColor,
                fontWeight = FontWeight.Bold
            )
        }
        Text(
            modifier = Modifier
                .padding(start = 32.dp, end = 32.dp)
                .height(98.dp),
            text = description,
            fontSize = 16.sp,
            color = SecondaryTextColor
        )
    }
}