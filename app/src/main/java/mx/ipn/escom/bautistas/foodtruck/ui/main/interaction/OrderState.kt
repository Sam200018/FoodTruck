package mx.ipn.escom.bautistas.foodtruck.ui.main.interaction

data class OrderState(
    val isProductSelected: Boolean = false,
    val isAmountValid: Boolean = true,
    val isSchoolSelected: Boolean = false,
    val isBuildingValid: Boolean = true,
    val isLocationSet: Boolean = false,
    val isSuccess: Boolean = false,
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
)

fun OrderState.formValid(): Boolean {
    return isProductSelected && isAmountValid && isSchoolSelected && isBuildingValid && isLocationSet
}
