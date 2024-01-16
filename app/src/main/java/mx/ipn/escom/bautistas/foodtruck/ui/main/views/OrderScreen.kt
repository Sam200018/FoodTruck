package mx.ipn.escom.bautistas.foodtruck.ui.main.views

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import mx.ipn.escom.bautistas.foodtruck.R
import mx.ipn.escom.bautistas.foodtruck.ui.components.ButtonComponent
import mx.ipn.escom.bautistas.foodtruck.ui.components.LocationSetComponent
import mx.ipn.escom.bautistas.foodtruck.ui.components.ProductSelectorComponent
import mx.ipn.escom.bautistas.foodtruck.ui.components.SchoolSelectorComponent
import mx.ipn.escom.bautistas.foodtruck.ui.components.TextFieldComponent
import mx.ipn.escom.bautistas.foodtruck.ui.main.interaction.formValid
import mx.ipn.escom.bautistas.foodtruck.ui.main.viewmodels.OrderViewModel

@ExperimentalMaterial3Api
@Composable
fun OrderScreen(
    modifier: Modifier = Modifier,
    orderViewModel: OrderViewModel,
    back: () -> Unit
) {
    val state by orderViewModel.orderState.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            TopAppBar(title = {

                Text(
                    text = stringResource(id = R.string.making_order_main_text),
                    style = MaterialTheme.typography.headlineMedium
                )
            }, navigationIcon = {
                IconButton(onClick = {
                    back()
                    orderViewModel.clearFields()
                    orderViewModel.resetState()
                }) {
                    Icon(Icons.Filled.ArrowBack, contentDescription = "")
                }
            })
        }
    ) {
        Box(modifier.padding(it)) {
            Column(
                modifier
                    .fillMaxSize()
                    .padding(20.dp)
            ) {

                ProductSelectorComponent(isError = state.isProductSelected.not(), select = {
                    orderViewModel.onProductChange(it)
                })
                TextFieldComponent(
                    label = stringResource(id = R.string.amount_label),
                    isError = state.isAmountValid.not(),
                    value = orderViewModel.amountValue,
                    onChanged = {
                        orderViewModel.onAmountChange(it)
                    },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )
                LocationSetComponent(
                    location = orderViewModel.currentLoc
                ) {
                    orderViewModel.onSetLocation()
                }
                SchoolSelectorComponent(isError = state.isSchoolSelected.not(), select = {
                    orderViewModel.onSchoolChange(it)
                })
                TextFieldComponent(
                    value = orderViewModel.buildingValue,
                    isError = state.isBuildingValid.not(),
                    label = stringResource(id = R.string.building_label),
                    onChanged = {
                        orderViewModel.onBuildingChange(it)
                    },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )
                TextFieldComponent(
                    value = orderViewModel.commentValue,
                    label = stringResource(id = R.string.comment_label),
                    onChanged = {
                        orderViewModel.onCommentChange(it)
                    }
                )
                ButtonComponent(
                    label = if (state.isLoading.not())
                        stringResource(id = R.string.submit_order_label)
                    else
                        stringResource(id = R.string.loading_text),
                    isEnable = state.formValid()
                ) {
                    orderViewModel.submitOrder() {
                        back()
                    }
                }
            }
        }
    }
}

