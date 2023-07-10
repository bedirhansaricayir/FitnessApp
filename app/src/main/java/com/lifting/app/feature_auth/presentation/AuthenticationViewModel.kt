package com.lifting.app.feature_auth.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.AuthCredential
import com.lifting.app.core.util.Resource
import com.lifting.app.feature_auth.domain.model.AuthenticationMode
import com.lifting.app.feature_auth.domain.model.PasswordRequirements
import com.lifting.app.feature_auth.domain.model.InputValidationType
import com.lifting.app.feature_auth.domain.repository.AuthRepository
import com.lifting.app.feature_auth.domain.use_case.EmailInputValidationUseCase
import com.lifting.app.feature_auth.presentation.sign_in.GoogleSignInState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class AuthenticationViewModel @Inject constructor(
    private val emailInputValidationUseCase: EmailInputValidationUseCase,
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _authState = MutableStateFlow(AuthenticationState())
    val authState: StateFlow<AuthenticationState> = _authState.asStateFlow()

    private val  _googleState = MutableStateFlow(GoogleSignInState())
    val googleState: StateFlow<GoogleSignInState> = _googleState.asStateFlow()

    fun onEvent(authenticationEvent: AuthenticationEvent) {
        when(authenticationEvent) {
            is AuthenticationEvent.ToggleAuthenticationMode -> {
                toggleAuthenticationMode()
            }
            is AuthenticationEvent.UsernameChanged -> {
                updateUsername(authenticationEvent.username)
            }
            is AuthenticationEvent.EmailChanged -> {
                updateEmail(authenticationEvent.emailAddress)
                checkInputValidation(authenticationEvent.emailAddress)
            }
            is AuthenticationEvent.PasswordChanged -> {
                updatePassword(authenticationEvent.password)
            }
            is AuthenticationEvent.Authenticate -> {
                authenticate()
            }
            is AuthenticationEvent.ErrorDismissed -> {
                dismissError()
            }
            is AuthenticationEvent.ToggleVisualTransformation -> {
                toggleVisualTransformation()
            }
            is AuthenticationEvent.GoogleSignInClicked -> {
                googleSignIn(authenticationEvent.credential)
            }
        }
    }

    private fun googleSignIn(credential: AuthCredential) = viewModelScope.launch {
        authRepository.googleSignIn(credential).collect { result ->
            when (result) {
                is Resource.Success -> {
                    _googleState.value = _googleState.value.copy(
                        success = result.data
                    )
                }
                is Resource.Loading -> {
                    _googleState.value = _googleState.value.copy(
                        loading = true
                    )                }
                is Resource.Error -> {
                    _googleState.value = _googleState.value.copy(
                        error = result.e ?: "Oops! something went wrong."
                    )
                }
            }


        }
    }
    private fun toggleAuthenticationMode() {
        val authenticationMode = _authState.value.authenticationMode
        val newAuthenticationMode = if (
            authenticationMode == AuthenticationMode.SIGN_IN
        ) {
            AuthenticationMode.SIGN_UP
        } else {
            AuthenticationMode.SIGN_IN
        }
        _authState.value = _authState.value.copy(
            authenticationMode = newAuthenticationMode
        )
    }

    private fun updateUsername(username: String) {
        _authState.value = _authState.value.copy(
            username = username
        )
    }

    private fun updateEmail(email: String) {
        _authState.value = _authState.value.copy(
            email = email
        )
    }

    private fun updatePassword(password: String) {
        val requirements = mutableListOf<PasswordRequirements>()
        if (password.length > 7) {
            requirements.add(PasswordRequirements.EIGHT_CHARACTERS)
        }
        if (password.any { it.isUpperCase() }) {
            requirements.add(PasswordRequirements.CAPITAL_LETTER)
        }
        if (password.any { it.isDigit() }) {
            requirements.add(PasswordRequirements.NUMBER)
        }
        _authState.value = _authState.value.copy(
            password = password,
            passwordRequirements = requirements.toList()
        )
    }

    private fun authenticate() {
        _authState.value = _authState.value.copy(
            isLoading = true
        )
        // trigger network request
        viewModelScope.launch(Dispatchers.IO) {
            delay(2000L)

            withContext(Dispatchers.Main) {
                _authState.value = _authState.value.copy(
                    isLoading = false,
                    error = "Something went wrong!"
                )
            }
        }
    }

    private fun dismissError() {
        _authState.value = _authState.value.copy(
            error = null
        )
    }

    private fun toggleVisualTransformation() {
        _authState.value = _authState.value.copy(
            isPasswordShown = !_authState.value.isPasswordShown
        )
    }

    private fun checkInputValidation(email: String) {
        val result = emailInputValidationUseCase.invoke(email)
        processInputValidation(result)
    }
    private fun processInputValidation(type: InputValidationType) {
        when(type) {
           InputValidationType.NoEmail -> {
               _authState.value = _authState.value.copy(
                   emailError = true
               )
           }
           InputValidationType.Valid -> {
               _authState.value = _authState.value.copy(
                   emailError = false
               )
           }
       }
    }
}
