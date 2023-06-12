package com.example.algis

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.MailOutline
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.algis.data.AppViewModelProvider
import com.example.algis.ui.components.TopAppBars
import com.example.algis.ui.screens.AccountScreen
import com.example.algis.ui.screens.ChatScreen
import com.example.algis.ui.screens.ContactsScreen
import com.example.algis.ui.screens.MainScreen
import com.example.algis.ui.screens.NewsScreen
import com.example.algis.ui.screens.ReferenceHistoryScreen
import com.example.algis.ui.screens.ReferenceListScreen
import com.example.algis.ui.screens.ReferenceManagerScreen
import com.example.algis.ui.screens.ReferenceOrder
import com.example.algis.ui.theme.BackgroundColor
import com.example.algis.ui.theme.PrimaryColor

enum class Navigation{
    Main,
    News,
    Messages,
    Account,
    ManageReference,
    ReferenceList,
    ReferenceOrder,
    ReferenceHistory,
    Contacts
}

@Composable
fun AlgisApp(
    modifier: Modifier = Modifier
        .background(BackgroundColor),
    viewModel: MainViewModel,
    navController: NavHostController = rememberNavController()
) {
    val title by viewModel.titleState
    Scaffold(
        topBar = {
            TopAppBars(
                title = title,
                onEditClick = {viewModel.onEditClick()},
                onLogOutClick = {viewModel.onLogOutClick()},
                onConfirmClick = {viewModel.saveAccountData()}
            )
        },
        bottomBar = { AppNavigation(navController = navController) }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Navigation.Main.name,
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            composable(route = Navigation.Main.name) {
                viewModel.titleState.value = "Главная"
                MainScreen(
                    viewModel = viewModel,
                    { navController.navigate(Navigation.Contacts.name) },
                    { navController.navigate(Navigation.ManageReference.name) }
                )
            }
            composable(route = Navigation.Contacts.name) {
                viewModel.titleState.value = "Контакты"
                ContactsScreen(viewModel = viewModel)
            }
            composable(route = Navigation.ManageReference.name) {
                viewModel.titleState.value = "Справки"
                ReferenceManagerScreen(
                    onReferenceListClick = { navController.navigate(Navigation.ReferenceList.name) },
                    onHistoryClick = { navController.navigate(Navigation.ReferenceHistory.name) }
                )
            }
            composable(route = Navigation.ReferenceHistory.name) {
                viewModel.titleState.value = "История заказов"
                ReferenceHistoryScreen(viewModel = viewModel)
            }
            composable(route = Navigation.ReferenceList.name) {
                viewModel.titleState.value = "Выбор справки"
                ReferenceListScreen(
                    onOrderClick = { navController.navigate(Navigation.ReferenceOrder.name + "/$it") },
                    viewModel = viewModel
                )
            }
            composable(
                route = Navigation.ReferenceOrder.name + "/{refName}",
                arguments = listOf(navArgument("refName") {
                    type = NavType.StringType
                })
            ) {
                viewModel.titleState.value = "Подтверждение заказа"
                val refName = it.arguments?.getString("refName")!!
                ReferenceOrder(refName = refName, navigateUp = { navController.navigateUp() },
                viewModel = viewModel)
            }
            composable(route = Navigation.News.name) {
                viewModel.titleState.value = "Новости"
                NewsScreen()
            }
            composable(route = Navigation.Messages.name) {
                viewModel.titleState.value = "Сообщения"
                ChatScreen(viewModel = viewModel)
            }
            composable(route = Navigation.Account.name) {
                viewModel.titleState.value = "Личный кабинет"
                AccountScreen(viewModel = viewModel)
            }

        }

    }
}

@Composable
fun AppNavigation(
    navController: NavHostController
) {

    val items = listOf(
        mapOf("name" to "Главная", "icon" to Icons.Default.Home, "nav" to Navigation.Main.name),
        mapOf("name" to "Новости", "icon" to Icons.Default.Info, "nav" to Navigation.News.name),
        mapOf("name" to "Сообщения", "icon" to Icons.Default.MailOutline, "nav" to Navigation.Messages.name),
        mapOf("name" to "Аккаунт", "icon" to Icons.Default.AccountBox, "nav" to Navigation.Account.name)
    )

    val selectedItem = remember { mutableStateOf(items[0]) }

    NavigationBar(
        containerColor = BackgroundColor,
        contentColor = PrimaryColor
    ) {
        items.forEach { item ->
            NavigationBarItem(
                icon = {
                    Icon(
                        item["icon"] as ImageVector,
                        contentDescription = null,
                        tint = PrimaryColor
                    )
                },
                label = { Text(
                    item["name"].toString(),
                    color = PrimaryColor
                ) },
                selected = item == selectedItem.value,
                onClick = {
                    selectedItem.value = item
                    navController.navigate(item["nav"].toString()) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
            colors = NavigationBarItemDefaults.colors(

            ))

        }
    }
}

@Preview
@Composable
fun AppNavigationPreview(){
    val navController = rememberNavController()
    AppNavigation(navController)
}