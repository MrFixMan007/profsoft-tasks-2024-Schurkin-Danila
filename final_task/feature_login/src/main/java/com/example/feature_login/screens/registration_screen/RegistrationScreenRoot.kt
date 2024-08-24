package com.example.feature_login.screens.registration_screen

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.feature_login.navigation.OutNavigator
import com.example.feature_login.navigation.navigateToAuthorizationScreen
import com.example.feature_login.screens.registration_screen.model.RegistrationSideEffect
import com.example.feature_login.screens.registration_screen.viewmodel.RegistrationViewModel
import org.orbitmvi.orbit.compose.collectAsState

@Composable
fun RegistrationScreenRoot(
    navController: NavController,
    outNavigator: OutNavigator
) {
    val viewModel: RegistrationViewModel = hiltViewModel()
    val sideEffects = viewModel.sideEffects

    LaunchedEffect(sideEffects) {
        sideEffects.collect { sideEffect ->
            when (sideEffect) {
                is RegistrationSideEffect.Completed -> outNavigator.navigateToMainScreen()
                is RegistrationSideEffect.NavigateToAuthorization -> navController.navigateToAuthorizationScreen()
                else -> Unit
            }
        }
    }

    Column {
        RegistrationScreenContent(
            state = viewModel.collectAsState().value,
            sideEffects = sideEffects,
            onAction = { action -> viewModel.onAction(action) })
    }
}