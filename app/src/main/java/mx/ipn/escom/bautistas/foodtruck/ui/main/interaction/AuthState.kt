package mx.ipn.escom.bautistas.foodtruck.ui.main.interaction

import mx.ipn.escom.bautistas.foodtruck.data.auth.model.UserData

data class AuthState(
    val userData: UserData? = null,
    val errorMessage: String? = null
)
