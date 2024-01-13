package mx.ipn.escom.bautistas.foodtruck.ui.main.viewmodels

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import mx.ipn.escom.bautistas.foodtruck.data.auth.model.SignInResult
import mx.ipn.escom.bautistas.foodtruck.ui.main.interaction.AuthState
import javax.inject.Inject

@HiltViewModel
class AuthViewModel
@Inject constructor() : ViewModel() {

    private val _authState = MutableStateFlow(AuthState())
    val authState = _authState.asStateFlow()

    fun onSignInResult(result: SignInResult) {
        _authState.update {
            it.copy(
                userData = result.data,
                errorMessage = result.errorMessage
            )
        }
    }

    fun resetState() {
        _authState.update {
            AuthState()
        }
    }
}