package com.example.profsoft_2024_final_task.presentation.authorization_screen.viewmodel

import androidx.lifecycle.ViewModel
import com.example.profsoft_2024_final_task.domain.usecase.AuthorizeUserUseCase
import com.example.profsoft_2024_final_task.domain.utils.isCorrectPhoneSymbolsAndLength
import com.example.profsoft_2024_final_task.domain.utils.isValidPassword
import com.example.profsoft_2024_final_task.domain.utils.isValidPhone
import com.example.profsoft_2024_final_task.presentation.authorization_screen.state.AuthorizationSideEffect
import com.example.profsoft_2024_final_task.presentation.authorization_screen.state.AuthorizationState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.viewmodel.container
import javax.inject.Inject

@HiltViewModel
class AuthorizationViewModel @Inject constructor(
    private val authorizeUserUseCase: AuthorizeUserUseCase,
) : ContainerHost<AuthorizationState, AuthorizationSideEffect>, ViewModel() {

    override val container = container<AuthorizationState, AuthorizationSideEffect>(
        AuthorizationState()
    )

    fun onAuthorize() = intent {

        if (!state.authorizeUserParam.password.isValidPassword()) {
            reduce {
                state.copy(isValidPassword = false)
            }
        } else if (!state.isValidPassword) {
            reduce {
                state.copy(isValidPassword = true)
            }
        }

        if (!state.authorizeUserParam.phoneNumber.isValidPhone()) {
            reduce {
                state.copy(isValidPhone = false)
            }
        } else if (!state.isValidPhone) {
            reduce {
                state.copy(isValidPhone = true)
            }
        }

        if (state.isValidPhone && state.isValidPassword) {
            reduce {
                state.copy(isLoadingAuthorization = true)
            }
//            authorizeUserUseCase.execute(authorizeUserParam = state.authorizeUserParam)
        }
    }

    fun onPhoneChange(newValue: String) = intent {
        if (newValue.isCorrectPhoneSymbolsAndLength()) {
            reduce {
                state.copy(authorizeUserParam = state.authorizeUserParam.copy(phoneNumber = newValue))
            }
        }
    }

    fun onPasswordChange(newValue: String) = intent {
        reduce {
            state.copy(authorizeUserParam = state.authorizeUserParam.copy(password = newValue))
        }
    }

    fun disactivate() = intent {
        reduce {
            state.copy(isEnabledRegisterNavigateButton = false)
        }
    }

    fun activate() = intent {
        delay(1000)
        reduce {
            state.copy(isEnabledRegisterNavigateButton = true)
        }
    }

}