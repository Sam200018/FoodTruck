package mx.ipn.escom.bautistas.foodtruck.ui.main.viewmodels

import android.location.Location
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import mx.ipn.escom.bautistas.foodtruck.data.auth.model.GoogleAuthUIClient
import mx.ipn.escom.bautistas.foodtruck.data.geolocation.LocationDataSource
import mx.ipn.escom.bautistas.foodtruck.data.oder.OrderDataSource
import mx.ipn.escom.bautistas.foodtruck.data.oder.model.Order
import mx.ipn.escom.bautistas.foodtruck.ui.main.interaction.OrderState
import javax.inject.Inject

@HiltViewModel
class OrderViewModel @Inject constructor(
    private val googleAuthUIClient: GoogleAuthUIClient,
    private val orderDataSource: OrderDataSource,
    private val locationDataSource: LocationDataSource,

    ) : ViewModel() {

    private val _orderState = MutableStateFlow(OrderState())
    val orderState = _orderState.asStateFlow()

    private var productValue: Int by mutableIntStateOf(0)
    var amountValue: String by mutableStateOf("")
        private set

    var currentLoc: Location? by mutableStateOf(null)
        private set

    private var schoolValue: Int by mutableIntStateOf(0)

    var buildingValue: String by mutableStateOf("")
        private set

    var commentValue: String by mutableStateOf("")
        private set


    fun onProductChange(product: Int) {
        productValue = product
        _orderState.update {
            it.copy(isProductSelected = true)
        }
    }

    fun onAmountChange(amount: String) {
        amountValue = amount
        _orderState.update {
            it.copy(isAmountValid = amountValue.isNotBlank())
        }
    }

    fun onSetLocation() {
        viewModelScope.launch {
            currentLoc = locationDataSource.getCurrentLocation()
            _orderState.update { it.copy(isLocationSet = currentLoc != null) }
        }
    }

    fun onSchoolChange(school: Int) {
        schoolValue = school
        _orderState.update {
            it.copy(isSchoolSelected = true)
        }
    }

    fun onBuildingChange(building: String) {
        buildingValue = building
        _orderState.update {
            it.copy(isBuildingValid = buildingValue.isNotBlank())
        }
    }

    fun onCommentChange(comment: String) {
        commentValue = comment
    }

    fun submitOrder(
        backEvent: () -> Unit
    ) {
        _orderState.update {
            it.copy(isLoading = true)
        }
        viewModelScope.launch {
            try {
                val user = googleAuthUIClient.getSignedInUser()!!
                val newOder = Order(
                    product = productValue,
                    amount = amountValue.toInt(),
                    lat = currentLoc?.latitude,
                    long = currentLoc?.longitude,
                    building = buildingValue,
                    comment = commentValue,
                    userOrder = user.userId
                )
                Log.i("order", newOder.toString())
                orderDataSource.createOrder(newOder)
                _orderState.update {
                    it.copy(isLoading = false, isSuccess = true)
                }
                backEvent()
            } catch (e: Exception) {
                _orderState.update {
                    it.copy(isLoading = false, errorMessage = e.message)
                }
            } finally {
                clearFields()
                resetState()
            }
        }

    }
    fun resetState(){
        _orderState.update {
            OrderState()
        }
    }

    fun clearFields(){
        amountValue = ""
        currentLoc = null
        buildingValue = ""
        commentValue = ""
    }

}