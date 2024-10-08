package com.example.feature_login.screens.registration_screen.viewmodel


import android.content.Context
import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.common.domain.usecase.unauthenticated.RegisterUserUseCase
import com.example.feature_login.screens.registration_screen.model.RegistrationAction
import com.example.feature_login.screens.registration_screen.model.RegistrationSideEffect
import com.example.feature_login.screens.registration_screen.model.RegistrationSideNavigate
import com.example.feature_login.screens.registration_screen.model.RegistrationState
import com.example.utils.PASSWORD_SHARED_PREFS
import com.example.utils.PHONE_SHARED_PREFS
import com.example.utils.TOKEN_NAME
import com.example.utils.TOKEN_SHARED_PREFS
import com.example.utils.isCorrectPhoneSymbolsAndLength
import com.example.utils.isValidName
import com.example.utils.isValidPassword
import com.example.utils.isValidPhone
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.viewmodel.container
import javax.inject.Inject

@HiltViewModel
class RegistrationViewModel @Inject constructor(
    private val registerUseCase: RegisterUserUseCase,
) : ContainerHost<RegistrationState, RegistrationSideEffect>, ViewModel() {

    override val container = container<RegistrationState, RegistrationSideEffect>(
        RegistrationState()
    )

    private val _sideNavigate = MutableSharedFlow<RegistrationSideNavigate>()
    val sideNavigate = _sideNavigate.asSharedFlow()

    private var buttonEnableJob: Job? = null

    fun onAction(action: RegistrationAction) = intent {
        when (action) {
            is RegistrationAction.Register -> {
                register(action)
            }

            is RegistrationAction.FirstNameChange -> {
                if (action.newValue.isValidName()) {
                    reduce {
                        state.copy(registerUserParam = state.registerUserParam.copy(firstName = action.newValue))
                    }
                }
            }

            is RegistrationAction.LastNameChange -> {
                if (action.newValue.isValidName()) {
                    reduce {
                        state.copy(registerUserParam = state.registerUserParam.copy(lastName = action.newValue))
                    }
                }
            }

            is RegistrationAction.PhoneChange -> {
                if (action.newValue.isCorrectPhoneSymbolsAndLength()) {
                    reduce {
                        state.copy(registerUserParam = state.registerUserParam.copy(phoneNumber = action.newValue))
                    }
                }
            }

            is RegistrationAction.PasswordChange -> {
                reduce {
                    state.copy(registerUserParam = state.registerUserParam.copy(password = action.newValue))
                }
            }

            is RegistrationAction.NavigateToAuthorization -> {
                if (buttonEnableJob?.isActive == true) return@intent
                reduce {
                    state.copy(isEnabledAuthorizeNavigateButton = false)
                }
                _sideNavigate.emit(RegistrationSideNavigate.NavigateToAuthorization)

                buttonEnableJob = viewModelScope.launch {
                    delay(2000)
                    reduce {
                        state.copy(isEnabledAuthorizeNavigateButton = true)
                    }
                }
            }
        }
    }

    private fun register(action: RegistrationAction.Register) = intent {
        if (!state.registerUserParam.password.isValidPassword()) {
            reduce {
                state.copy(isValidPassword = false)
            }
        } else if (!state.isValidPassword) {
            reduce {
                state.copy(isValidPassword = true)
            }
        }

        if (!state.registerUserParam.phoneNumber.isValidPhone()) {
            reduce {
                state.copy(isValidPhone = false)
            }
        } else if (!state.isValidPhone) {
            reduce {
                state.copy(isValidPhone = true)
            }
        }

        if (!state.registerUserParam.firstName.isValidName() || state.registerUserParam.firstName.isEmpty()) {
            reduce {
                state.copy(isValidFirstName = false)
            }
        } else if (!state.isValidFirstName) {
            reduce {
                state.copy(isValidFirstName = true)
            }
        }

        if (!state.registerUserParam.lastName.isValidName() || state.registerUserParam.lastName.isEmpty()) {
            reduce {
                state.copy(isValidLastName = false)
            }
        } else if (!state.isValidLastName) {
            reduce {
                state.copy(isValidLastName = true)
            }
        }

        if (state.isValidPhone && state.isValidPassword && state.isValidFirstName && state.isValidLastName) {
            reduce {
                state.copy(isLoadingRegistration = true)
            }
            delay(500)

            var phoneParam =
                if (state.registerUserParam.phoneNumber[0] != '+') state.registerUserParam.phoneNumber else state.registerUserParam.phoneNumber.substring(
                    1
                )
            if (phoneParam[0] == '8') phoneParam = "7${phoneParam.substring(1)}"

            val result = registerUseCase.execute(
                registerUserParam = state.registerUserParam.copy(phoneNumber = phoneParam)
            )

            if (result.isSuccess) {
                val sharedPreferences: SharedPreferences =
                    action.context.getSharedPreferences(
                        TOKEN_SHARED_PREFS, Context.MODE_PRIVATE
                    )
                val editor: SharedPreferences.Editor = sharedPreferences.edit()
                editor.putString(TOKEN_NAME, result.token)
                editor.putString(PHONE_SHARED_PREFS, phoneParam)
                editor.putString(PASSWORD_SHARED_PREFS, state.registerUserParam.password)
                editor.apply()
                _sideNavigate.emit(RegistrationSideNavigate.Completed)
            } else {
                postSideEffect(RegistrationSideEffect.Failed)
            }

            reduce {
                state.copy(isLoadingRegistration = false)
            }
        }
    }
}