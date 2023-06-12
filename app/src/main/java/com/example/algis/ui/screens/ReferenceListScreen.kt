package com.example.algis.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.algis.MainViewModel
import com.example.algis.ui.theme.BackgroundColor
import com.example.algis.ui.theme.PrimaryColor
import com.example.algis.ui.theme.PrimaryTextColor

@Composable
fun ReferenceListScreen(
    onOrderClick: (String) -> Unit,
    viewModel: MainViewModel
) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = BackgroundColor
    ) {
        LazyColumn(){
            items(viewModel.references){ item ->
                ReferenceItem(text = item, onOrderClick = { onOrderClick(it) })
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }

}

@Composable
fun ReferenceItem(
    text: String,
    onOrderClick: (String) -> Unit
) {
    Card(
        modifier = Modifier
            .padding(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = BackgroundColor
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 3.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(8.dp)
                .height(64.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                modifier = Modifier
                    .padding(end = 8.dp)
                    .weight(3f),
                text = text,
                fontSize = 16.sp,
                color = PrimaryTextColor
            )
            Button(
                onClick = { onOrderClick(text) },
                colors = ButtonDefaults.buttonColors(
                    containerColor = BackgroundColor,
                    contentColor = PrimaryColor
                ),
                border = BorderStroke(2.dp, PrimaryColor)
            ) {
                Text(text = "Заказать", color = PrimaryColor)
            }
        }
    }
}

@Composable
@Preview
fun ReferenceItemPreview(){
    ReferenceItem(text = "Отчет о прохождении специальной аттестации", onOrderClick = {})
}