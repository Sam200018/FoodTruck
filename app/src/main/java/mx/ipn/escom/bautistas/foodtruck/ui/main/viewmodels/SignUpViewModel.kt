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
import mx.ipn.escom.bautistas.foodtruck.data.signup.SignUpDataSource
import mx.ipn.escom.bautistas.foodtruck.ui.main.interaction.SignUpState
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel
@Inject constructor(
    private val signUpDataSource: SignUpDataSource
) : ViewModel() {

    private val _signUpState = MutableStateFlow(SignUpState())
    val signUpState = _signUpState.asStateFlow()

    var nameValue: String by mutableStateOf("")
        private set

    var emailValue: String by mutableStateOf("")
        private set

    var passwordValue: String by mutableStateOf("")
        private set

    var confirmPasswordValue: String by mutableStateOf("")
        private set

    var userTypeValue: String by mutableStateOf("")
        private set

    fun onNameChange(name: String) {
        nameValue = name
        _signUpState.update {
            it.copy(
                isNameValid = nameValue.isNotBlank()
            )
        }
    }

    fun onEmailChange(email: String) {
        emailValue = email
        _signUpState.update {
            it.copy(isEmailValid = Patterns.EMAIL_ADDRESS.matcher(email).matches())
        }
    }

    fun onPasswordChange(password: String) {
        passwordValue = password
        _signUpState.update {
            it.copy(isPasswordValid = password.length > 6)
        }
    }

    fun onConfirmPasswordChange(password: String) {
        confirmPasswordValue = password
        _signUpState.update {
            it.copy(isMatchPassword = confirmPasswordValue == passwordValue)
        }
    }

    fun onTypeChange(userType: String) {
        userTypeValue = userType
        _signUpState.update {
            it.copy(isUserSelected = userTypeValue.isNotBlank())
        }
    }

    fun clearFields() {
        nameValue = ""
        emailValue = ""
        passwordValue = ""
        confirmPasswordValue = ""
        userTypeValue = ""
    }

    fun createAccount(
        backEvent: () -> Unit
    ) {
        _signUpState.update {
            it.copy(isLoading = true)
        }
        viewModelScope.launch {
            val signUpResult = signUpDataSource.createUserWithEmailAndPassword(
                name = nameValue,
                email = emailValue,
                password = passwordValue,
                type = userTypeValue
            )
            if (signUpResult.errorMessage != null) {
                _signUpState.update {
                    it.copy(errorMessage = signUpResult.errorMessage)
                }
            } else {
                _signUpState.update {
                    it.copy(
                        isLoading = false,
                        isSuccess = true
                    )
                }
                backEvent()
            }
        }
    }

    fun resetState() {
        _signUpState.update {
            SignUpState()
        }
    }

}