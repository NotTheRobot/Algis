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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.algis.MainViewModel
import com.example.algis.R
import com.example.algis.ui.theme.BackgroundColor
import com.example.algis.ui.theme.PrimaryColor

@Composable
fun MainScreen(
    viewModel: MainViewModel,
    onContactsClick: () -> Unit,
    onReferencesClick: () -> Unit
) {
    Surface() {
        Image(
            modifier = Modifier.fillMaxSize(),
            painter = painterResource(id = R.drawable.background),
            contentDescription = null,
            contentScale = ContentScale.Crop)

        Column {
            ItemCard(iconId = R.drawable.people, title = "Контакты", description = "Полезные контакты", { onContactsClick() })
            Spacer(modifier = Modifier.height(16.dp))
            ItemCard(iconId = R.drawable.newspaper, title = "Справки", description = "Заказ справок и список активных заявок", { onReferencesClick() })
        }
    }
}

@Composable
fun ItemCard(
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
            fontSize = 24.sp,
            color = PrimaryColor,
            fontWeight = FontWeight.Light,
        )
    }
}
@Preview
@Composable
fun ItemCardPreview(
){
    ItemCard(iconId = R.drawable.people, title = "Справки", description = "Заказ справок и список активных заявок", onClick = {})
}
