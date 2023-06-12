package com.example.algis.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.algis.MainViewModel
import com.example.algis.ui.theme.BackgroundColor
import com.example.algis.ui.theme.PrimaryColor
import com.example.algis.ui.theme.PrimaryTextColor
import com.example.algis.ui.theme.SecondaryTextColor

@Composable
fun ReferenceHistoryScreen(
    viewModel: MainViewModel
) {
    val scroll = rememberScrollState()
    Surface(
        color = BackgroundColor
    ) {
        LazyColumn(
            modifier = Modifier
                .padding(start = 16.dp, top = 16.dp, end = 16.dp)
                .fillMaxSize()
        ) {
            items(viewModel.historyReference) { item ->
                HistoryItem(item = item)
                Spacer(modifier = Modifier.height(24.dp))
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HistoryItem(
    item: MutableList<String>
) {
    val isDialogOpen = remember { mutableStateOf(false) }
    if(isDialogOpen.value){
        AlertDialog(
            onDismissRequest = { isDialogOpen.value = false }
        ) {
            Column(
                modifier = Modifier
                    .background(BackgroundColor)
                    .border(
                        color = BackgroundColor,
                        width = 4.dp,
                        shape = RoundedCornerShape(4.dp)
                    )) {
                Text(
                    modifier = Modifier
                        .padding(16.dp),
                    text = "Вы уверены, что хотите отменить справку?",
                    color = PrimaryTextColor,
                    fontSize = 20.sp
                )
                Row() {
                    Box(modifier = Modifier.weight(2f)){}
                    Button(
                        modifier = Modifier.padding(16.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = BackgroundColor,
                            contentColor = PrimaryTextColor
                        ),
                        onClick = {
                        item[2] = "Отменено"
                        isDialogOpen.value = false
                    }) {
                        Text(
                            fontSize = 20.sp,
                            text = "Да"
                        )
                    }
                    Button(
                        modifier = Modifier.padding(16.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = BackgroundColor,
                            contentColor = PrimaryTextColor
                        ),
                        onClick = { isDialogOpen.value = false }
                    ) {
                        Text(
                            fontSize = 20.sp,
                            text = "Нет"
                        )
                    }
                }
            }
        }
    }
    Box(modifier = Modifier.fillMaxWidth()) {
        if(item[2] != "Отменено"){
            OutlinedButton(
                modifier = Modifier
                    .align(Alignment.BottomEnd),
                onClick = {
                    isDialogOpen.value = true
                },
                border = BorderStroke(2.dp, PrimaryColor),
            ) {
                Text(
                    text = "Отменить",
                    color = PrimaryColor,
                    fontSize = 24.sp
                )
            }
        }

        Column {
            Box(modifier = Modifier
                .background(PrimaryColor)
                .fillMaxWidth()
                .height(2.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    modifier = Modifier
                        .weight(7F)
                        .padding(end = 8.dp),
                    text = item[0],
                    color = PrimaryTextColor,
                )
                Text(
                    modifier = Modifier.weight(1f),
                    text = item[3] + "шт.",
                    color = PrimaryTextColor
                )
            }
            Box(modifier = Modifier
                .background(PrimaryColor)
                .fillMaxWidth()
                .height(2.dp))
            Text(
                modifier = Modifier.padding(top = 16.dp),
                text = "Дата заказа",
                color = SecondaryTextColor
            )
            Text(
                modifier = Modifier.padding(),
                text = item[1],
                color = PrimaryTextColor
            )
            Text(
                modifier = Modifier.padding(top = 16.dp),
                text = "Статус",
                color = SecondaryTextColor
            )
            Text(
                modifier = Modifier.padding(),
                text = item[2],
                color = PrimaryTextColor
            )
        }
    }
}

@Composable
@Preview
fun HistoryItemPreview(){

}