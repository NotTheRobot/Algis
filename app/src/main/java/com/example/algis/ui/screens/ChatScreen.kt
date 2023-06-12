package com.example.algis.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.algis.MainViewModel
import com.example.algis.ui.theme.BackgroundColor
import com.example.algis.ui.theme.PurpleGrey40

@Composable
fun ChatScreen(
    viewModel: MainViewModel
) {
    var textField by remember { mutableStateOf("") }
    val lazyScroll = rememberLazyListState()
    Surface() {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(BackgroundColor)
        ) {
            LazyColumn(
                state = lazyScroll,
                modifier = Modifier.fillMaxWidth()
                    .padding(bottom = 64.dp)
            ) {
                items(viewModel.messages) { item ->
                    Card(
                        modifier = Modifier.padding(8.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = Color(0xFFBDD1E9)
                        ),
                        shape = RoundedCornerShape(4.dp)
                    ) {
                        Text(
                            modifier = Modifier.padding(8.dp),
                            text = item.second
                        )
                    }
                }
            }
            TextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomEnd),
                value = textField,
                onValueChange = { textField = it },
                trailingIcon = {
                    IconButton(onClick = {
                        viewModel.sendMessage(textField)
                        textField = ""
                    }) {
                        Icon(imageVector = Icons.Default.Send, contentDescription = null)
                    }
                }
            )
        }
    }
}