package mx.ipn.escom.bautistas.foodtruck.ui.main.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor() : ViewModel() {



    var emailValue: String by mutableStateOf("")
        private set

    var passwordValue: String by mutableStateOf("")
        private set

    fun onEmailChange(email: String) {
        emailValue = email
    }

    fun onPasswordChange(password: String) {
        passwordValue = password
    }

}