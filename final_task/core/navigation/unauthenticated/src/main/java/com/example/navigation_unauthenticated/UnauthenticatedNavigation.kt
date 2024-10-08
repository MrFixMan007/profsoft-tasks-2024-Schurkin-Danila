package com.example.navigation_unauthenticated

import androidx.navigation.NavController

object UnauthenticatedNavigation{
    const val AUTHORIZATION_ROUTE = "authorization_screen"
    const val REGISTRATION_ROUTE = "registration_screen"
}

fun NavController.navigateToAuthorizationScreen() {
    this.navigate(UnauthenticatedNavigation.AUTHORIZATION_ROUTE){
        popUpTo(UnauthenticatedNavigation.REGISTRATION_ROUTE){
            saveState = true
            inclusive = true
        }
        launchSingleTop = true
        restoreState = true
    }
}

fun NavController.navigateToRegistrationScreen(
) {
    this.navigate(UnauthenticatedNavigation.REGISTRATION_ROUTE)
}