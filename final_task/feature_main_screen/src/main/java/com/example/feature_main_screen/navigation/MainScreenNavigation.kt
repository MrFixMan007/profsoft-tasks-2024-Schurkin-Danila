package com.example.feature_main_screen.navigation

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.feature_main_screen.screen.MainScreenRoot
import com.example.ui.components.bottom_bar.BottomBarRoot

private const val TRANSITION_DURATION = 300

fun NavController.navigateToMainScreen() {
    this.navigate(BottomBarRoot.Home.route)
}

fun NavGraphBuilder.mainScreen(
    navController: NavController,
) {
    composable(
        route = BottomBarRoot.Home.route,
        enterTransition = { fadeIn(tween(TRANSITION_DURATION)) },
        exitTransition = { fadeOut(tween(TRANSITION_DURATION)) },
        popExitTransition = { fadeOut(tween(TRANSITION_DURATION)) },
        popEnterTransition = { fadeIn(tween(TRANSITION_DURATION)) },
    ) {
        MainScreenRoot(
            navController = navController,
        )
    }
}