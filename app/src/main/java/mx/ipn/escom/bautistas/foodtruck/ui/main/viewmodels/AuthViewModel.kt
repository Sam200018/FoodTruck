package mx.ipn.escom.bautistas.foodtruck.ui.main.viewmodels

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
import mx.ipn.escom.bautistas.foodtruck.data.auth.model.GoogleAuthUIClient
import mx.ipn.escom.bautistas.foodtruck.data.auth.model.SignInResult
import mx.ipn.escom.bautistas.foodtruck.data.userInfo.UserInfoDataSource
import mx.ipn.escom.bautistas.foodtruck.ui.main.interaction.AuthState
import javax.inject.Inject

@HiltViewModel
class AuthViewModel
@Inject constructor(
    private val userInfoDataSource: UserInfoDataSource,
    private val googleAuthUIClient: GoogleAuthUIClient,
) : ViewModel() {

    private val _authState = MutableStateFlow(AuthState())
    val authState = _authState.asStateFlow()

    var userType: String by mutableStateOf("")
        private set

    fun onSignInResult(result: SignInResult) {
        _authState.update {
            it.copy(
                userData = result.data,
                errorMessage = result.errorMessage
            )
        }
    }

    fun onUserTypeChange(userType: String){
        this.userType = userType
    }

    fun updateUserInfo() {
        viewModelScope.launch {
            userInfoDataSource.updateUserType(userType)
            _authState.update {
                it.copy(
                    userData = googleAuthUIClient.getSignedInUser()
                )
            }
        }
    }

    fun resetState() {
        _authState.update {
            AuthState()
        }
    }
}