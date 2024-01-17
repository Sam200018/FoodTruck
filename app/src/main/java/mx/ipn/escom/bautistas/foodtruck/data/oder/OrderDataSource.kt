package mx.ipn.escom.bautistas.foodtruck.data.oder

import android.util.Log
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
            db.collection("orders").document().set(order.toMap()).await()
        } catch (e: Exception) {
            e.printStackTrace()
            if (e is CancellationException) throw e
        }
    }

    suspend fun readOrders(): List<Order> {
        val user = googleAuthUIClient.getSignedInUser()!!
        val ordersList = mutableListOf<Order>()
        try {
            val ordersRef = db.collection("orders").get().await()
            ordersRef.documents.forEach {

                val order = Order(
                    id = it.id,
                    product = it.data?.get("product").toString().toInt(),
                    amount = it.data?.get("amount").toString().toInt(),
                    lat = (it.data?.get("lat") as? Double) ?: 0.0,
                    long = (it.data?.get("long") as? Double) ?: 0.0,
                    building = it.data?.get("building") as? String ?: "",
                    comment = it.data?.get("comment") as? String ?: "",
                    userOrder = it.data?.get("userOrder") as? String,
                    userWorker = it.data?.get("userWorker") as? String,
                )
                Log.i("value", it.data?.get("amount").toString())
                ordersList.add(order)
            }
            return ordersList.filter { it.userOrder == user.userId }
        } catch (e: Exception) {
            if (e is CancellationException) throw e
        }
        return mutableListOf()
    }
}