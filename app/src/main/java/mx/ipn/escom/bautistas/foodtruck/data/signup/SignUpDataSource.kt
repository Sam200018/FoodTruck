package mx.ipn.escom.bautistas.foodtruck.data.signup

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import mx.ipn.escom.bautistas.foodtruck.data.auth.model.SignInResult
import mx.ipn.escom.bautistas.foodtruck.data.auth.model.UserData
import mx.ipn.escom.bautistas.foodtruck.data.auth.model.toMap
import javax.inject.Inject
import kotlin.coroutines.cancellation.CancellationException

class SignUpDataSource @Inject constructor(
    private val auth: FirebaseAuth,
    private val db: FirebaseFirestore
) {
    suspend fun createUserWithEmailAndPassword(
        name: String,
        email: String,
        password: String,
        type: String,
    ): SignInResult {
        return try {
            val user = auth.createUserWithEmailAndPassword(email, password).await().user

            SignInResult(
                data = user?.run {
                    val userData = UserData(
                        userId = uid,
                        userName = name,
                        profilePictureUri = photoUrl?.toString(),
                        email = email,
                        userType = type
                    )
                    val infoMap = hashMapOf(
                        "info" to userData.toMap()
                    )
                    db.collection("users").document(uid)
                        .set(infoMap)

                    userData
                },
                errorMessage = null
            )

        } catch (e: Exception) {
            e.printStackTrace()
            if (e is CancellationException) throw e
            SignInResult(
                data = null, errorMessage = e.message
            )
        }
    }
}

