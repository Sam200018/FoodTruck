package mx.ipn.escom.bautistas.foodtruck.ui.main.interaction


data class SignInState(
    val isEmailValid:Boolean = true,
    val isPasswordValid:Boolean = true,
    val isSuccess:Boolean = false,
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)

fun SignInState.formValid():Boolean{
    return isEmailValid && isPasswordValid
}