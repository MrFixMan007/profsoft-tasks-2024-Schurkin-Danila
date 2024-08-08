package com.example.profsoft_2024_task7_compose_navigation.navigation

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.profsoft_2024_task7_compose_navigation.secondscreen.SecondScreen

const val SECOND_SCREEN_ROUTE = "second_screen"
private const val TRANSITION_DURATION = 300

fun NavController.navigateToSecondScreen() {
    this.navigate(SECOND_SCREEN_ROUTE)
}

fun NavGraphBuilder.secondScreen(
    navController: NavController
) {
    composable(
        route = SECOND_SCREEN_ROUTE,
        enterTransition = { fadeIn(tween(TRANSITION_DURATION)) },
        exitTransition = { fadeOut(tween(TRANSITION_DURATION)) },
        popExitTransition = { fadeOut(tween(TRANSITION_DURATION)) },
        popEnterTransition = { fadeIn(tween(TRANSITION_DURATION)) },
    ) {
        SecondScreen(
            navController
        )
    }
}