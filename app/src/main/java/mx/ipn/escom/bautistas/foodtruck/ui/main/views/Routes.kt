package mx.ipn.escom.bautistas.foodtruck.ui.main.views

sealed class Routes (
    val route: String
){
    data object HomeScreen: Routes(route = "/home")
    data object SignInScreen: Routes(route = "/sign_in")
    data object SignupScreen: Routes(route = "/sign_up")
}