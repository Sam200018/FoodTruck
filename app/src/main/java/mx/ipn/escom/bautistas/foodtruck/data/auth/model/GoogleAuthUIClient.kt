package mx.ipn.escom.bautistas.foodtruck.data.auth.model

import android.content.Context
import android.content.Intent
import android.content.IntentSender
import android.util.Log
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import mx.ipn.escom.bautistas.foodtruck.R
import kotlin.coroutines.cancellation.CancellationException

class GoogleAuthUIClient(
    private val context: Context,
    private val oneTapClient: SignInClient,
    private val auth: FirebaseAuth,
    private val db: FirebaseFirestore,
) {
    suspend fun signIn(): IntentSender? {
        val result = try {
            oneTapClient.beginSignIn(
                buildSignInRequest()
            ).await()
        } catch (e: Exception) {
            e.printStackTrace()
            if (e is CancellationException) throw e
            null
        }
        return result?.pendingIntent?.intentSender
    }

    suspend fun googleSignInWithIntent(intent: Intent): SignInResult {
        val credential = oneTapClient.getSignInCredentialFromIntent(intent)
        val googleIdToken = credential.googleIdToken

        val googleCredentials = GoogleAuthProvider.getCredential(googleIdToken, null)

        return try {
            val user = auth.signInWithCredential(googleCredentials).await().user

            SignInResult(
                data = user?.run {
                    val userData = UserData(
                        userId = uid,
                        userName = displayName,
                        profilePictureUri = photoUrl?.toString(),
                        email = email,
                        userType = null
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

    suspend fun signOut() {
        try {
            oneTapClient.signOut().await()
            auth.signOut()
        } catch (e: Exception) {
            e.printStackTrace()
            if (e is CancellationException) throw e
        }
    }

    suspend fun getSignedInUser(): UserData? {


        return auth.currentUser?.run {
            val userDoc = db.collection("users").document(uid).get().await()
            if (userDoc.exists()) {
                val infoMap = userDoc["info"] as? HashMap<String, String?> ?: hashMapOf()
                Log.i(
                    "infoMap",
                    infoMap.toString()
                )

                UserData(
                    userId = uid,
                    userName = displayName,
                    profilePictureUri = infoMap["profilePictureUri"],
                    email = email,
                    userType = infoMap["user_type"]
                )
            } else {
                UserData(
                    userId = uid,
                    userName = displayName,
                    profilePictureUri = photoUrl?.toString(),
                    email = email,
                    userType = null
                )
            }

        }
    }

    private fun buildSignInRequest(): BeginSignInRequest {
        return BeginSignInRequest.Builder()
            .setGoogleIdTokenRequestOptions(
                BeginSignInRequest.GoogleIdTokenRequestOptions
                    .builder()
                    .setSupported(true)
                    .setFilterByAuthorizedAccounts(false)
                    .setServerClientId(context.getString(R.string.web_client_id))
                    .build()
            )
            .setAutoSelectEnabled(true)
            .build()
    }
}