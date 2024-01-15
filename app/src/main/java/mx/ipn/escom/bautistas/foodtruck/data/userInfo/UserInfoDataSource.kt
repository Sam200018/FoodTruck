package mx.ipn.escom.bautistas.foodtruck.data.userInfo

import com.google.firebase.firestore.FirebaseFirestore
import mx.ipn.escom.bautistas.foodtruck.data.auth.model.GoogleAuthUIClient
import mx.ipn.escom.bautistas.foodtruck.data.auth.model.toMap
import javax.inject.Inject

class UserInfoDataSource @Inject constructor(
    private val db: FirebaseFirestore,
    private val googleAuthUIClient: GoogleAuthUIClient,
) {

    suspend fun updateUserType(userType: String) {
        try {
            var userData = googleAuthUIClient.getSignedInUser()!!

            userData.userType = userType

            val infoMap = hashMapOf(
                "info" to userData.toMap()
            )
            db.collection("users").document(userData.userId).set(infoMap)
        } catch (e: Exception) {

        }
    }
}