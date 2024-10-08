package com.example.profsoft_2024_task7_compose_navigation.navigation

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.profsoft_2024_task7_compose_navigation.mainscreen.MainScreen

const val MAIN_SCREEN_ROUTE = "main_screen"
private const val TRANSITION_DURATION = 300

fun NavController.navigateToMainScreen() {
    this.navigate(MAIN_SCREEN_ROUTE) {
        popUpTo(MAIN_SCREEN_ROUTE) {
            saveState = true
        }
        launchSingleTop = true
        restoreState = true
    }
}

fun NavGraphBuilder.mainScreen(
    navController: NavController
) {
    composable(
        route = MAIN_SCREEN_ROUTE,
        enterTransition = { fadeIn(tween(TRANSITION_DURATION)) },
        exitTransition = { fadeOut(tween(TRANSITION_DURATION)) },
        popExitTransition = { fadeOut(tween(TRANSITION_DURATION)) },
        popEnterTransition = { fadeIn(tween(TRANSITION_DURATION)) },
    ) {
        MainScreen(
            navController
        )
    }
}