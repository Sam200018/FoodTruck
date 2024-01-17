package mx.ipn.escom.bautistas.foodtruck.ui.main.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import mx.ipn.escom.bautistas.foodtruck.data.oder.OrderDataSource
import mx.ipn.escom.bautistas.foodtruck.ui.main.interaction.OrdersState
import javax.inject.Inject

@HiltViewModel
class OrdersViewModel @Inject constructor(
    private val orderDataSource: OrderDataSource
) : ViewModel() {

    private val _ordersState = MutableStateFlow(OrdersState())
    val ordersState = _ordersState.asStateFlow()


    init {
        getAll()
    }

    fun getAll() {
        _ordersState.update {
            it.copy(isLoading = true)
        }

        viewModelScope.launch {
            Log.i("Flow", orderDataSource.readOrders().toString())

            _ordersState.update {
                OrdersState(
                    ordersList = orderDataSource.readOrders()
                )
            }
        }
    }
}