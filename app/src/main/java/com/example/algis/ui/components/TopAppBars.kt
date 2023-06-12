package com.example.algis.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.tooling.preview.Preview
import com.example.algis.ui.theme.BackgroundColor
import com.example.algis.ui.theme.PrimaryColor

@Composable
fun TopAppBars(
    title: String,
    onEditClick: () -> Unit,
    onLogOutClick: () -> Unit,
    onConfirmClick: () -> Unit,
) {
    if(title == "Личный кабинет") {
        AccountAppBar(
            title = title,
            onEditClick = { onEditClick() },
            onLogOutClick = { onLogOutClick() },
            onConfirmClick = { onConfirmClick() }
        )
    }else{
        DefaultAppBar(title)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DefaultAppBar(
    title: String
){
    TopAppBar(
        title = {
            Text(
                text = title,
                color = PrimaryColor
            )
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = BackgroundColor
        )
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AccountAppBar(
    title: String,
    onEditClick: () -> Unit,
    onLogOutClick: () -> Unit,
    onConfirmClick: () -> Unit
){
    var isEdit = remember{ mutableStateOf(false) }
    TopAppBar(
        title = {
            Text(
                text = title,
                color = PrimaryColor
            )
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = BackgroundColor
        ),
        actions = {

            if(isEdit.value){
                IconButton(onClick = {
                    onConfirmClick()
                    isEdit.value = !isEdit.value
                }) {
                    Icon(
                        imageVector = Icons.Default.Done,
                        contentDescription = "Принять",
                        tint = PrimaryColor
                    )
                }
            }else{
                IconButton(onClick = {
                    onEditClick()
                    isEdit.value = !isEdit.value
                }) {
                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = "Редактировать профиль",
                        tint = PrimaryColor
                    )
                }
                IconButton(onClick = {onLogOutClick()}) {
                    Icon(
                        imageVector = Icons.Default.ExitToApp,
                        contentDescription = "Редактировать профиль",
                        tint = PrimaryColor
                    )
                }
            }
        }
    )
}

@Preview
@Composable
fun TopAppBarsPreview(){
    TopAppBars(title = "Личный кабинет", onEditClick = { /*TODO*/ }, onLogOutClick = {}) {
    }
}