package com.example.profsoft_2024_task7_compose_navigation.thirdscreen

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import com.example.profsoft_2024_task7_compose_navigation.R
import com.example.profsoft_2024_task7_compose_navigation.component.ActionBar
import com.example.profsoft_2024_task7_compose_navigation.thirdscreen.components.ThirdScreenContent

@Composable
fun ThirdScreen(
    navController: NavController,
    firstName: String,
    lastName: String,
    patronymic: String,
    birthday: String,
) {
    Column {
        ActionBar(
            title = LocalContext.current.resources.getString(R.string.changes_profile),
            onBackClick = navController::popBackStack
        )
        ThirdScreenContent(
            navController = navController,
            firstName = firstName,
            lastName = lastName,
            patronymic = patronymic,
            birthday = birthday
        )
    }
}