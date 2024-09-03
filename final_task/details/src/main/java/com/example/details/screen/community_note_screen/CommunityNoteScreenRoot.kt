package com.example.details.screen.community_note_screen

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.details.viewmodel.CommunityNoteViewModel
import org.orbitmvi.orbit.compose.collectAsState

@Composable
fun CommunityNoteScreenRoot(
    navController: NavController,
) {
    val viewModel: CommunityNoteViewModel = hiltViewModel()

    CommunityNoteScreenContent(
        state = viewModel.collectAsState().value,
        stack = navController.currentBackStackEntryAsState(),
        onAction = { action -> viewModel.onAction(action) }
    )
}