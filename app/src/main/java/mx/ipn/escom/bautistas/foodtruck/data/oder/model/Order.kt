package mx.ipn.escom.bautistas.foodtruck.data.oder.model

data class Order(
    val id: String? = null,
    val product: Int = 0,
    val amount: Int = 0,
    val lat: Double? = 0.0,
    val long: Double? = 0.0,
    val building: String = "",
    val comment: String = "",
    val userOrder: String? = null,
    val userWorker: String? = null,
)

fun Order.toMap(): HashMap<String, Any?> {
    return hashMapOf(
        "product" to product,
        "amount" to amount,
        "lat" to lat,
        "long" to long,
        "building" to building,
        "comment" to comment,
        "userOrder" to userOrder,
        "userWorker" to userWorker
    )
}

//fun Order.fromMap(id: String?, data: Map<String, Object> ): Order{
//    return  Order(id,product= data["product"],)
//}


