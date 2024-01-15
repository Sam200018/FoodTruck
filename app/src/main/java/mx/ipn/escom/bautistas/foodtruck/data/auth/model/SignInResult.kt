package mx.ipn.escom.bautistas.foodtruck.data.auth.model

data class SignInResult(
    val data: UserData?,
    val errorMessage: String?
)

data class UserData(
    val userId: String,
    val userName: String?,
    val profilePictureUri: String?,
    val email: String?,
    var userType: String?
)

fun UserData.toMap(): HashMap<String, String?> {
    return hashMapOf(
        "name" to userName,
        "profilePictureUri" to profilePictureUri,
        "email" to email,
        "user_type" to userType
    )
}
