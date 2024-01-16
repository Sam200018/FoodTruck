package mx.ipn.escom.bautistas.foodtruck.data.oder

import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import mx.ipn.escom.bautistas.foodtruck.data.auth.model.GoogleAuthUIClient
import mx.ipn.escom.bautistas.foodtruck.data.oder.model.Order
import mx.ipn.escom.bautistas.foodtruck.data.oder.model.toMap
import javax.inject.Inject
import kotlin.coroutines.cancellation.CancellationException

class OrderDataSource @Inject constructor(
    private val googleAuthUIClient: GoogleAuthUIClient,
    private val db: FirebaseFirestore
) {
    suspend fun createOrder(order: Order) {
        try {
            db.collection("oders").document().set(order.toMap()).await()
        } catch (e: Exception) {
            e.printStackTrace()
            if(e is CancellationException) throw e
        }
    }


}