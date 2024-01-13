package mx.ipn.escom.bautistas.foodtruck.ui.main.viewmodels

import android.util.Patterns
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import mx.ipn.escom.bautistas.foodtruck.data.auth.model.SignInResult
import mx.ipn.escom.bautistas.foodtruck.data.signup.SignUpDataSource
import mx.ipn.escom.bautistas.foodtruck.ui.main.interaction.SignInState
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val signUpDataSource: SignUpDataSource
) : ViewModel() {
    private val _signInState = MutableStateFlow(SignInState())
    val signInState = _signInState.asStateFlow()

    var emailValue: String by mutableStateOf("")
        private set

    var passwordValue: String by mutableStateOf("")
        private set

    fun onEmailChange(email: String) {
        emailValue = email
        _signInState.update {
            it.copy(isEmailValid = Patterns.EMAIL_ADDRESS.matcher(emailValue).matches())
        }
    }

    fun onPasswordChange(password: String) {
        passwordValue = password
        _signInState.update {
            it.copy(isPasswordValid = passwordValue.isNotBlank())
        }
    }


    fun signIn(updateUI: (SignInResult) -> Unit) {
        _signInState.update {
            it.copy(isLoading = true)
        }

        viewModelScope.launch {

            val signInResult =
                signUpDataSource.signInWithEmailAndPassword(emailValue, passwordValue)

            if (signInResult.errorMessage != null) {
                _signInState.update {
                    it.copy(errorMessage = signInResult.errorMessage)
                }
            } else {
                _signInState.update {
                    it.copy(
                        isLoading = false,
                        isSuccess = true
                    )
                }
                updateUI(signInResult)
            }

        }
    }

    fun resetState() {
        _signInState.update {
            SignInState()
        }
    }

    fun clearFields() {
        emailValue = ""
        passwordValue = ""
    }
}