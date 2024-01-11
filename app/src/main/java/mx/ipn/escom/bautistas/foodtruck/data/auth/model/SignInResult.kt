package mx.ipn.escom.bautistas.foodtruck.data.auth.model

data class SignInResult(
    val data: UserData?,
    val errorMessage: String?
)

data class UserData(
    val userId: String,
    val userName: String?,
    val profilePictureUri: String?
)
