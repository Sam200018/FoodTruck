package mx.ipn.escom.bautistas.foodtruck.ui.main.interaction

data class SignUpState(
    val isNameValid: Boolean = true,
    val isEmailValid: Boolean = true,
    val isPasswordValid: Boolean = true,
    val isMatchPassword: Boolean = true,
    val isUserSelected: Boolean = false,
    val isSuccess: Boolean = false,
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)

fun SignUpState.formValid(): Boolean {
    return isEmailValid && isEmailValid && isPasswordValid && isMatchPassword && isUserSelected
}