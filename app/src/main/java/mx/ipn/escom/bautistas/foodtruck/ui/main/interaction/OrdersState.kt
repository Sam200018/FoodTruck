package mx.ipn.escom.bautistas.foodtruck.ui.main.interaction

import mx.ipn.escom.bautistas.foodtruck.data.oder.model.Order

data class OrdersState(
    val ordersList: List<Order> = listOf(),
    val order: Order = Order(),
    var isLoading: Boolean = false,
)
